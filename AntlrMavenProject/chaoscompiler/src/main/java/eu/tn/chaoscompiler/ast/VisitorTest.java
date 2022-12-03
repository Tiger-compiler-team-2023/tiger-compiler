package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.ast.nodes.looporcondition.For;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.IfThenElse;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.Let;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.While;
import eu.tn.chaoscompiler.ast.nodes.operators.Addition;
import eu.tn.chaoscompiler.ast.nodes.operators.BinariesOperators;
import eu.tn.chaoscompiler.ast.nodes.operators.Negation;
import eu.tn.chaoscompiler.ast.nodes.references.FunctionCall;
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
}
