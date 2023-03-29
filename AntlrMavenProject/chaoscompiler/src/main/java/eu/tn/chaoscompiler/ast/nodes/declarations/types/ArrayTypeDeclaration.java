package eu.tn.chaoscompiler.ast.nodes.declarations.types;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;

public class ArrayTypeDeclaration extends Declaration {
    public Id baseTypeId;

    public ArrayTypeDeclaration(Id baseTypeId) {
        this.baseTypeId = baseTypeId;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
