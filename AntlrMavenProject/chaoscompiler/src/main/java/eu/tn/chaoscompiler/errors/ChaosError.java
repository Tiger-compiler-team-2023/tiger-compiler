package eu.tn.chaoscompiler.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChaosError {
    public enum typeError {
        SYNTAX_ERROR,
        SEMANTIC_ERROR
    }

    private int line;
    private int column;
    private String message;
    private typeError type;

    public String getErrorMessage() {
        String type = this.type == typeError.SYNTAX_ERROR ? "Erreur syntaxique" : "Erreur sémantique";
        return String.format("[%s] ligne %d:%d -> %s", type, line, column, message);
    }

}