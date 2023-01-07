package eu.tn.chaoscompiler.ast.nodes.operators;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.Setter;

public class Division extends BinaryOperator {

    public Division(Ast leftValue, Ast rightValue) {
        super(leftValue, rightValue);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
