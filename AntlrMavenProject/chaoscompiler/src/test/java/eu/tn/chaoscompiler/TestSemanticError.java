package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.Errors;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import org.junit.Before;
import org.junit.Test;

/**
 * Pour les fichiers qui sont correctement analysés par le parser mais,
 * qui lancent des erreurs sémantiques
 * TODO : ajouter la detection des erreurs sémantiques quand on sera à cette étape
 */
public final class TestSemanticError extends TigerTest {

    @Before
    public void setUp() {
        super.setUp();
        TDScontroller.getInstance().add(new Value(Type.INT_TYPE, "int_1"));
        TDScontroller.getInstance().add(new Value(Type.INT_TYPE, "int_2"));
        TDScontroller.getInstance().add(new Value(Type.STRING_TYPE, "str_1"));
        TDScontroller.getInstance().add(new Value(Type.STRING_TYPE, "str_2"));
    }

    @Test
    public void ArithmeticWithLiterals() {
        TigerAssert.assertCorrectSemantic("let var INT_VAR := 1  in end");

        TigerAssert.assertCorrectSemantic("1+1");
        TigerAssert.assertCorrectSemantic("1/1");
        TigerAssert.assertCorrectSemantic("1*1");
        TigerAssert.assertCorrectSemantic("1-1");

        TigerAssert.assertSemanticErrors(Errors.BAD_OPERATION_TYPE, "1 + 'A' ");
        TigerAssert.assertSemanticErrors(Errors.BAD_OPERATION_TYPE, " 'A' + 'B' ");
    }

    @Test
    public void ArithmeticWithVar() {
        TigerAssert.assertCorrectSemantic("1 + int_1");
        TigerAssert.assertCorrectSemantic("int_1 + int_2");
        TigerAssert.assertCorrectSemantic("int_1 + int_2");

        TigerAssert.assertSemanticErrors(Errors.BAD_OPERATION_TYPE, "str_2 + str_1 ");
        TigerAssert.assertSemanticErrors(Errors.BAD_OPERATION_TYPE, "1 + str_1 ");
    }

    @Test
    public void ArithmeticDivisionByZero() {
        TigerAssert.assertSemanticErrors(Errors.ZERO_DIV, "1/0");
    }
}
