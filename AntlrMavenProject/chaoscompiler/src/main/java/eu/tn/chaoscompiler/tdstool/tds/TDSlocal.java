package eu.tn.chaoscompiler.tdstool.tds;

import java.util.HashMap;

import eu.tn.chaoscompiler.tdstool.variable.Variable;

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
