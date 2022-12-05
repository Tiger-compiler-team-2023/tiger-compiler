package eu.tn.chaoscompiler.ast.nodes.operators;

import eu.tn.chaoscompiler.ast.Ast;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BinaryOperator implements Ast {
    public Ast leftValue;
    public Ast rightValue;

    public Ast getLeft() { return this.leftValue ; }
    public Ast getRight() { return this.rightValue ; }
    public void setLeft(Ast node) { this.leftValue = node ; }
    public void setRight(Ast node) { this.rightValue = node ; }
}
