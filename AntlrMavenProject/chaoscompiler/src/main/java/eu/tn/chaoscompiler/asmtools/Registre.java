package eu.tn.chaoscompiler.asmtools;

public enum Registre {
    x1(1),
    x2(2),
    x3(2),
    x4(4),
    x5(5),
    x6(6),
    x7(7),
    x8(8),
    x9(9),
    x10(10),
    x11(11),
    x12(12),
    x13(13),
    x14(14),
    x15(15),
    x16(16),
    x17(17),
    x18(18),
    x19(19),
    x20(20),
    x21(21),
    x22(22),
    x23(23),
    x24(24),
    x25(25),
    x26(26),
    x27(27),
    ch_stat(28),
    FP(29),
    LR(30),
    SP(31, "SP");

    private int o;
    private String n;

    private Registre(int o) {
        this.o = o;
    }

    private Registre(int o, String n) {
        this.o = o;
        this.n = n;
    }

    public int o() {
        return this.o;
    }

    public String n() {
        if (this.n != null)
            return this.n;
        else
            return "x" + this.o;
    }
}
