package eu.tn.chaoscompiler.ast.nodes.declarations;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class TypeDeclaration implements Declaration{
    public Id typeId;
}
