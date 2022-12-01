package eu.tn.chaoscompiler.ast.nodes.operators;

import eu.tn.chaoscompiler.ast.Ast;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BinariesOperators implements Ast {
    public Ast leftValue;
    public Ast rightValue;
}
