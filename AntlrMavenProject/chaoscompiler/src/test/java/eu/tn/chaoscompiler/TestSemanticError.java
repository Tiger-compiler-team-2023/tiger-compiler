package eu.tn.chaoscompiler;

import org.junit.Test;

import java.io.IOException;

/**
 * Pour les fichiers qui sont correctement analysés par le parser mais,
 * qui lancent des erreurs sémantiques
 * TODO : ajouter la detection des erreurs sémantiques quand on sera à cette étape
 */
public final class TestSemanticError extends TigerTest{
    @Test public void comparaisonStringInt() throws IOException {
        TigerAssert.assertCorrect("erreursSemantiques/comparaison.tig");
    }

    @Test public void undeclaredVarInLoop(){
        TigerAssert.assertCorrect("erreursSemantiques/for.tig");
    }


}
