package eu.tn.chaoscompiler.tdstool.tds;

import java.util.HashMap;

import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import eu.tn.chaoscompiler.tdstool.variable.Variable;

public class TDSroot implements TDS {
    protected HashMap<String, Type> hmType;
    protected HashMap<String, Value> hmVari;

    public TDSroot() {
        this.hmType = new HashMap<String, Type>();
        this.hmVari = new HashMap<String, Value>();
    }

    public Type findType(String id) {
        return this.hmType.get(id);
    }

    public Boolean existsType(String id) {
        return this.hmType.containsKey(id);
    }

    public Value findVari(String id) {
        return this.hmVari.get(id);
    }

    public Boolean existsVari(String id) {
        return this.hmVari.containsKey(id);
    }

    public void add(Variable var) {
        if (var instanceof Value) {
            this.hmVari.put(var.getId(), (Value) var);
        } else {
            this.hmType.put(var.getId(), (Type) var);
        }
    }

    @Override
    public String toString() {
        return "\n{[TDSroot]" + "Types:" + this.hmType.toString() + "Variables:" + this.hmVari.toString() + "\n}";
    }
}
