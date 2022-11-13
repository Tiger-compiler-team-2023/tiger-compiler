package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tools.CustomParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class IncorrectTests {
    @Before
    public void setUp() throws Exception {
        GestionnaireErreur.reset();
    }

    // Pour la démo
    @Test
    public void firstTest() throws IOException {
        String path = "src/test/ressources/incorrect/01_test";
        File file = new File(path);
        CustomParser.parse(file);
        Assert.assertEquals("Mauvais nombre d'erreurs", GestionnaireErreur.getNbErreur(), 1);
    }

    @Test
    public void secondTest() throws IOException {
        String path = "src/test/ressources/correct/01_test";

        File file = new File(path);
        CustomParser.parse(file);
        Assert.assertEquals("Mauvais nombre d'erreurs", GestionnaireErreur.getNbErreur(), 0);
    }

    @Test
    public void troisiemeTest() throws IOException {
        String path = "src/test/ressources/correct/ExponentiationRapide.tiger";

        File file = new File(path);
        CustomParser.parse(file);
        Assert.assertEquals("Mauvais nombre d'erreurs", GestionnaireErreur.getNbErreur(), 0);
    }

    @Test
    public void quatriemeTest() throws IOException {
        String path = "src/test/ressources/correct/Inegalites.tiger";

        File file = new File(path);
        CustomParser.parse(file);
        //Assert.assertEquals("Mauvais nombre d'erreurs", GestionnaireErreur.getNbErreur(), 0);
    }
}