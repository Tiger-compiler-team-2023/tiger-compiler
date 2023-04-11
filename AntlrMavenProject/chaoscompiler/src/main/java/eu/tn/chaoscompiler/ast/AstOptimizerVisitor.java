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
import eu.tn.chaoscompiler.tdstool.variable.ArrayRecordType;
import eu.tn.chaoscompiler.tdstool.variable.ArrayValue;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * PARTIE 1 - Visiteur qui part d'un AST existant et le modifie pour :
 * - Transformer les records en tableaux d'adresses
 * - Transformer les boucle for en let contenant une variable et un while
 * <p>
 * <p>
 * PARTIE 2 - OPTIMISATION SUR l'AST
 * <p>
 * - Les séquences qui ont un seul fils sont supprimées
 * - Les expresssions arithmétiques et booléennes sont simplifiées autant que possible
 *      - Y compris avec le court-circuit des and et or si possible
 * - Un nœud while dont la condition est simplifiée à false est supprimée
 */
public class AstOptimizerVisitor implements AstVisitor<Ast> {
    private TDScontroller tdsController;
    /**
     * Pour la sauvegarde temporaire des affectations à ajouter au début de partie do du let
     */
    private final ArrayList<Ast> toAdd = new ArrayList<>();
    private Id lastRecordId;
    /**
     * Pile représentant l'emplacement actuel dans l'arbre de la TDS.
     */
    private final Stack<Integer> tdsStack = new Stack<>();


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
        tdsController.down(tdsStack.peek());
        tdsStack.push(tdsStack.pop() + 1);
        tdsStack.push(0);
    }

    /**
     * Remonte dans le scope parent
     */
    public void up() {
        tdsController.up();
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
        // Le nom de la variable qu'on est en train de créer n'est pas disponible dans le noeud,
        // On a un attribut de classe qui nous permet de le récupérer
        // Voir la méthode visit(VariableDeclaration node).
        Id idRecord = lastRecordId;

        ArrayRecordType type = (ArrayRecordType) tdsController.findType(node.idObject.toString());
        Value rec = tdsController.findVar(idRecord.identifier);

        // On crée un nœud d'instanciation de tableau avec autant de fields que de champs du record
        ArrayAssign arrayAssign = new ArrayAssign(
                node.idObject,
                new IntegerNode(type.getNbFields()),
                new IntegerNode(0)
        );

        // On remplace donc l'ancienne variable record par un tableau dans la TDS
        tdsController.add(new ArrayValue(type, idRecord.identifier), rec);

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
        down(); // Le contenu du for est dans un scope enfant

        // On trouve un nom qui existe pas
        String endId = "end_" + ((Id) node.id).identifier;
        while (tdsController.existsVar(endId)) {
            endId += "_";
        }

        // On met la valeur finale dans une variable pour éviter de la recalculer à chaque itération
        var endValueDec = new VariableDeclaration(new Id(endId), new Id(Type.INT_TYPE.getId()), node.endExpr);
        var iterationVarDec = new VariableDeclaration((Id) node.id, new Id(Type.INT_TYPE.getId()), node.startExpr);

        // On transforme les boucles for en let contenant une variable et un while
        var declarations = new DeclarationList();
        declarations.addDeclaration(iterationVarDec);
        declarations.addDeclaration(endValueDec);


        // Incrémentation du i à la fin de chaque itération
        var do_in_while = new Sequence();
        do_in_while.addInstr(node.doExpr);
        do_in_while.addInstr(new Affect(node.id, new Addition(node.id, new IntegerNode(1))));

        // création du while avec la condition d'arrêt
        var while_var = new While(new InferiorOrEquals(node.id, new Id(endId)), do_in_while);

        // conteneur du while
        var sequence_containing_while = new Sequence();
        sequence_containing_while.addInstr(while_var);

        // création du let final puis accept sur celui-ci
        var let = new Let(declarations, sequence_containing_while);

        // ---- mise à jour TDS ----
        // la TDS du for devient celle du let en remplaçant la variable
        tdsController.add(new Value(Type.INT_TYPE, endId));
        // On n'accepte pas directement le let pour rester dans le bon scope de la tds
        let.decList = let.decList.accept(this);
        let.exprSeq = let.exprSeq.accept(this);

        // Le type du let est void, cela sert pour la génération de l'ASM
        while_var.setType(Type.VOID_TYPE);
        let.setType(Type.VOID_TYPE);

        up();
        // Ce nœud est remplacé par le let dans l'AST
        return let;
    }


    // ************************************************************************
    // ****** Tous les nœuds restants ne subiront pas de changement de nature *
    // ****** (ils ne peuvent pas retourner autre chose qu'eux même)          *
    // ************************************************************************

    @Override
    public Ast visit(Sequence node) {
        node.instructions = node.instructions.stream()
                // Accept sur chaque instruction
                .map(ast -> ast.accept(this))
                // On filtre les nœuds séquences vides (possible dans le cas d'un while supprimé).
                .filter(ast -> {
                    if (!(ast instanceof Sequence s)) return true;
                    return s.instructions.size() != 0;
                })
                // On récupère la liste d'instructions (<=> toList() en java moderne)
                .collect(Collectors.toCollection(ArrayList::new));

        // Si la séquence a un unique élément, elle ne sert à rien
        if (node.instructions.size() == 1) {
            return node.instructions.get(0);
        }
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
        if (node.elseExpr != null)
            node.elseExpr = node.elseExpr.accept(this);
        return node;
    }

    @Override
    public Ast visit(Let node) {
        var x = tdsStack.toString();
        down();
        node.decList = node.decList.accept(this);

        // On ajoute les instructions d'initialisation des nouveaux tableaux correspondant aux records
        // en tête de tableau
        ((Sequence) node.exprSeq).instructions.addAll(0, toAdd);
        // On vide la liste des instructions à ajouter
        toAdd.clear();

        node.exprSeq = node.exprSeq.accept(this);
        up();
        return node;
    }

    @Override
    public Ast visit(While node) {
        node.condExpr = node.condExpr.accept(this);
        int indexBeforeDo = tdsStack.peek();
        node.doExpr = node.doExpr.accept(this);
        int indexAfterDo = tdsStack.peek();

        // Si le while est evaluable à false, on le supprime de la tds
        if (node.condExpr instanceof IntegerNode integerNode && integerNode.value == 0) {
            // Si le while contient une sous tds, on la supprime
            for (int i = 0; i < indexAfterDo - indexBeforeDo; i++) {
                tdsController.getTds().getFullTDS().remove(indexBeforeDo);
            }
            // On remet l'index de la tds à l'index avant le do qu'on a supprimé
            tdsStack.pop();
            tdsStack.push(indexBeforeDo);
            return new Sequence();
        }
        return node;
    }

    int leftValue, rightValue;

    public void binaryVisit(BinaryOperator node) {
        node.leftValue = node.leftValue.accept(this);
        node.rightValue = node.rightValue.accept(this);
    }

    /**
     * Visite les deux fils, et retourne vrai si les deux sont des entiers terminaux (donc optimisables)
     */
    public boolean binaryVisitAndCheckOptimizable(BinaryOperator node) {
        binaryVisit(node);
        if (node.leftValue instanceof IntegerNode && node.rightValue instanceof IntegerNode) {
            leftValue = ((IntegerNode) node.leftValue).value;
            rightValue = ((IntegerNode) node.rightValue).value;
            return true;
        }
        return false;
    }

    @Override
    public Ast visit(Addition node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode(leftValue + rightValue);
        }
        return node;
    }

    @Override
    public Ast visit(Affect node) {
        binaryVisit(node);
        return node;
    }

    @Override
    public Ast visit(And node) {
        int indexBeforeAcccept = tdsStack.peek();
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode((leftValue != 0 && rightValue != 0 ? 1 : 0));
        }

        // Dans le cas d'un And, un côté à false suffit à optimiser.
        // on optimise que s'il n'y pas de let dans le contenu d'un des fils
        if (indexBeforeAcccept == tdsStack.peek()){
            if (node.leftValue instanceof IntegerNode left){
                return (left.value == 0 ? new IntegerNode(0) : node.rightValue);
            }
            if (node.rightValue instanceof IntegerNode right){
                return (right.value == 0 ? new IntegerNode(0) : node.leftValue);
            }
        }

        return node;
    }

    @Override
    public Ast visit(Or node) {
        int indexBeforeAcccept = tdsStack.peek();
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode((leftValue != 0 || rightValue != 0 ? 1 : 0));
        }

        // Dans le cas d'un Or, un côté à true suffit à optimiser.
        // on optimise que s'il n'y pas de let dans le contenu d'un des fils
        if (indexBeforeAcccept == tdsStack.peek()){
            if (node.leftValue instanceof IntegerNode left){
                return (left.value != 0 ? new IntegerNode(1) : node.rightValue);
            }
            if (node.rightValue instanceof IntegerNode right){
                return (right.value != 0 ? new IntegerNode(1) : node.leftValue);
            }
        }
        return node;
    }


    @Override
    public Ast visit(Division node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode(leftValue / rightValue);
        }
        return node;
    }

    @Override
    public Ast visit(Equals node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode((leftValue == rightValue ? 1 : 0));
        }
        return node;
    }

    @Override
    public Ast visit(Inferior node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode((leftValue < rightValue ? 1 : 0));
        }
        return node;
    }

    @Override
    public Ast visit(InferiorOrEquals node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode((leftValue <= rightValue ? 1 : 0));
        }
        return node;
    }

    @Override
    public Ast visit(Multiplication node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode(leftValue * rightValue);
        }
        return node;
    }

    @Override
    public Ast visit(Negation node) {
        node.negationTail = node.negationTail.accept(this);
        if (node.negationTail instanceof IntegerNode integerNode) {
            return new IntegerNode(-integerNode.value);
        }
        return node;
    }

    @Override
    public Ast visit(NotEquals node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode((leftValue != rightValue ? 1 : 0));
        }
        return node;
    }

    @Override
    public Ast visit(Soustraction node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode(leftValue - rightValue);
        }
        return node;
    }

    @Override
    public Ast visit(Superior node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode((leftValue > rightValue ? 1 : 0));
        }
        return node;
    }

    @Override
    public Ast visit(SuperiorOrEquals node) {
        if (binaryVisitAndCheckOptimizable(node)) {
            return new IntegerNode((leftValue >= rightValue ? 1 : 0));
        }
        return node;
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
