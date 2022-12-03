package eu.tn.chaoscompiler.ast.nodes.looporcondition;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;

public class Let implements Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
    public Ast declarationList;
    public Ast exprSeq;
    public Let(Ast declarationList, Ast exprSeq){
        this.declarationList=declarationList;
        this.exprSeq=exprSeq;
    }
}
