package eu.tn.chaoscompiler.ast.nodes.terminals;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringNode extends Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public String stringContent;
}
