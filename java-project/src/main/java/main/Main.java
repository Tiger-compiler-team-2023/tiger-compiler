package main;

import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.IOException;


import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

public class Main {

    public static void main(String[] args){

        if (args.length < 1){
            System.out.println("Error : Expected 1 argument filepath, found 0");
            return;
        }

        String testFile = args[0];

        try {
            //chargement du fichier et construction du parser
            // Le programme lit d'abord une chaîne de caractères
            CharStream input = CharStreams.fromFileName(testFile);
            // il la passe à l'analyseur lexical.
            // Ceci permet de transformer la chaîne de caractères en une
            // suite de mots (ou token) du langage (par exemple 'if')
            parser.exprLexer lexer = new parser.exprLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(GestionnaireErreur.getInstance());

            // On utilise ensuite le lexer pour transformer la chaîne
            // de départ en chaîne de token
            CommonTokenStream stream = new CommonTokenStream(lexer);
            //  Pour finir, on analyse syntaxiquement (parse) la chaine de
            //  Token grâce à la classe de parser générée par antlr.
            // https://stackoverflow.com/questions/18132078/handling-errors-in-antlr4
            parser.exprParser parser = new parser.exprParser(stream);
            parser.removeErrorListeners();
            parser.addErrorListener(GestionnaireErreur.getInstance());
            // obtenir l'arbre syntaxique
            System.out.println("----");
            parser.exprParser.ProgramContext program = parser.program();
            System.out.println("----");

            // code d'affichage de l'arbre syntaxique
            JFrame frame = new JFrame("Antlr AST");
            JPanel panel = new JPanel();
            TreeViewer viewer = new TreeViewer(Arrays.asList(
                    parser.getRuleNames()),program);
            viewer.setScale(1.5); // Scale a little
            panel.add(viewer);
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
        catch (IOException | RecognitionException e) {
            e.printStackTrace();
        }
    }
}
