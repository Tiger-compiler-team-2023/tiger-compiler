package eu.tn.chaoscompiler.ast;

import lombok.Getter;

/**
 * Interface représentant un nœud de l'AST
 */
public interface Ast {
    /**
     * Méthode permettant de parcourir le nœud d'AST donné en paramètre
     */
    public <T> T accept(AstVisitor<T> visitor);
}