package eu.tn.chaoscompiler.ast.nodes.declarations;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;

public class VariableDeclaration extends Declaration {
    public Id typeId;
    public Ast value;

    public VariableDeclaration(Id varId, Id typeId, Ast value) {
        super(varId);
        this.typeId = typeId;
        this.value = value;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
