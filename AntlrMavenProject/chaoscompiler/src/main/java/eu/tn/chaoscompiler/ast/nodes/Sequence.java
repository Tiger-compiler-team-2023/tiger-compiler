package eu.tn.chaoscompiler.ast.nodes;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;

import java.util.ArrayList;

public class Sequence extends Ast  {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public ArrayList<Ast> instructions;

    public Sequence() {
        this.instructions = new ArrayList<Ast>();
    }

    public void addInstr(Ast instr) {
        this.instructions.add(instr) ;
    }
}
















