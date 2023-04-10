package eu.tn.chaoscompiler.ast.nodes;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter @Getter
public class Program extends Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return (T) visitor.visit(this);
    }

    public Ast expression;
    public String asm;

    public Program(Ast expression) {
        this.expression = expression;
    }
}
