package eu.tn.chaoscompiler.ast.nodes.declarations.types;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;

public class ArrayTypeDeclaration extends Declaration {
    public ArrayTypeDeclaration(Id baseTypeId) {
        super(baseTypeId);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }
}
