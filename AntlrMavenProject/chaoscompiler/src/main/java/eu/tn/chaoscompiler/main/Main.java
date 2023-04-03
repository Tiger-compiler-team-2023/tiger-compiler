package eu.tn.chaoscompiler.main;

import eu.tn.chaoscompiler.ChaosLexer;
import eu.tn.chaoscompiler.ChaosParser;
import eu.tn.chaoscompiler.ast.*;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.graphViz.GraphDisplayer;
import eu.tn.chaoscompiler.graphViz.GraphVizVisitor;
import eu.tn.chaoscompiler.tools.CustomParser;
import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Error : Expected 1 argument filepath, found 0");
            return;
        }
        String testFile = args[0];
        try {
            // Parsing du fichier et obtention de l'arbre syntaxique
            ChaosParser parser = CustomParser.parse(new File(testFile));
            ChaosParser.ProgramContext program = parser.program();

            AstCreator creator = new AstCreator();
            Ast ast = program.accept(creator);

            // Génération du code graphviz pour l'AST
            GraphVizVisitor graphViz = new GraphVizVisitor();
            ast.accept(graphViz);
            graphViz.dumpGraph("./src/test/ressources/tree.dot");
            // Affichage du graphe dans le navigateur
            GraphDisplayer.displayDotFile("./src/test/ressources/tree.dot");

            // Tests sémantiques
            ast.accept(new ControlesSemantiques());

            //ast.accept(new AstAdapterVisitor());

            GraphVizVisitor graphViz2 = new GraphVizVisitor();
            ast.accept(graphViz2);
            graphViz2.dumpGraph("./src/test/ressources/tree.dot");
            System.out.println("\n\n\n\n\n");
            GraphDisplayer.displayDotFile("./src/test/ressources/tree.dot");

            GestionnaireErreur.getInstance().afficherErreurs();
            AsmVisitor asmvisitor= new AsmVisitor();
            ast.accept(asmvisitor);
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }

}