package eu.tn.chaoscompiler.ast.nodes.references;

import java.util.ArrayList;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import lombok.AllArgsConstructor;

public class RecordCreate implements Ast {
    public ArrayList<Ast> args;

    public RecordCreate() {
        args = new ArrayList<Ast>();
    }

    public void addArg(Ast arg) {
        args.add(arg);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
