package eu.tn.chaoscompiler.errors;

import eu.tn.chaoscompiler.ast.Ast;
import lombok.Getter;

public class ChaosError {
    public enum typeError {
        SYNTAX_ERROR,
        SEMANTIC_ERROR
    }

    private final int line;
    private final int column;
    private @Getter Errors errorId;
    private final ChaosError.typeError type;
    private final String message;

    public ChaosError(Ast node, ChaosError.typeError type, Errors errorId, String... arguments) {
        this(node.getNumLigne(), node.getNumColonne(), type, errorId, arguments);
    }

    public ChaosError(int numLigne, int numColonne, ChaosError.typeError type, Errors errorId, String... arguments) {
        this.line = numLigne;
        this.column = numColonne;
        this.type = type;
        if(errorId.nbArg != arguments.length) {
            throw new IllegalArgumentException("Nombre d'arguments incorrect");
        }
        this.errorId = errorId;
        this.message = String.format(errorId.message, arguments);
    }

    public String getErrorMessage() {
        String type = this.type == ChaosError.typeError.SYNTAX_ERROR ? "Erreur syntaxique" : "Erreur sémantique";

        return String.format("[%s, E%s] ligne %d:%d -> %s", type, errorId.ordinal(), line, column, message);
    }
}
