package eu.tn.chaoscompiler.ast.nodes.references;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FunctionCall implements Ast {
    public Ast id;
    public Ast argList;

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
