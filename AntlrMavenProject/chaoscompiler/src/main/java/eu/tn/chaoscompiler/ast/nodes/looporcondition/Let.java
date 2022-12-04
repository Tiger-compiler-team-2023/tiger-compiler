package eu.tn.chaoscompiler.ast.nodes.looporcondition;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;

import java.util.ArrayList;

public class Let implements Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<Declaration> declarationList;
    public Ast exprSeq;

    public Let(ArrayList<Declaration> declarationList, Ast exprSeq) {
        this.declarationList = declarationList;
        this.exprSeq = exprSeq;
    }
}
