package eu.tn.chaoscompiler;

import org.junit.Test;

public final class TestLexicalError extends TigerTest {
    @Test public void testErreurSyntaxique(){
        TigerAssert.assertNbErreurs("erreursSyntaxique/trace_matricetrace_matrice.tig", 1);
    }

}
