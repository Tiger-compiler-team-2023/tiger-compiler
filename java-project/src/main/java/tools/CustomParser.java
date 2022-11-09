package tools;

import errors.GestionnaireErreur;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.File;

public class CustomParser {
    public static void parse(File file){
        try {
            //chargement du fichier et construction du parser
            // Le programme lit d'abord une chaîne de caractères
            CharStream input = CharStreams.fromPath(file.toPath());

            // il la passe à l'analyseur lexical. Ceci permet de transformer la chaîne de caractères en une
            // suite de mots (ou token) du langage (par exemple 'if')
            parser.exprLexer lexer = new parser.exprLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(GestionnaireErreur.getInstance());

            // On utilise ensuite le lexer pour transformer la chaîne
            // de départ en chaîne de token
            CommonTokenStream stream = new CommonTokenStream(lexer);
            //  Pour finir, on analyse syntaxiquement (parse) la chaine de
            //  Token grâce à la classe de parser générée par antlr.
            parser.exprParser parser = new parser.exprParser(stream);
            parser.removeErrorListeners();
            parser.addErrorListener(GestionnaireErreur.getInstance());

            // obtenir l'arbre syntaxique
            parser.program();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
