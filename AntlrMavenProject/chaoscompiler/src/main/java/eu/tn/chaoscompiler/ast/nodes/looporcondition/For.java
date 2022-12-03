package eu.tn.chaoscompiler.ast.nodes.looporcondition;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;

public class For implements Ast {

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }
    private Ast Id;

    private Ast startExpr;
    private Ast endExpr;
    private Ast doExpr;
    public For(Ast Id, Ast startExpr,Ast endExpr,Ast doExpr){
        this.Id=Id;
        this.startExpr=startExpr;
        this.endExpr=endExpr;
        this.doExpr=doExpr;
    }
}
