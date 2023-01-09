package eu.tn.chaoscompiler.tdstool.tds;

public class TDSlocal extends TDSroot {
    protected TDS father;

    public TDSlocal(TDS father) {
        super();
        this.father = father;
    }

    public TDS getFather() {
        return this.father;
    }

    @Override
    public String toString() {
        return "\n{[TDSlocal]" +"Types:" + this.hmType.toString() + "Variables:" + this.hmVari.toString() + "father:" + this.father.toString() + "\n}";
    }
}
