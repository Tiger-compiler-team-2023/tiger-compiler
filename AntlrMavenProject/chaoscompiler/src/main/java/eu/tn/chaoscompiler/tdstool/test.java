package eu.tn.chaoscompiler.tdstool;

import java.util.ArrayList;

import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.FunctionType;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;

public class test {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";

    public static void main(String[] args) {
        /*
        System.out.println(ANSI_BLUE + "[TEST] Testing counter." + ANSI_RESET);
        for (int i = 0; i < 10; i++) {
            System.out.println(TokenGiver.getToken());
        }
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        TokenGiver.reset();

        System.out.println(ANSI_BLUE + "[TEST] Testing creatind a TDScontroller." + ANSI_RESET);
        TDScontroller my_tds = new TDScontroller();
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        System.out.println(ANSI_BLUE + "[TEST] Adding global var to my_tds." + ANSI_RESET);
        my_tds.add(new Type());
        my_tds.add(new Type("void"));
        my_tds.add(new Type("int"));
        my_tds.add(new Type("string"));
        ArrayList<Type> inType = new ArrayList<Type>();
        inType.add(my_tds.getTypeOfId("string"));
        my_tds.add(new Value(new FunctionType("__PRINT__",
                inType,
                my_tds.getTypeOfId("void")), "print"));
        System.out.println(my_tds.toString());
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        System.out.println(ANSI_BLUE + "[TEST] Reading a 'let', entering a local scope.." + ANSI_RESET);
        my_tds.down();
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        System.out.println(ANSI_BLUE + "[TEST] Adding a local var ('my_var') to my_tds." + ANSI_RESET);
        my_tds.add(new Value(my_tds.getTypeOfId("int"), "my_var"));
        System.out.println(my_tds.toString());
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        System.out.println(ANSI_BLUE + "[TEST] Checking if 'my_var' exists." + ANSI_RESET);
        System.out.println("Expecting: true.");
        System.out.println("Got  --> " + my_tds.existsVari("my_var") + ".");
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        System.out.println(ANSI_BLUE + "[TEST] Printing what 'my_var' is." + ANSI_RESET);
        System.out.println(my_tds.findVari("my_var").toString());
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        System.out.println(ANSI_BLUE + "[TEST] Checking if 'print' exists." + ANSI_RESET);
        System.out.println("Expecting: true.");
        System.out.println("Got  --> " + my_tds.existsVari("print") + ".");
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        System.out.println(ANSI_BLUE + "[TEST] Reading a 'end', exiting the local scope.." + ANSI_RESET);
        my_tds.up();
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

        System.out.println(ANSI_BLUE + "[TEST] Checking if 'my_var' exists." + ANSI_RESET);
        System.out.println("Expecting: false.");
        System.out.println("Got  --> " + my_tds.existsVari("my_var") + ".");
        System.out.println(ANSI_BLUE + "--> [TEST] Done." + ANSI_RESET);

         */
    }
}
