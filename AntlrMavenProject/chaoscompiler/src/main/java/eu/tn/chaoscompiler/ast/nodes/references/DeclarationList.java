package eu.tn.chaoscompiler.ast.nodes.references;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;

import java.util.ArrayList;

public class DeclarationList extends Ast {
    public ArrayList<Declaration> list;

    public DeclarationList() {
        list = new ArrayList<Declaration>();
    }

    public void addDeclaration(Declaration dec) {
        list.add(dec);
    }

    @Override
    public <T> T accept(AstVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
