package eu.tn.chaoscompiler.ast.nodes;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;

import java.util.ArrayList;

public class Sequence implements Ast {
    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return null;
    }

    public ArrayList<Ast> instructions;

    public Sequence(Ast firstElements){
        this.instructions = new ArrayList<>();
    }

    public Sequence addInHead(Ast node){
        this.instructions.add(0, node);
        return this;
    }
}
















