package eu.tn.chaoscompiler.ast.nodes.declarations;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;

/**
 * Déclaration de type simples ou de tableau
 */
public class NoRecordTypeDeclaration extends TypeDeclaration {
    public Id baseTypeId;

    public NoRecordTypeDeclaration(Id baseTypeId) {
        super(null);
        this.baseTypeId = baseTypeId;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }
}
