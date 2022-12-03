package eu.tn.chaoscompiler.ast.nodes.references;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FieldDeclaration implements Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }

    public Id fieldId;
    public Id baseType;
}
