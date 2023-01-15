package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.Errors;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

/**
 * Pour les fichiers qui sont correctement analysés par le parser mais,
 * qui lancent des erreurs sémantiques
 * TODO : ajouter la detection des erreurs sémantiques quand on sera à cette étape
 */
public final class TestSemanticError extends TigerTest {

    public final String empty_let = " var place_holder := 0 ";

    @Before
    public void setUp() {
        super.setUp();
        TDScontroller.reset();
        TDScontroller.getInstance().add(new Value(Type.INT_TYPE, "int_1"));
        TDScontroller.getInstance().add(new Value(Type.INT_TYPE, "int_2"));
        TDScontroller.getInstance().add(new Value(Type.STRING_TYPE, "str_1"));
        TDScontroller.getInstance().add(new Value(Type.STRING_TYPE, "str_2"));

        Type custom_type_1 = new Type("custom_type_1");
        TDScontroller.getInstance().add(custom_type_1);
        TDScontroller.getInstance().add(new Value(custom_type_1, "custom_var_1"));
        TDScontroller.getInstance().add(new Value(custom_type_1, "custom_var_2"));
    }

    @Test
    public void arithmeticWithLiterals() {
        TigerAssert.assertCorrectSemantic("1 + 1");
        TigerAssert.assertCorrectSemantic("1 / 1");
        TigerAssert.assertCorrectSemantic("1 * 1");
        TigerAssert.assertCorrectSemantic("1 - 1");

        TigerAssert.assertSemanticErrors(Errors.BAD_OPERATION_TYPE, "1 + 'A' ");
        TigerAssert.assertSemanticErrors(Errors.BAD_OPERATION_TYPE, " 'A' + 'B' ");
    }

    @Test
    public void arithmeticWithVar() {
        TigerAssert.assertCorrectSemantic("1 + int_1");
        TigerAssert.assertCorrectSemantic("int_1 + int_2");
        TigerAssert.assertCorrectSemantic("int_1 + int_2");

        TigerAssert.assertSemanticErrors(Errors.BAD_OPERATION_TYPE, "str_2 + str_1 ");
        TigerAssert.assertSemanticErrors(Errors.BAD_OPERATION_TYPE, "1 + str_1 ");
    }

    @Test
    public void arithmeticDivisionByZero() {
        TigerAssert.assertSemanticErrors(Errors.ZERO_DIV, "1 / 0");
    }

    @Test
    public void basicAffect() {
        TigerAssert.assertCorrectSemantic("int_1 := 3");
        TigerAssert.assertCorrectSemantic("str_1 := 'a'");

        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, "int_1 := 'a' ");
        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, "str_1 := 32 ");

        TigerAssert.assertCorrectSemantic("let var x :int := 8 in end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, "let var y :int := 'STR' in end ");
    }

    @Test
    public void comparisons() {
        TigerAssert.assertCorrectSemantic("1 <= 2");
        TigerAssert.assertCorrectSemantic(" int_1 >= int_2");

        TigerAssert.assertCorrectSemantic(" 'a' < 'b' ");
        TigerAssert.assertCorrectSemantic(" str_1 > str_2 ");

        TigerAssert.assertSemanticErrors(Errors.INCOMPARABLE_TYPES, " 1 < 'A' ");
        TigerAssert.assertSemanticErrors(Errors.INCOMPARABLE_TYPES, " str_1 >= int_1 ");
        TigerAssert.assertSemanticErrors(Errors.INCOMPARABLE_TYPES, " custom_var_1 > custom_var_2 ");
    }

    @Test
    public void equality() {
        TigerAssert.assertCorrectSemantic(" int_1 <> int_2 ");
        TigerAssert.assertCorrectSemantic(" str_1 <> str_2 ");
        TigerAssert.assertCorrectSemantic(" custom_var_1 <> custom_var_2 ");

        TigerAssert.assertSemanticErrors(Errors.INCOMPARABLE_TYPES, " 1 = 'A' ");
        TigerAssert.assertSemanticErrors(Errors.INCOMPARABLE_TYPES, " str_1 <> int_1 ");
        TigerAssert.assertSemanticErrors(Errors.INCOMPARABLE_TYPES, " custom_var_1 = str_1 ");
    }

    @Test
    public void undeclaredThings() {
        TigerAssert.assertCorrectSemantic(" let var x := 9 in x := 42 end ");
        TigerAssert.assertSemanticErrors(Errors.UNDECLARED_VARIABLE, " let var x := 9 in str := 9 end ");

        TigerAssert.assertCorrectSemantic(" let function printhello() = print('Hello') in printhello() end ");
        TigerAssert.assertSemanticErrors(Errors.UNDECLARED_FUNCTION, " let var x := 0 in printhello() end ");

        TigerAssert.assertCorrectSemantic(" let type tab = array of int in end ");
        TigerAssert.assertSemanticErrors(Errors.ALREADY_DECLARED_TYPE, " let type tab = array of int type tab = array of string in end ");
    }
}
