package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.Sequence;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.FunctionDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.VariableDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.ArrayTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.NoRecordTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.RecordTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.For;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.IfThenElse;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.Let;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.While;
import eu.tn.chaoscompiler.ast.nodes.operators.*;
import eu.tn.chaoscompiler.ast.nodes.references.*;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import eu.tn.chaoscompiler.ast.nodes.terminals.IntegerNode;
import eu.tn.chaoscompiler.ast.nodes.terminals.StringNode;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.tds.TDSlocal;
import eu.tn.chaoscompiler.tdstool.variable.ArrayRecordType;
import eu.tn.chaoscompiler.tdstool.variable.ArrayValue;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Visiteur qui part d'un AST existant et le modifie pour :
 * - Transformer les records en tableaux d'adresses
 * - Transformer les boucle for en let contenant une variable et un while
 */
public class AstAdapterVisitor implements AstVisitor<Ast> {
    private TDScontroller tdsController;
    /**
     * Pour la sauvegarde temporaire des affectations à ajouter au début de partie do du let
     */
    private final ArrayList<Ast> toAdd = new ArrayList<>();
    private Id lastRecordId;
    /**
     * Pile représentant l'emplacement actuel dans l'arbre de la TDS.
     */
    private Stack<Integer> tdsStack = new Stack<>();
    private boolean exploringForScope = false;


    @Override
    public Void visit(Program node) {
        tdsStack.push(0);
        tdsController = TDScontroller.getInstance();

        node.expression.accept(this);
        return null;
    }

    /**
     * Descend dans le scope suivant
     */
    public void down() {
        tdsController.goDown(tdsStack.peek());
        tdsStack.push(tdsStack.pop() + 1);
        tdsStack.push(0);
    }

    /**
     * Remonte dans le scope parent
     */
    public void up() {
        tdsController.goUp();
        tdsStack.pop();
    }


    // **************************************************************
    // ** Les nœuds suivants peuvent subir un changement de nature  *
    // ** Par ex: un RecordAccess deviendra un ArrayAccess          *
    // **************************************************************


    // ------- RECORD Declaration ----------
    @Override
    public Ast visit(RecordTypeDeclaration node) {
        ArrayList<FieldDeclaration> fields = node.fields.list;

        // On crée un nouveau type dans la TDS correspondant à un truc qui extends ArrayType,
        // Ce type va remplacer l'ancien RecordType dans la TDS
        tdsController.add(
                new ArrayRecordType(
                        node.objectId.identifier,
                        fields.stream().map(field -> field.fieldId.identifier).collect(Collectors.toList())
                )
        );

        // Ce nœud est transformé en un ArrayTypeDeclaration dans l'AST
        return new ArrayTypeDeclaration(new Id(Type.POINTER_TYPE.getId())).setObjectId(node.objectId);
    }

    @Override
    public Ast visit(FieldDecList node) {
        return null; // Traitement déjà fait dans RecordTypeDeclaration
    }

    @Override
    public Ast visit(FieldDeclaration node) {
        return null; // Traitement déjà fait dans RecordTypeDeclaration
    }

    // ------- RECORD Instanciation ----------
    @Override
    public Ast visit(RecordCreate node) {
        ArrayRecordType type = (ArrayRecordType) tdsController.findType(node.idObject.toString());

        // On crée un nœud d'instanciation de tableau avec autant de fields que de champs du record
        ArrayAssign arrayAssign = new ArrayAssign(
                node.idObject,
                new IntegerNode(type.getNbFields()),
                new IntegerNode(0)
        );

        // Le nom de la variable qu'on est en train de créer n'est pas disponible dans le noeud,
        // On a un attribut de classe qui nous permet de le récupérer
        // Voir la méthode visit(VariableDeclaration node).
        Id idRecord = lastRecordId;
        // On remplace donc l'ancienne variable record par un tableau dans la TDS
        tdsController.add(new ArrayValue(type, idRecord.identifier));

        // On met les champs du record dans une liste...
        List<FieldCreate> fields = node.args.stream().map(arg -> (FieldCreate) arg).collect(Collectors.toList());
        // ... puis on itère sur cette liste...
        for (int i = 0; i < fields.size(); i++) {
            // ... pour créer un nœud Affect par champs initialisé.
            // Chaque nœud sera ajouter au début des instructions du let pour initialiser le tableau
            toAdd.add(
                    new Affect(
                            new ArrayAccess(
                                    idRecord,
                                    new IntegerNode(i)
                            ),
                            fields.get(i).expr.accept(this)
                    )
            );
        }
        // Ce nœud se transforme en nœud d'instanciation de tableau
        return arrayAssign;
    }

    @Override
    public Ast visit(FieldCreate node) {
        return null; // Traitement déjà fait dans RecordCreate
    }

    // ------- RECORD Access ----------
    @Override
    public Ast visit(RecordAccess node) {
        Id index = (Id) node.index.accept(this);
        Id idRecord = (Id) node.exp.accept(this);
        ArrayRecordType type = (ArrayRecordType) tdsController.findVar(idRecord.identifier).getType();

        // On transforme les accès de record en accès de tableau.
        // L'indice du tableau nous est donnée par getIndexOfField de la classe ArrayRecordType
        return new ArrayAccess(idRecord, new IntegerNode(type.getIndexOfField(index.identifier)));
    }


    // ------- FOR ----------
    @Override
    public Ast visit(For node) {
        // On transforme les boucles for en let contenant une variable et un while
        var declarations = new DeclarationList();
        declarations.addDeclaration(new VariableDeclaration((Id) node.id, new Id(Type.INT_TYPE.getId()), node.startExpr));

        // Incrémentation du i à la fin de chaque itération
        var do_in_while = new Sequence();
        do_in_while.addInstr(node.doExpr);
        do_in_while.addInstr(new Affect(node.id, new Addition(node.id, new IntegerNode(1))));

        // création du while avec la condition d'arrêt
        var while_var = new While(new InferiorOrEquals(node.id, node.endExpr), do_in_while);

        // conteneur du while
        var sequence_containing_while = new Sequence();
        sequence_containing_while.addInstr(while_var);

        // création du let final puis accept sur celui-ci
        var let = new Let(declarations, sequence_containing_while);

        // ---- Update TDS ----
        if(!exploringForScope){
            exploringForScope = true;
            // L'idée, c'est de créer une nouvelle TDS pour le scope de la variable i...
            TDSlocal tdsLet = new TDSlocal(tdsController.getTds());
            tdsLet.add(new Value(Type.INT_TYPE, ((Id) node.id).identifier));

            // ... Le problème, c'est qu'on doit savoir comment positionner la nouvelle TDS dans l'arbre,
            // en effet, il est possible que le for contienne des let, if, while, etc.
            // pour ça on va accept le while et regarder où est la tête de lecture de la pile de TDS avant et après.
            int subTdsBeforeWhile = tdsStack.peek();
            //while_var.accept(this);
            int subTdsAfterWhile = tdsStack.peek();

            // Si on a un/des sous tds dans le for, on les place dans la tds du let
            if (subTdsBeforeWhile != subTdsAfterWhile) {
                for (int i = subTdsBeforeWhile; i < subTdsAfterWhile; i++) {
                    tdsLet.getFullTDS().add(tdsController.getTds().getFullTDS().remove(i));
                }

                // On replace la tête de lecture de la tds au bon endroit
                tdsStack.pop();
                tdsStack.push(subTdsBeforeWhile);
            }
            // ...et dans tous les cas, on ajoute la tds au bon endroit
            tdsController.getTds().getFullTDS().add(subTdsBeforeWhile, tdsLet);

            // C'est peut-être tordu, mais je n'ai pas trouvé plus simple (╯°□°)╯︵ ┻━┻
            exploringForScope = false;
        }



        // Ce nœud est remplacé par le let dans l'AST
        let.accept(this);
        return let;
    }


    // ************************************************************************
    // ****** Tous les nœuds restants ne subiront pas de changement de nature *
    // ****** (ils ne peuvent pas retourner autre chose qu'eux même)          *
    // ************************************************************************

    @Override
    public Ast visit(Sequence node) {
        node.instructions = node.instructions.stream()
                .map(ast -> ast.accept(this)).collect(Collectors.toCollection(ArrayList::new));
        return node;
    }

    @Override
    public Ast visit(FunctionDeclaration node) {
        down();
        node.content.accept(this);
        up();

        return node;
    }

    @Override
    public Ast visit(VariableDeclaration node) {
        lastRecordId = node.objectId;
        node.value = node.value.accept(this);
        return node;
    }

    @Override
    public Ast visit(ArrayTypeDeclaration node) {
        return node;
    }

    @Override
    public Ast visit(NoRecordTypeDeclaration node) {
        return node;
    }

    @Override
    public Ast visit(IfThenElse node) {
        node.condExpr = node.condExpr.accept(this);
        node.thenExpr = node.thenExpr.accept(this);
        node.elseExpr = node.elseExpr.accept(this);
        return node;
    }

    @Override
    public Ast visit(Let node) {
        var x = tdsStack.toString();
        down();
        node.decList.accept(this);

        // On ajoute les instructions d'initialisation des nouveaux tableaux correspondant aux records
        // en tête de tableau
        ((Sequence) node.exprSeq).instructions.addAll(0, toAdd);
        // On vide la liste des instructions à ajouter
        toAdd.clear();

        node.exprSeq.accept(this);
        up();
        return node;
    }

    @Override
    public Ast visit(While node) {
        node.condExpr = node.condExpr.accept(this);
        node.doExpr.accept(this);
        return node;
    }

    public Ast binaryVisit(BinaryOperator node) {
        node.leftValue = node.leftValue.accept(this);
        node.rightValue = node.rightValue.accept(this);
        return node;
    }

    @Override
    public Ast visit(Addition node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Affect node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(And node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Division node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Equals node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Inferior node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(InferiorOrEquals node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Multiplication node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Negation node) {
        node.negationTail = node.negationTail.accept(this);
        return node;
    }

    @Override
    public Ast visit(NotEquals node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Or node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Soustraction node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(Superior node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(SuperiorOrEquals node) {
        return binaryVisit(node);
    }

    @Override
    public Ast visit(ArrayAccess node) {
        node.exp = node.exp.accept(this);
        node.index = node.index.accept(this);
        return node;
    }

    @Override
    public Ast visit(ArrayAssign node) {
        node.nombreDElements = node.nombreDElements.accept(this);
        node.element = node.element.accept(this);
        return node;
    }

    @Override
    public Ast visit(FunctionCall node) {
        node.argList.accept(this);
        return node;
    }

    @Override
    public Ast visit(DeclarationList node) {
        node.list = node.list.stream()
                .map(decl -> decl.accept(this))
                .map(decl -> (Declaration) decl).collect(Collectors.toCollection(ArrayList::new));
        return node;
    }

    @Override
    public Ast visit(ParameterList node) {
        node.parameters = node.parameters.stream()
                .map(p -> p.accept(this)).collect(Collectors.toCollection(ArrayList::new));
        return node;
    }

    @Override
    public Ast visit(Id node) {
        return node;
    }

    @Override
    public Ast visit(IntegerNode node) {
        return node;
    }

    @Override
    public Ast visit(StringNode node) {
        return node;
    }
}
