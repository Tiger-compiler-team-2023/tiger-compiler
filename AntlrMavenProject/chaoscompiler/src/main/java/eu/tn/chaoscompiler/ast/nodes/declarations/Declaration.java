package eu.tn.chaoscompiler.ast.nodes.declarations;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@AllArgsConstructor
public abstract class Declaration implements Ast {
    public Id objectId;
}
