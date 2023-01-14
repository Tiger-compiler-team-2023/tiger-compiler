package eu.tn.chaoscompiler.tools;

import eu.tn.chaoscompiler.ChaosLexer;
import eu.tn.chaoscompiler.ChaosParser;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;
import java.io.IOException;

public class CustomParser {
    public static ChaosParser parseFromFile(File file) {
        return parse(file);
    }
    public static ChaosParser parse(File file) {
        //chargement du fichier et construction du parser
        // Le programme lit d'abord une chaîne de caractères
        CharStream input = null;
        try {
            input = CharStreams.fromPath(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return parse(input);
    }

    public static ChaosParser parseFromString(String input) {
        //chargement du fichier et construction du parser
        // Le programme lit d'abord une chaîne de caractères
        CharStream stream = null;
        stream = CharStreams.fromString(input);
        return parse(stream);
    }

    public static ChaosParser parse(CharStream input) {
        try {
            // Ceci permet de transformer la chaîne de caractères en une
            // suite de mots (ou token) du langage (par exemple 'if')
            ChaosLexer lexer = new ChaosLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(GestionnaireErreur.getInstance());

            // On utilise ensuite le lexer pour transformer la chaîne
            // de départ en chaîne de token
            CommonTokenStream stream = new CommonTokenStream(lexer);
            //  Pour finir, on analyse syntaxiquement (parse) la chaine de
            //  Token grâce à la classe de parser générée par antlr.
            // https://stackoverflow.com/questions/18132078/handling-errors-in-antlr4
            ChaosParser parser = new ChaosParser(stream);
            parser.removeErrorListeners();
            parser.addErrorListener(GestionnaireErreur.getInstance());
            return parser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}