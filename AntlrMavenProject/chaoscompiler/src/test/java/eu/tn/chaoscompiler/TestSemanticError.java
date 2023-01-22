package eu.tn.chaoscompiler;

import eu.tn.chaoscompiler.errors.Errors;
import eu.tn.chaoscompiler.tdstool.tds.TDS;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public final class TestSemanticError extends TigerTest {

    @Before
    public void setUp() {
        super.setUp();
        TDScontroller.reset();
        // Ajout de données pour les tests
        TDScontroller.getInstance().add(new Value(Type.INT_TYPE, "int_1"));
        TDScontroller.getInstance().add(new Value(Type.INT_TYPE, "int_2"));
        TDScontroller.getInstance().add(new Value(Type.STRING_TYPE, "str_1"));
        TDScontroller.getInstance().add(new Value(Type.STRING_TYPE, "str_2"));

        Type custom_type_1 = new Type("custom_type_1");
        TDScontroller.getInstance().add(custom_type_1);
        TDScontroller.getInstance().add(new Value(custom_type_1, "custom_var_1"));
        TDScontroller.getInstance().add(new Value(custom_type_1, "custom_var_2"));

        TDScontroller.getInstance().add(new RecordType("rec")
                .addAttribut(new Value(Type.INT_TYPE, "x")).addAttribut(new Value(Type.STRING_TYPE, "y")));

        TDScontroller.getInstance().add(new ArrayType("arr", Type.INT_TYPE));
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

    @Test
    public void recordsCreation() {
        TigerAssert.assertCorrectSemantic(" let type X={x:int} var r := X{x=1} in end ");
        TigerAssert.assertCorrectSemantic(" let var r := rec {x=1, y='a'} in end ");

        TigerAssert.assertSemanticErrors(Errors.INEXISTING_FIELD, " let var r := rec {x=1, y='a', z=3} in end ");
        TigerAssert.assertSemanticErrors(Errors.MISSING_FIELD, " let var r := rec {x=1} in end ");
        TigerAssert.assertSemanticErrors(Errors.DUPLICATE_FIELD, " let var r := rec {x=1, y='a', x=2} in end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_ARGUMENT_TYPE, " let var r := rec {x=1, y=3} in end ");
        TigerAssert.assertSemanticErrors(Errors.NO_RECORD_TYPE, " let var r := custom_type_1 {x=1, y='a'} in end ");
    }

    @Test
    public void recordsAccess() {
        TigerAssert.assertCorrectSemantic(" let var r := rec {x=1, y='a'} in r.x := 2 end ");
        TigerAssert.assertCorrectSemantic(" let var r := rec {x=1, y='a'} in r.y := 'b' end ");
        TigerAssert.assertCorrectSemantic(" let var r := rec {x=1, y='a'} in r := nil end ");

        TigerAssert.assertSemanticErrors(Errors.INEXISTING_FIELD, " let var r := rec {x=1, y='a'} in r.z := 2 end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, " let var r := rec {x=1, y='a'} in r.x := 'a' end ");
        TigerAssert.assertSemanticErrors(Errors.NO_RECORD_TYPE, " let var r := custom_type_1 {x=1, y='a'} in r.x := 2 end ");
    }

    @Test
    public void nestedRecord() {
        // Record in record
        TigerAssert.assertCorrectSemantic("let type rec = {I:int} type rec2 = {R:rec} var X := rec2{R= rec{I=1} } in end");
        TigerAssert.assertCorrectSemantic("let type rec = {I:int} type rec2 = {R:rec} var X := rec2{R=nil} in end");
        TigerAssert.assertCorrectSemantic("let type rec = {I:int} type rec2 = {R:rec} var X := rec2{R= rec{I=1} } " +
                " in X.R.I := 42;  X.R := nil;  X := nil end");

        TigerAssert.assertSemanticErrors(Errors.UNDECLARED_VARIABLE, "let type rec = {I:int} type rec2 = {R:rec} " +
                "var X := rec2{R= rec{I=1} } in X.R.I := nil end");
    }

    @Test
    public void selfRecursiveRecord() {
        // Récursivité simple
        TigerAssert.assertCorrectSemantic(" let type rec2 = {a:rec2} in end ");
        TigerAssert.assertCorrectSemantic(" let type rec2 = {a:rec2} var X:=rec2{a=nil} in end ");
        TigerAssert.assertCorrectSemantic(" let type rec2 = {a:rec2} var X:=rec2{a= rec2{a=nil}} in end ");
        TigerAssert.assertCorrectSemantic(" let type rec2 = {a:rec2} var X:=rec2{a= rec2{a=rec2{a=nil}}} in end ");
        TigerAssert.assertCorrectSemantic(" let type rec2 = {a:rec2} var X:=rec2{a= rec2{a=rec2{a=nil}}}" +
                " in X.a.a.a = X.a end ");
    }

    @Test
    public void mutualRecursiveRecord() {
        // Récursivité mutuelle
        TigerAssert.assertCorrectSemantic("let type R1 = {x:R2} type R2 = {y:R1} var X := R1{x= nil} in end");
        TigerAssert.assertCorrectSemantic("let type R1 = {x:R2} type R2 = {y:R1} var X := R1{x= R2{y= nil} } in end");
        TigerAssert.assertCorrectSemantic("let type R1 = {x:R2} type R2 = {y:R1} var X := R1{x= R2{y= R1{x= nil}} } in end");
        TigerAssert.assertCorrectSemantic("let type R1 = {x:R2} type R2 = {y:R1} var X := R1{x= nil} " +
                "in X.x.y := X.x.y.x.y end");

        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, "let type R1 = {x:R2} type R2 = {y:R1} var X := R1{x= nil} " +
                "in X.x.y := X.x.y.x end");
    }

    @Test
    public void arrayCreate() {
        TigerAssert.assertCorrectSemantic(" let type X = array of int var x := X [1] of 5 in end ");
        TigerAssert.assertCorrectSemantic(" let var x := arr [1] of 5 in end ");

        TigerAssert.assertSemanticErrors(Errors.INT_EXPECTED, " let var x := arr ['a'] of 5 in end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_ARRAY_INIT_VALUE, " let var x := arr [3] of 'a' in end ");
        TigerAssert.assertSemanticErrors(Errors.NO_ARRAY_TYPE, " let var x := custom_type_1 [3] of 'a' in end ");
    }

    @Test
    public void arrayAccess() {
        String start = "let var x := arr [1] of 0 in ";
        TigerAssert.assertCorrectSemantic(start + "x[0] := 2 end ");
        TigerAssert.assertCorrectSemantic(start + "x[0] := x[0] + 1 end ");
        TigerAssert.assertCorrectSemantic(start + "x := arr[5] of 8 end ");

        TigerAssert.assertSemanticErrors(Errors.INT_EXPECTED, start + " x['a'] := 2 end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, start + " x[0] := 'a' end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, start + " x := 'a' end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, start + " x[0][0] := 'a' end ");
        TigerAssert.assertSemanticErrors(Errors.NO_ARRAY_TYPE, start + " int_1[0] := 2 end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_ARRAY_INIT_VALUE, start + "x := arr[5] of 'a' end ");
    }

    @Test
    public void nestedArray() {
        String nestedArr = " let type tab = array of int type tab2 = array of tab";
        TigerAssert.assertCorrectSemantic(nestedArr + " var v := tab2[5] of tab[5] of 0 in end");
        TigerAssert.assertCorrectSemantic(nestedArr + " var v := tab2[5] of tab[5] of 0 in v[3][2] = 5 end");
        TigerAssert.assertCorrectSemantic(nestedArr + " var v := tab2[5] of tab[5] of 0 in v[3] = v[2] end");

        TigerAssert.assertSemanticErrors(Errors.BAD_ARRAY_INIT_VALUE, nestedArr + " var v := tab2[5] of tab[5] of 'a' in end");
        TigerAssert.assertSemanticErrors(Errors.BAD_ARRAY_INIT_VALUE, nestedArr + " var v := tab2[5] of tab2[5] of 0 in end");
        TigerAssert.assertSemanticErrors(Errors.NO_ARRAY_TYPE, nestedArr + " var v := tab2[5] of tab[5] of 0 in v[3][2][1] = 5 end");
    }

    @Test
    public void mixedArrayAndRecord() {
        // Tableau de records
        TigerAssert.assertCorrectSemantic(" let type tab= array of rec var X := tab[42] of rec{x=1, y='a'} " +
                " in X[35].x := 28; X[30].y := 'b'; X[15] := X[10]; X[0] := nil end");

        TigerAssert.assertSemanticErrors(Errors.INEXISTING_FIELD, " let type tab= array of rec var X := tab[42] of rec{x=1, y='a'} in X[35].z := 28 end");
        TigerAssert.assertSemanticErrors(Errors.NO_RECORD_TYPE, " let var X := arr[3] of 42 in X[35].x := 28 end");

        // Records de tableaux
        TigerAssert.assertCorrectSemantic("let type REC = {ARR: arr} var X := REC{ARR= arr[10] of 42} in X.ARR[33] := X.ARR[34] end");
        TigerAssert.assertCorrectSemantic("let type REC = {ARR: arr} var X := REC{ARR= arr[10] of 42} in X.ARR := arr[1] of 4 end");
        TigerAssert.assertCorrectSemantic("let type REC = {ARR: arr} var X := REC{ARR= arr[10] of 42} in X.ARR[33] := X.ARR[34] end");

        TigerAssert.assertSemanticErrors(Errors.BAD_AFFECT_TYPE, "let type REC = {ARR: arr} var X := REC{ARR= arr[10] of 42} in X.ARR[33] := arr[1] of 4 end");
        TigerAssert.assertSemanticErrors(Errors.NO_ARRAY_TYPE, "let var X := rec{x= 1, y='A'} in X.x[33] := X.x[34] end");

        // Record de tableau de records de tableau  (╯°□°)╯︵ ┻━┻
        TigerAssert.assertCorrectSemantic("let " +
                " type A1 = array of int" +
                " type R1 = {X1 : A1}" +
                " type A2 = array of R1" +
                " type R2 = {X2 : A2}" +
                " var VAR := R2{X2 = A2[10] of R1{X1 = A1[54] of 42}}" +
                " in " +
                "   VAR.X2[1].X1[2] := 54" +
                " end");
        //   ┬─┬ノ( º _ ºノ)
    }

    @Test
    public void functionCreate() {
        String function = " function max(first:int, second:int):int = if first >= second then first else second ";
        TigerAssert.assertCorrectSemantic(" let" + function + "in end ");
        TigerAssert.assertCorrectSemantic(" let function foo() = print('Hello') in end ");

        TigerAssert.assertSemanticErrors(Errors.ALREADY_DECLARED, " let" + function + function + "in end ");
        TigerAssert.assertSemanticErrors(Errors.INCOMPATIBLE_FUNCTION_TYPE, " let function foo(x:int):int = 'X' in end ");
        TigerAssert.assertSemanticErrors(Errors.INCOMPATIBLE_FUNCTION_TYPE, " let function max(first:int, second:int):int = if first >= second then 'a' else second in end");
        TigerAssert.assertSemanticErrors(Errors.INCOMPATIBLE_FUNCTION_TYPE, " let function max(first:int, second:int):int = if first >= second then first else 'b' in end");
        TigerAssert.assertSemanticErrors(Errors.INCOMPATIBLE_FUNCTION_TYPE, " let function foo() = 42 in end ");
    }

    @Test
    public void functionAccess() {
        String function = " function max(first:int, second:int):int = if first >= second then first else second ";
        TigerAssert.assertCorrectSemantic(" let" + function + "in max(1, 2) end ");
        TigerAssert.assertCorrectSemantic(" let function foo() = print('Hello') in foo() end ");

        TigerAssert.assertSemanticErrors(Errors.UNDECLARED_FUNCTION, " let" + function + "in foo() end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_ARGUMENT_TYPE, " let" + function + "in max('a', 2) end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_ARGUMENT_NUMBER, " let" + function + "in max(1) end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_ARGUMENT_NUMBER, " let" + function + "in max(1, 2, 3) end ");
        TigerAssert.assertSemanticErrors(Errors.BAD_ARGUMENT_NUMBER, " let function foo() = print('Hello') in foo(1) end ");
    }

}
