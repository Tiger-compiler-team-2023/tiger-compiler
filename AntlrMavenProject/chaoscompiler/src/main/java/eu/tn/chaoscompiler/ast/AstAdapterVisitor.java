package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.Sequence;
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

import java.util.ArrayList;

/**
 * Visiteur qui part d'un AST existant et le modifie pour :
 *  - Transformer les records en tableaux d'adresses
 *  - Transformer les boucle for en let contenant une variable et un while
 */
public class AstAdapterVisitor implements AstVisitor<Ast> {

    @Override
    public Void visit(Program node) {
        node.expression.accept(this);
        return null;
    }

    // **************************************************************
    // ** Les nœuds suivants peuvent subir un changement de nature  *
    // ** Par ex : un RecordAccess deviendra un ArrayAccess      *
    // **************************************************************


    // ------- RECORD Declaration ----------
    @Override
    public Ast visit(RecordTypeDeclaration node) {
        // TODO --------------------------------------
        return node;
    }

    @Override
    public Ast visit(FieldDecList node) {
        // TODO ----------------------------------
        return node;
    }

    @Override
    public Ast visit(FieldDeclaration node) {
        // TODO --------------------------------
        return null;
    }

    // ------- RECORD Instanciation ----------
    @Override
    public Ast visit(RecordCreate node) {
        // TODO --------------------------------------
        return node;
    }

    @Override
    public Ast visit(FieldCreate node) {
        // TODO ------------------------------
        return node;
    }

    // ------- RECORD Access ----------
    @Override
    public Ast visit(RecordAccess node) {
        // TODO --------------------------------------
        return node;
    }


    // ------- FOR ----------
    @Override
    public Ast visit(For node) {
        // TODO   --------------------------------------
        return node;
    }


    // ************************************************************************
    // ****** Tous les nœuds restants ne subiront pas de changement de nature *
    // ****** (ils ne peuvent pas retourner autre chose qu'eux même)          *
    // ************************************************************************

    @Override
    public Ast visit(Sequence node) {
        node.instructions = (ArrayList<Ast>) node.instructions.stream()
                .map(ast -> ast.accept(this)).toList();
        return node;
    }

    @Override
    public Ast visit(FunctionDeclaration node) {
        node.content.accept(this);
        return node;
    }

    @Override
    public Ast visit(VariableDeclaration node) {
        // TODO
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
        node.decList.accept(this);
        node.exprSeq.accept(this);
        return node;
    }

    @Override
    public Ast visit(While node) {
        node.condExpr = node.condExpr.accept(this);
        node.doExpr.accept(this);
        return node;
    }

    public Ast binaryVisit(BinaryOperator node){
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
        node.list.forEach(decl -> decl.accept(this));
        return node;
    }

    @Override
    public Ast visit(ParameterList node) {
        node.parameters = (ArrayList<Ast>) node.parameters.stream()
                .map(p -> p.accept(this)).toList();
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
