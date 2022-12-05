package eu.tn.chaoscompiler.ast;

import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * Classe servant à construire des listes d'objet AST en remontant l'arbre au fur et à mesure de droite à gauche.
 * Une fois remonté à la tête de la liste, l'idée est de récupérer l'objet list et de laisser l'instance de cette
 * classe au garbage collector.
 */
@NoArgsConstructor
public class ListAccumulator<T extends Ast> implements Ast {
    public ArrayList<T> list;

    public ListAccumulator(T firstElement) {
        this.list = new ArrayList<>();
        list.add(firstElement);
    }

    @Override
    public <Q> Q accept(AstVisitor<Q> visitor) {
        return null;
    }

    public ListAccumulator<T> addInHead(T element) {
        list.add(0, element);
        return this;
    }
}
