package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.tdstool.variable.Type;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Interface représentant un nœud de l'AST
 */
@Accessors(chain = true)
public abstract class Ast {
    @Getter private int numLigne, numColonne;

    /**
     * Méthode permettant de parcourir le nœud d'AST donné en paramètre
     */
    public abstract <T> T accept(AstVisitor<T> visitor);

    private Type nodeType;
    public void setType(Type nodeType) {
        this.nodeType = nodeType;
    }
    public Type getType() {
        return this.nodeType;
    }

    public Ast setligneEtColonne(int ligne, int colonne) {
        this.numLigne = ligne;
        this.numColonne = colonne;
        return this;
    }
}