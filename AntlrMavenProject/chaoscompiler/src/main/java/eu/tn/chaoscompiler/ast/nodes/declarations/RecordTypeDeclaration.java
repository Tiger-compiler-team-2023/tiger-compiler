package eu.tn.chaoscompiler.ast.nodes.declarations;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.references.FieldDeclaration;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;

import java.util.ArrayList;

public class RecordTypeDeclaration extends TypeDeclaration {
    public RecordTypeDeclaration() {
        super(null);
        this.fields = new ArrayList<>();
    }
    public RecordTypeDeclaration(Id typeId) {
        super(typeId);
        this.fields = new ArrayList<>();
    }

    public RecordTypeDeclaration addFieldInHead(FieldDeclaration fieldDeclaration){
        fields.add(0, fieldDeclaration);
        return this;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }

    public ArrayList<FieldDeclaration> fields;
}
