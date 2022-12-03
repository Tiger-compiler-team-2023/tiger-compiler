package eu.tn.chaoscompiler.ast.nodes.references;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.AllArgsConstructor;

public class FunctionCall implements Ast {
    public Ast id;
    public Ast ArgList;

    public FunctionCall(Ast id, Ast ArgList) {
        this.id = id;
        this.ArgList = ArgList;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
