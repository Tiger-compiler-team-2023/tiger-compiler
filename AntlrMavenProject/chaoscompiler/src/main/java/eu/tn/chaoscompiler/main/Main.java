package eu.tn.chaoscompiler.main;

import eu.tn.chaoscompiler.ChaosParser;
import eu.tn.chaoscompiler.ast.*;
import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.graphViz.GraphDisplayer;
import eu.tn.chaoscompiler.graphViz.GraphVizVisitor;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tools.CustomParser;
import org.antlr.v4.runtime.RecognitionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

            ast.accept(new ControlesSemantiques());
            ast.accept(new AstOptimizerVisitor());

            if(args.length > 1 && args[1].equals("--ast")) {
                GraphVizVisitor graphViz2 = new GraphVizVisitor();
                ast.accept(graphViz2);
                graphViz2.dumpGraph("./tree.dot");
                GraphDisplayer.displayDotFile("./tree.dot");
            }


            AsmVisitor asmvisitor = new AsmVisitor();
            ast.accept(asmvisitor);

            GestionnaireErreur.getInstance().afficherErreurs();

            String file_out_name = testFile.substring(0, testFile.lastIndexOf('.')) + ".s";
            new File(file_out_name).createNewFile();
            FileWriter file_out_wr = new FileWriter(file_out_name);
            file_out_wr.write(((Program) ast).getAsm());
            file_out_wr.close();


            String ANSI_RESET = "\u001B[0m";
            String ANSI_GREEN_BOLD = "\033[1;32m";
            System.out.println(ANSI_GREEN_BOLD + "Compilation terminée avec succès !" + ANSI_RESET);

        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}