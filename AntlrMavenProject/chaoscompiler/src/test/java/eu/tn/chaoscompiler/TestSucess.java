package eu.tn.chaoscompiler;

import org.junit.Test;

/**
 * Tests unitaires portant sur la bonne création d'arbre syntaxique pour des programmes
 * sources correctes
 *
 */
public final class TestSucess extends TigerTest {
    @Test public void correctTests() {
        TigerAssert.assertCorrectSyntaxic("correct/matrice.tig");
    }

    @Test public void basicWhileLoop(){
        TigerAssert.assertCorrectSyntaxic("correct/while.tig");
    }

    @Test public void fastExponentationAlgo(){
        TigerAssert.assertCorrectSyntaxic("correct/ExponentiationRapide.tig");
    }

    @Test public void matrixManipulation(){
        TigerAssert.assertCorrectSyntaxic("correct/matrice.tig");
    }

    @Test public void stackManipulation(){
        TigerAssert.assertCorrectSyntaxic("correct/stack.tig");
    }

    @Test public void stringFormat(){
        TigerAssert.assertCorrectSyntaxic("correct/strings_format.tig");
    }

    @Test public void queenProblem(){
        TigerAssert.assertCorrectSyntaxic("correct/queen.tig");
    }




}
