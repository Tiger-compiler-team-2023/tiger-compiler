package eu.tn.chaoscompiler.asmtools;

public enum Arm64Functions {

    INT_NEG("ari_int_neg", 1, 1),
    INT_ADD("ari_int_add", 2, 1),
    INT_SUB("ari_int_sub", 2, 1),
    INT_MUL("ari_int_mul", 2, 1),
    INT_DIV("ari_int_div", 2, 1),
    LOG_AND("ari_log_and", 2, 1),
    LOG_OR("ari_log_or", 2, 1),
    INT_EQ("ari_int_eq", 2, 1),
    INT_NE("ari_int_ne", 2, 1),
    INT_GT("ari_int_gt", 2, 1),
    INT_LT("ari_int_lt", 2, 1),
    INT_GE("ari_int_ge", 2, 1),
    INT_LE("ari_int_le", 2, 1),
    STR_EQ("ari_str_eq", 2, 1),
    STR_NE("ari_str_ne", 2, 1),
    STR_GT("ari_str_gt", 2, 1),
    STR_LT("ari_str_lt", 2, 1),
    STR_GE("ari_str_ge", 2, 1),
    STR_LE("ari_str_le", 2, 1),
    PRINT_INT10("print_int10", 1, 0),
    PRINT_INT16("print_int16", 1, 0),
    INPUT_INT10("intput_int10", 0, 1);

    public final String functionName;
    public final int nbArgs;
    public final int nbRes;

    private Arm64Functions(String functionName, int nbArgs, int nbRes) {
        this.functionName = functionName;
        this.nbArgs = nbArgs;
        this.nbRes = nbRes;
    }

    public String call() {
        String res = "";
        res += "bl ";
        res += this.functionName;
        res += " // [";
        res += nbArgs;
        res += "] -> [";
        res += nbRes;
        res += "]\n";
        return res;
    }

}
