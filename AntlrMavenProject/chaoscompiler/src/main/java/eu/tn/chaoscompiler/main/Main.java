package eu.tn.chaoscompiler.main;

import eu.tn.chaoscompiler.ChaosLexer;
import eu.tn.chaoscompiler.ChaosParser;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Error : Expected 1 argument filepath, found 0");
            return;
        }

        String testFile = args[0];
        try {
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            //chargement du fichier et construction du parser
            // Le programme lit d'abord une chaîne de caractères
            CharStream input = CharStreams.fromFileName(testFile);
            // il la passe à l'analyseur lexical.
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
            // obtenir l'arbre syntaxique
            System.out.println("----");
            ChaosParser.ProgramContext program = parser.program();

            // code d'affichage de l'arbre syntaxique
            JFrame frame = new JFrame("Antlr AST");
            JPanel panel = new JPanel();
            TreeViewer viewer = new TreeViewer(Arrays.asList(
                    parser.getRuleNames()), program);
            viewer.setScale(0.3); // Scale a little
            panel.add(viewer);
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            System.out.println(program.getChild(0).getChild(1).getText());
            System.out.println(program.getChild(0).getChild(1));

            System.setProperty("org.graphstream.ui", "swing");

            Graph graph = new SingleGraph("Tutorial 1");

            graph.addNode("A");
            graph.addNode("B");
            graph.addNode("C");
            graph.addEdge("AB", "A", "B");
            graph.addEdge("BC", "B", "C");
            graph.addEdge("CA", "C", "A");

            graph.display();

        } catch (IOException | RecognitionException e) {
            e.printStackTrace();
        }
    }
}