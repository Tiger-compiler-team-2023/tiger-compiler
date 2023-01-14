package eu.tn.chaoscompiler.errors;

import eu.tn.chaoscompiler.ast.Ast;
import lombok.Setter;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.ArrayList;

public class GestionnaireErreur extends BaseErrorListener {
    private static GestionnaireErreur INSTANCE;
    private ArrayList<ChaosError> errors;
    private @Setter boolean throwException = false;

    public GestionnaireErreur() {
        this.errors = new ArrayList<>();
    }

    // Pattern Singleton
    public synchronized static GestionnaireErreur getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestionnaireErreur();
        }
        return INSTANCE;
    }

    /**
     * Réinitialise la gestion des erreurs
     */
    public static void reset() {
        getInstance().errors.clear();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
            throws ParseCancellationException {
        // System.out.println("error <3 " + offendingSymbol + " "+ e.getMessage());
        if (throwException) {
            throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
        }
        errors.add(new ChaosError(line, charPositionInLine, ChaosError.typeError.SYNTAX_ERROR,
                Errors.SYNTAX_ERROR, msg));
    }

    public void addSemanticError(Ast ast, Errors errorsId, String... msg) {
        errors.add(new ChaosError(ast, ChaosError.typeError.SEMANTIC_ERROR, errorsId, msg));
    }

    public void addUnrecognisedError(String msg, ChaosError.typeError type) {
        errors.add(new ChaosError(0, 0, type, Errors.UNRECOGNISED_ERROR, msg));
    }

    public void afficherErreurs() {
        StringBuilder sb = new StringBuilder();
        for (ChaosError error : errors) {
            sb.append(error.getErrorMessage()).append("\n");
        }
        System.out.println(sb);
    }

    public static int getNbErreur() {
        return getInstance().errors.size();
    }
}
