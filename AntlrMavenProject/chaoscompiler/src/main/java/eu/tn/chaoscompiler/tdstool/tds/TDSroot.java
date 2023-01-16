package eu.tn.chaoscompiler.tdstool.tds;

import java.util.HashMap;

import eu.tn.chaoscompiler.tdstool.variable.FunctionType;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import eu.tn.chaoscompiler.tdstool.variable.Variable;

public class TDSroot implements TDS {
    public HashMap<String, Type> hmType;
    public HashMap<String, Value> hmVari;

    public TDSroot() {
        this.hmType = new HashMap<String, Type>();
        this.hmVari = new HashMap<String, Value>();
    }

    public HashMap<String, Type> getHmType() {
        return this.hmType;
    }
    public HashMap<String, Value> getHmVari() {
        return this.hmVari;
    }

    public Type findType(String id) {
        if (this instanceof TDSlocal) {
            Type t = this.hmType.get(id);
            if (t == null) {
                return ((TDSlocal) this).getFather().findType(id);
            } else {
                return t;
            }
        } else {
            return this.hmType.get(id);
        }
    }

    public Boolean existsType(String id) {
        if (this instanceof TDSlocal) {
            return this.hmType.containsKey(id) || ((TDSlocal) this).getFather().existsType(id);
        } else {
            return this.hmType.containsKey(id);
        }
    }

    public Value findVar(String id) {
        if (this instanceof TDSlocal) {
            Value t = this.hmVari.get(id);
            if (t == null) {
                return ((TDSlocal) this).getFather().findVar(id);
            } else {
                return t;
            }
        } else {
            return this.hmVari.get(id);
        }
    }

    public Boolean existsVar(String id) {
        if (this instanceof TDSlocal) {
            return this.hmVari.containsKey(id) || ((TDSlocal) this).getFather().existsVar(id);
        } else {
            return this.hmVari.containsKey(id);
        }
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

    public String toJSONString() {
        return null ;
    }
}
