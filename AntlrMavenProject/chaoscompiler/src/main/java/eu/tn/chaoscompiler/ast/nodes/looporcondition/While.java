package eu.tn.chaoscompiler.ast.nodes.looporcondition;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;

public class While extends Ast  {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Ast condExpr;
    public Ast doExpr;

    public While(Ast condExpr, Ast doExpr) {
        this.condExpr = condExpr;
        this.doExpr = doExpr;
    }
}
