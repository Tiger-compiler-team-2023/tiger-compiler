package eu.tn.chaoscompiler.tdstool.tds;

import java.util.HashMap;

import eu.tn.chaoscompiler.tdstool.variable.Variable;

public class TDSroot implements TDS {
    protected HashMap<String, Variable> hm;

    public TDSroot() {
        this.hm = new HashMap<String, Variable>();
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
        return "\n{[TDSroot]" + this.hm.toString() + "\n}";
    }
}
