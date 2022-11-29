import errors.GestionnaireErreur;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tools.CustomParser;

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
        String path = "src/test/ressources/incorrect/trace_matrice_incorrect";
        File file = new File(path);
        CustomParser.parse(file);
        Assert.assertEquals("Mauvais nombre d'erreurs", GestionnaireErreur.getNbErreur(), 1);
    }

    @Test
    public void secondTest() throws IOException {
        String path = "src/test/ressources/correct/trace_matrice";

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
        Assert.assertEquals("Mauvais nombre d'erreurs", GestionnaireErreur.getNbErreur(), 0);
    }
}