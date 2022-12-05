package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ast.nodes.looporcondition.For;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.IfThenElse;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.Let;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.While;
import eu.tn.chaoscompiler.ast.nodes.operators.*;
import eu.tn.chaoscompiler.ast.nodes.references.*;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.terminals.IntegerNode;

public class VisitorTest implements AstVisitor<String> {

    @Override
    public String visit(Id node) {
        return null;
    }

    @Override
    public String visit(Program node) {
        return null;
    }

    @Override
    public String visit(Addition node) {
        return null;
    }

    @Override
    public String visit(Negation node) {
        return null;
    }

    @Override
    public String visit(IntegerNode node) {
        return null;
    }

    @Override
    public String visit(FunctionCall node) {
        return null;
    }

    @Override
    public String visit(ParameterList node) {
        return null;
    }

    @Override
    public String visit(ArrayAssign node) {
        return null;
    }

    @Override
    public String visit(ArrayAccess node) {
        return null;
    }

    @Override
    public String visit(RecordCreate node) {
        return null;
    }

    @Override
    public String visit(RecordAccess node) {
        return null;
    }

    @Override
    public String visit(FieldCreate node) {
        return null;
    }

    @Override
    public String visit(For forExpr) {
        return null;
    }

    @Override
    public String visit(While whileExpr) {
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElseExpr) {
        return null;
    }

    @Override
    public String visit(Let letExpr) {
        return null;
    }

    @Override
    public String visit(Multiplication node) {
        return null;
    }

    @Override
    public String visit(Division node) {
        return null;
    }

    @Override
    public String visit(Soustraction node) {
        return null;
    }

    @Override
    public String visit(Equals node) {
        return null;
    }

    @Override
    public String visit(NotEquals node) {
        return null;
    }

    @Override
    public String visit(Superior node) {
        return null;
    }

    @Override
    public String visit(Inferior node) {
        return null;
    }

    @Override
    public String visit(SuperiorOrEquals node) {
        return null;
    }

    @Override
    public String visit(InferiorOrEquals node) {
        return null;
    }

    @Override
    public String visit(Or node) {
        return null;
    }

    @Override
    public String visit(And node) {
        return null;
    }

    @Override
    public String visit(Affect node) {
        return null;
    }
}
