package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.Errors;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Pour les fichiers qui sont correctement analysés par le parser mais,
 * qui lancent des erreurs sémantiques
 * TODO : ajouter la detection des erreurs sémantiques quand on sera à cette étape
 */
public final class TestSemanticError extends TigerTest{

    @Test public void testA() {
        TigerAssert.assertCorrectSemantic(
                "let var N := 8\n"
                        + "type intArray = array of string\n"
                        + "var row := intArray [ 2+4 ] of \"sdq\" in end");
    }

    @Test public void testB() {
        TigerAssert.assertSemanticErrors(Errors.BAD_ARRAY_INIT_VALUE,
                "let var N := 8\n"
                        + "type intArray = array of string\n"
                        + "var row := intArray [ 2+4 ] of 1 in end");
    }
}
