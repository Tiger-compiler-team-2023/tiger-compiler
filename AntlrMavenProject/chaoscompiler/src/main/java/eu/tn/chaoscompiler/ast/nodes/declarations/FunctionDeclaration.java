package eu.tn.chaoscompiler.ast.nodes.declarations;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.references.FieldDecList;
import eu.tn.chaoscompiler.ast.nodes.references.FieldDeclaration;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;

import java.util.ArrayList;

public class FunctionDeclaration extends Declaration {
    public FieldDecList fields;
    public Id returnType;
    public Ast content;

    public FunctionDeclaration(Id objectId, FieldDecList list, Id returnType, Ast content) {
        super(objectId);
        this.fields = list;
        this.returnType = returnType;
        this.content = content;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
