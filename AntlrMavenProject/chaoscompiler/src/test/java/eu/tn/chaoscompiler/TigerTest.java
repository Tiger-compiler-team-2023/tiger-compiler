package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public abstract class TigerTest {
    @Before
    public void setUp() {
        GestionnaireErreur.reset();
    }
}
