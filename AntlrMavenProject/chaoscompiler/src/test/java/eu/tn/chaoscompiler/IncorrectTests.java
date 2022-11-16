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
        TigerAssert.assertNbErreurs("incorrect/01_test", 1);
    }

    @Test
    public void secondTest() throws IOException {
        TigerAssert.assertNbErreurs("correct/01_test", 0);
    }

    @Test
    public void troisiemeTest() throws IOException {
        TigerAssert.assertCorrect("correct/ExponentiationRapide.tiger");
    }

    @Test
    public void quatriemeTest() throws IOException {
        //TigerAssert.assertCorrect("correct/Inegalites.tiger");
    }
}
