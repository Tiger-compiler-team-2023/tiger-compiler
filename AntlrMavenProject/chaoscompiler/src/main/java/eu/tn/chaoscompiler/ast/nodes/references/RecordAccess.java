package eu.tn.chaoscompiler.ast.nodes.references;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RecordAccess implements Ast {
    public Ast exp;
    public Ast index;

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
