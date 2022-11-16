package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tools.CustomParser;
import org.junit.Assert;

import java.io.File;

public class TigerAssert extends Assert {
    public final static String RESSOURCE_FOLDER = "src/test/ressources/";

    public static void assertNbErreurs(String path, int expected) {
        File file = new File(RESSOURCE_FOLDER + path);
        CustomParser.parse(file);
        assertEquals("Mauvais nombre d'erreurs", expected, GestionnaireErreur.getNbErreur());
    }

    public static void assertCorrect(String path){
        assertNbErreurs(path, 0);
    }


}
