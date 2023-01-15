package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.ast.AstCreator;
import eu.tn.chaoscompiler.ast.ControlesSemantiques;
import eu.tn.chaoscompiler.errors.Errors;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tools.CustomParser;
import org.junit.Assert;

import java.io.File;

public class TigerAssert extends Assert {
    public final static String RESSOURCE_FOLDER = "src/test/ressources/";

    /**
     * Assert portant sur le nombre d'erreurs detecté lors de la création de l'arbre syntaxique
     *
     * @param path     lien relatif du fichier à partir de src/test/ressources/
     * @param expected nombre d'erreurs attendu
     */
    public static void assertNbErreurs(String path, int expected) {
        File file = new File(RESSOURCE_FOLDER + path);
        CustomParser.parseFromFile(file);
        assertEquals("Mauvais nombre d'erreurs syntaxique", expected, GestionnaireErreur.getNbErreur());
    }

    /**
     * Assert vérifiant qu'il n'y a aucune erreur lors de la création de l'arbre syntaxique
     *
     * @param path lien relatif du fichier à partir de src/test/ressources/
     */
    public static void assertCorrectSyntaxic(String path) {
        assertNbErreurs(path, 0);
    }

    /**
     * Assert vérifiant qu'une erreur sémantique est détectée. Si une erreur est détectée durant les étapes precedents,
     * l'assertion échoue
     *
     * @param input         programme à tester sous forme de String, les ' en entrée sont remplacés par des " pour simplifier
     * @param expectedError erreur attendue, null si aucune
     */
    public static void assertSemanticErrors(Errors expectedError, String input) {
        try {
            input = input.replace('\'', '"');
            var chaosParser = CustomParser.parseFromString(input);
            assertEquals("Test sémantique | Aucune erreur syntaxique attendue", 0, GestionnaireErreur.getNbErreur());
            Ast ast = chaosParser.program().accept(new AstCreator());
            assertEquals("Test sémantique | Aucune erreur attendue lors de la création de l'AST", 0, GestionnaireErreur.getNbErreur());
            ast.accept(new ControlesSemantiques());


            if (expectedError == null) {
                assertEquals("Test sémantique | Aucune erreur sémantique attendue", 0, GestionnaireErreur.getNbErreur());
            } else {
                assertTrue("Test sémantique | L'erreur attendue n'est pas présente",
                        GestionnaireErreur.getInstance().getErrors()
                                .stream().anyMatch(e -> e.getErrorId() == expectedError));
            }
            GestionnaireErreur.reset();
        } catch (AssertionError e) {
            System.out.println("Erreur attendue : " + expectedError);
            System.out.print("Liste des erreurs obtenues : ");
            GestionnaireErreur.getInstance().getErrors().forEach(err -> System.out.print(err.getErrorId().name() + ", "));
            throw (e);
        }
    }

    /**
     * Assert vérifiant qu'aucune erreur sémantique est détectée
     *
     * @param input programme à tester sous forme de String, les ' en entrée sont remplacés par des " pour simplifier
     */
    public static void assertCorrectSemantic(String input) {
        assertSemanticErrors(null, input);
    }

}
