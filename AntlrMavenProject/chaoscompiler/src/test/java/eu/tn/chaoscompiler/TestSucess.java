package eu.tn.chaoscompiler;

import org.junit.Test;

/**
 * Tests unitaires portant sur la bonne création d'arbre syntaxique pour des programmes
 * sources correctes
 *
 */
public final class TestSucess extends TigerTest {
    @Test public void correctTests() {
        TigerAssert.assertCorrect("correct/matrice.tig");
    }

    @Test public void basicWhileLoop(){
        TigerAssert.assertCorrect("correct/while.tig");
    }

    @Test public void fastExponentationAlgo(){
        TigerAssert.assertCorrect("correct/ExponentiationRapide.tig");
    }

    @Test public void matrixManipulation(){
        TigerAssert.assertCorrect("correct/matrice.tig");
    }

    @Test public void stackManipulation(){
        TigerAssert.assertCorrect("correct/stack.tig");
    }

    @Test public void stringFormat(){
        TigerAssert.assertCorrect("correct/strings_format.tig");
    }

    @Test public void queenProblem(){
        TigerAssert.assertCorrect("correct/queen.tig");
    }




}
