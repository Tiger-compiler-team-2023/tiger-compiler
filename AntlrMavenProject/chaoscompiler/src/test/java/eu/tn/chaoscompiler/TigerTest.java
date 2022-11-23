package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public abstract class TigerTest {

    /**
     * Méthode de préparation des tests unitaires.
     * Les classes filles héritent du comportement de
     * l'annotation Before (@{@link org.junit.Before})
     */
    @Before
    public void setUp() {
        GestionnaireErreur.reset();
    }
}
