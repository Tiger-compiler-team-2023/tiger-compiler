package eu.tn.chaoscompiler.ast.nodes.declarations;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;

public class ArrayTypeDeclaration extends NoRecordTypeDeclaration {
    public ArrayTypeDeclaration(Id baseTypeId) {
        super(baseTypeId);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }
}
