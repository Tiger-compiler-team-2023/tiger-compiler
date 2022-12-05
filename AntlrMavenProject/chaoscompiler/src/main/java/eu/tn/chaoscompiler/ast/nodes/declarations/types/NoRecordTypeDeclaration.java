package eu.tn.chaoscompiler.ast.nodes.declarations.types;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;

/**
 * Déclaration de type simples ou de tableau
 */
public class NoRecordTypeDeclaration extends Declaration {
    public Id baseTypeId;

    public NoRecordTypeDeclaration(Id baseTypeId) {
        super(null);
        this.baseTypeId = baseTypeId;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
