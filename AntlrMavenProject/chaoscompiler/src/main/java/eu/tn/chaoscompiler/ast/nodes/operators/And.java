package eu.tn.chaoscompiler.ast.nodes.operators;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;

public class And extends BinaryOperator {

    public And(Ast leftValue, Ast rightValue) {
        super(leftValue, rightValue);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
