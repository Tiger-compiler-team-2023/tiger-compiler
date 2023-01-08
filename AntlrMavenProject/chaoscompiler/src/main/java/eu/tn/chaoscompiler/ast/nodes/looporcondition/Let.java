package eu.tn.chaoscompiler.ast.nodes.looporcondition;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;
import eu.tn.chaoscompiler.ast.nodes.references.DeclarationList;

import java.util.ArrayList;

public class Let extends Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Ast decList;
    public Ast exprSeq;

    public Let(Ast decList, Ast exprSeq) {
        this.decList = decList;
        this.exprSeq = exprSeq;
    }
}
