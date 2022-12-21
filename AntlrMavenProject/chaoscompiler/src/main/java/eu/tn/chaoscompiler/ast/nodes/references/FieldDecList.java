package eu.tn.chaoscompiler.ast.nodes.references;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

public class FieldDecList implements Ast {

    public ArrayList<FieldDeclaration> list ;

    public FieldDecList() {
        this.list = new ArrayList<FieldDeclaration>() ;
    }

    public void addFieldDec(FieldDeclaration field) {
        this.list.add(field) ;
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this) ;
    }
}
