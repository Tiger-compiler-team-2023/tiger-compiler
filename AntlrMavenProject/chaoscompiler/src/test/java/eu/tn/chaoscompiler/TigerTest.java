package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import org.junit.Before;

public abstract class TigerTest {

    private TDScontroller tdScontroller;

    /**
     * Méthode de préparation des tests unitaires.
     * Les classes filles héritent du comportement de
     * l'annotation Before (@{@link org.junit.Before})
     */
    @Before
    public void setUp() {
        GestionnaireErreur.reset();
        tdScontroller = new TDScontroller();
    }
}
