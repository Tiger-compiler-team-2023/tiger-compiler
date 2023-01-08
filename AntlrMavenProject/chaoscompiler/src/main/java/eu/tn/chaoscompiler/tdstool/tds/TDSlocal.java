package eu.tn.chaoscompiler.tdstool.tds;

import java.util.HashMap;

import eu.tn.chaoscompiler.tdstool.variable.Variable;

public class TDSlocal implements TDS {
    protected HashMap<String, Variable> hm;
    protected TDS father;

    public TDSlocal(TDS father) {
        this.hm = new HashMap<String, Variable>();
        this.father = father;
    }

    public TDS getFather() {
        return this.father;
    }

    public Variable find(String id) {
        return this.hm.get(id);
    }

    public Boolean exists(String id) {
        return this.hm.containsKey(id);
    }

    public void add(Variable var) {
        this.hm.put(var.getId(), var);
    }

    @Override
    public String toString() {
        return "\n{[TDSlocal]" + this.hm.toString() + " father:" + this.father.toString() + "\n}";
    }
}
