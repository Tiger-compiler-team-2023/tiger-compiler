package eu.tn.chaoscompiler.ast.nodes.terminals;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringNode implements Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }

    public String stringContent;
}