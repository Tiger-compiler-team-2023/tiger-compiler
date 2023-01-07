package eu.tn.chaoscompiler.ast;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Interface représentant un nœud de l'AST
 */
@Accessors(chain = true)
public abstract class Ast {
    @Setter @Getter private int numLigne;
    /**
     * Méthode permettant de parcourir le nœud d'AST donné en paramètre
     */
    public abstract <T> T accept(AstVisitor<T> visitor);
}