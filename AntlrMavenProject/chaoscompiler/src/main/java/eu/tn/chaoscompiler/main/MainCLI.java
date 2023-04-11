package eu.tn.chaoscompiler.main;

import eu.tn.chaoscompiler.ChaosParser;
import eu.tn.chaoscompiler.ast.*;
import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.graphViz.GraphDisplayer;
import eu.tn.chaoscompiler.graphViz.GraphVizVisitor;
import eu.tn.chaoscompiler.tools.CustomParser;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.Callable;

@Command(name = "tigerCompiler", mixinStandardHelpOptions = true, version = "1.0",
        header ="" +
                "@|blue  _____ _                    ____                      _ _           \n|@" +
                "@|blue |_   _(_) __ _  ___ _ __   / ___|___  _ __ ___  _ __ (_) | ___ _ __ \n|@" +
                "@|blue   | | | |/ _` |/ _ \\ '__| | |   / _ \\| '_ ` _ \\| '_ \\| | |/ _ \\ '__|\n|@" +
                "@|blue   | | | | (_| |  __/ |    | |__| (_) | | | | | | |_) | | |  __/ |   \n|@" +
                "@|blue   |_| |_|\\__, |\\___|_|     \\____\\___/|_| |_| |_| .__/|_|_|\\___|_|   \n|@" +
                "@|blue          |___/                                 |_|                 |@ ",
        description = "\nCompilateur du langage Tiger vers l'ARM64.",
        synopsisHeading = "%n",
        optionListHeading = "@|bold %nOptions|@:%n",
        parameterListHeading = "@|bold %nParamètres|@:%n",
        headerHeading = "",
        footer = "\n\n@|cyan Projet de seconde année de Nicolas Frache, Théo Goureau, Cyrielle Lacrampe--Diter et Rida Moussaoui, 2023.|@\n" +
                " @|cyan https://gitlab.telecomnancy.univ-lorraine.fr/Theo.Goureau/pcl-goureau1u,|@"
)

public class MainCLI implements Callable<Integer> {


    @Parameters(index = "0", description = "Fichier Tiger source.")
    private File source;

    @Parameters(index = "1", description = "Fichier binaire ARM64 généré")
    String destination;

    @Option(names = {"-a", "--ast", "--display-ast"}, description = "Affiche le lien pour l'affichage de l'arbre " +
            "syntaxique abstrait.")
    boolean displayAST = false;

    public static void main(String... args) {
        int exitCode = new CommandLine(new MainCLI()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() throws Exception {
        // Parsing du fichier et obtention de l'arbre syntaxique
        ChaosParser parser = CustomParser.parse(new File(source.toURI()));
        ChaosParser.ProgramContext program = parser.program();

        AstCreator creator = new AstCreator();
        Ast ast = program.accept(creator);

        ast.accept(new ControlesSemantiques());
        ast.accept(new AstOptimizerVisitor());

        if(displayAST) {
            GraphVizVisitor graphViz2 = new GraphVizVisitor();
            ast.accept(graphViz2);
            graphViz2.dumpGraph("./tree.dot");
            GraphDisplayer.displayDotFile("./tree.dot");
        }

        GestionnaireErreur.getInstance().afficherErreurs();
        if(GestionnaireErreur.getNbErreur() > 0) {
            return 1;
        }

        AsmVisitor asmvisitor = new AsmVisitor();
        ast.accept(asmvisitor);

        GestionnaireErreur.getInstance().afficherErreurs();
        if(GestionnaireErreur.getNbErreur() > 0) {
            return 1;
        }

        //String file_out_name = source.substring(0, source.lastIndexOf('.')) + ".s";
        String file_out_name = destination;
        new File(file_out_name).createNewFile();
        FileWriter file_out_wr = new FileWriter(file_out_name);
        file_out_wr.write(((Program) ast).getAsm());
        file_out_wr.close();

        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN_BOLD = "\033[1;32m";
        System.out.println(ANSI_GREEN_BOLD + "Compilation terminée avec succès !" + ANSI_RESET);

        return 0;
    }
}