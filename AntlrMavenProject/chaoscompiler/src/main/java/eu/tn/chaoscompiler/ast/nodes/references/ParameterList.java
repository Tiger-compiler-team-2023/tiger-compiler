package eu.tn.chaoscompiler.ast.nodes.references;

import java.util.ArrayList;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;

public class ParameterList implements Ast {
    public ArrayList<Ast> parameters;

    public ParameterList() {
        parameters = new ArrayList<Ast>();
    }

    public void addParameter(Ast p) {
        parameters.add(p);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
