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

    public Sequence(ArrayList<Ast> list){
        this.instructions = list;
    }
}
















