
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

import java.util.ArrayList;

public class GestionnaireErreur extends BaseErrorListener {
    private static GestionnaireErreur INSTANCE;
    private ArrayList<SyntaxError> errors;

    public GestionnaireErreur() {
        this.errors = new ArrayList<SyntaxError>();
    }

    // Pattern Singleton
    public synchronized static GestionnaireErreur getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GestionnaireErreur();
        }
        return INSTANCE;
    }

    public static void reset() {
        getInstance().errors.clear();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
            throws ParseCancellationException {
        // System.out.println("error <3 " + offendingSymbol + " "+ e.getMessage());
        // throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);
        errors.add(new SyntaxError(line, charPositionInLine, msg));
    }

    public static int getNbErreur(){
        return getInstance().errors.size();
    }

}
