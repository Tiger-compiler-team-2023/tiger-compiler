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

import java.util.ArrayList;
import java.util.List;

/**
 * Visiteur qui part d'un AST existant et le modifie pour :
 * - Transformer les records en tableaux d'adresses
 * - Transformer les boucle for en let contenant une variable et un while
 */
public class AstAdapterVisitor implements AstVisitor<Ast> {
    private TDScontroller tdsController;
    private final ArrayList<Ast> toAdd = new ArrayList<>();
    private Id lastRecordId;


    @Override
    public Void visit(Program node) {
        tdsController = TDScontroller.getInstance();

        node.expression.accept(this);
        return null;
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
        // Ce type va remplacer l'ancier RecordType dans la TDS
        tdsController.add(
                new ArrayRecordType(
                        node.objectId.identifier,
                        fields.stream().map(field -> field.fieldId.identifier).toList()
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
        List<FieldCreate> fields = node.args.stream().map(arg -> (FieldCreate) arg).toList();
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

        tdsController.goDown();
        node.doExpr = node.doExpr.accept(this);
        node.endExpr = node.endExpr.accept(this);
        node.startExpr = node.startExpr.accept(this);

        tdsController.goUp();
        // TODO   --------------------------------------
        return node;
    }











    // ************************************************************************
    // ****** Tous les nœuds restants ne subiront pas de changement de nature *
    // ****** (ils ne peuvent pas retourner autre chose qu'eux même)          *
    // ************************************************************************

    @Override
    public Ast visit(Sequence node) {
        node.instructions = new ArrayList<>(node.instructions.stream()
                .map(ast -> ast.accept(this)).toList());
        return node;
    }

    @Override
    public Ast visit(FunctionDeclaration node) {
        tdsController.down(node);
        node.content.accept(this);
        tdsController.goUp();

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
        tdsController.goDown();
        node.decList.accept(this);

        // On ajoute les instructions d'initialisation des nouveaux tableaux correspondant aux records
        // en tête de tableau
        ((Sequence) node.exprSeq).instructions.addAll(0, toAdd);
        // On vide la liste des instructions à ajouter
        toAdd.clear();

        node.exprSeq.accept(this);
        tdsController.goUp();
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
        node.list = new ArrayList<>(
                node.list.stream()
                        .map(decl -> decl.accept(this))
                        .map(decl -> (Declaration) decl).toList()
        );
        return node;
    }

    @Override
    public Ast visit(ParameterList node) {
        node.parameters = new ArrayList<>(node.parameters.stream()
                .map(p -> p.accept(this)).toList());
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
