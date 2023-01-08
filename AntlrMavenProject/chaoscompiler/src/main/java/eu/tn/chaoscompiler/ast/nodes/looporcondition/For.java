package eu.tn.chaoscompiler.ast.nodes.looporcondition;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;


public class For extends Ast {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Ast id;

    public Ast startExpr;
    public Ast endExpr;
    public Ast doExpr;

    public For(Ast id, Ast startExpr, Ast endExpr, Ast doExpr) {
        this.id = id;
        this.startExpr = startExpr;
        this.endExpr = endExpr;
        this.doExpr = doExpr;
    }
}
