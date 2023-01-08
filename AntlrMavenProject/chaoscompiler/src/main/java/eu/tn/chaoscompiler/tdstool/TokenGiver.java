package eu.tn.chaoscompiler.tdstool;

public class TokenGiver {
    private static int last;

    public static void reset() {
        last = 0;
    }

    public static void reset(int start) {
        last = start;
    }

    public static int getToken() {
        last++;
        return last;
    }
}
