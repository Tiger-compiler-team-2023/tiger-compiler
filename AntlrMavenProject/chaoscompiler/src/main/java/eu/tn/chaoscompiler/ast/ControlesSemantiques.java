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

public class ControlesSemantiques implements AstVisitor<Void> {
    @Override
    public Void visit(Program node) {
        return null;
    }

    @Override
    public Void visit(Sequence node) {
        return null;
    }

    @Override
    public Void visit(FunctionDeclaration node) {
        return null;
    }

    @Override
    public Void visit(VariableDeclaration node) {
        return null;
    }

    @Override
    public Void visit(ArrayTypeDeclaration node) {
        return null;
    }

    @Override
    public Void visit(NoRecordTypeDeclaration node) {
        return null;
    }

    @Override
    public Void visit(RecordTypeDeclaration node) {
        return null;
    }

    @Override
    public Void visit(For forExpr) {
        return null;
    }

    @Override
    public Void visit(IfThenElse ifThenElseExpr) {
        return null;
    }

    @Override
    public Void visit(Let letExpr) {
        return null;
    }

    @Override
    public Void visit(While whileExpr) {
        return null;
    }

    @Override
    public Void visit(Addition node) {
        return null;
    }

    @Override
    public Void visit(Affect node) {
        return null;
    }

    @Override
    public Void visit(And node) {
        return null;
    }

    @Override
    public Void visit(Division node) {
        return null;
    }

    @Override
    public Void visit(Equals node) {
        return null;
    }

    @Override
    public Void visit(Inferior node) {
        return null;
    }

    @Override
    public Void visit(InferiorOrEquals node) {
        return null;
    }

    @Override
    public Void visit(Multiplication node) {
        return null;
    }

    @Override
    public Void visit(Negation node) {
        return null;
    }

    @Override
    public Void visit(NotEquals node) {
        return null;
    }

    @Override
    public Void visit(Or node) {
        return null;
    }

    @Override
    public Void visit(Soustraction node) {
        return null;
    }

    @Override
    public Void visit(Superior node) {
        return null;
    }

    @Override
    public Void visit(SuperiorOrEquals node) {
        return null;
    }

    @Override
    public Void visit(ArrayAccess node) {
        return null;
    }

    @Override
    public Void visit(ArrayAssign node) {
        return null;
    }

    @Override
    public Void visit(FieldCreate node) {
        return null;
    }

    @Override
    public Void visit(FieldDeclaration node) {
        return null;
    }

    @Override
    public Void visit(FieldDecList node) {
        return null;
    }

    @Override
    public Void visit(FunctionCall node) {
        return null;
    }

    @Override
    public Void visit(DeclarationList node) {
        return null;
    }

    @Override
    public Void visit(ParameterList node) {
        return null;
    }

    @Override
    public Void visit(RecordAccess node) {
        return null;
    }

    @Override
    public Void visit(RecordCreate node) {
        return null;
    }

    @Override
    public Void visit(Id node) {
        return null;
    }

    @Override
    public Void visit(IntegerNode node) {
        return null;
    }

    @Override
    public Void visit(StringNode node) {
        return null;
    }
}
