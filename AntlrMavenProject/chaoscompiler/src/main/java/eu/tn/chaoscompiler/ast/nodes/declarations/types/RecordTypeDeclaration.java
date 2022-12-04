package eu.tn.chaoscompiler.ast.nodes.declarations.types;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;
import eu.tn.chaoscompiler.ast.nodes.references.FieldDeclaration;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
public class RecordTypeDeclaration extends Declaration {
    public RecordTypeDeclaration(ArrayList<FieldDeclaration> list) {
        super();
        this.fields = list;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }

    public ArrayList<FieldDeclaration> fields;
}
