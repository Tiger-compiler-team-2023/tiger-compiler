package eu.tn.chaoscompiler.ast.nodes.operators;

import eu.tn.chaoscompiler.ast.Ast;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@AllArgsConstructor
public abstract class BinaryOperator extends Ast {
    public Ast leftValue;
    public Ast rightValue;

    public BinaryOperator setLeft(Ast node) {
        this.leftValue = node;
        return this;
    }
}
