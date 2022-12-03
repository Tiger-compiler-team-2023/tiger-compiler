package eu.tn.chaoscompiler.ast.nodes;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Program implements Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public Ast expression;
    public Program(Ast expression){
        this.expression=expression;
    }
}
