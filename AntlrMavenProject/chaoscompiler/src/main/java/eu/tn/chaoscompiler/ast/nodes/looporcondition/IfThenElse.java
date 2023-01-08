package eu.tn.chaoscompiler.ast.nodes.looporcondition;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;

public class IfThenElse extends Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Ast condExpr;
    public Ast thenExpr;
    public Ast elseExpr;

    public IfThenElse(Ast condExpr, Ast thenExpr, Ast elseExpr) {
        this.condExpr = condExpr;
        this.thenExpr = thenExpr;
        this.elseExpr = elseExpr;
    }
}
