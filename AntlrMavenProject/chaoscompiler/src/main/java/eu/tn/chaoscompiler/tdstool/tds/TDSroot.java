package eu.tn.chaoscompiler.tdstool.tds;

import java.util.ArrayList;
import java.util.HashMap;

import eu.tn.chaoscompiler.tdstool.variable.*;
import lombok.Getter;

public class TDSroot implements TDS {
    public HashMap<String, Type> hmType;
    public HashMap<String, Value> hmVari;
    public ArrayList<TDS> fullTDS;

    protected int nextSubTdsIndex;
    protected int startLine;

    protected int nextVarDpl;
    protected int nextParamDpl;

    public TDSroot() {
        this.hmType = new HashMap<String, Type>();
        this.hmVari = new HashMap<String, Value>();
        this.fullTDS = new ArrayList<TDS>();
        this.nextVarDpl = 2;
        this.nextParamDpl = -2;
        this.nextSubTdsIndex = 0;
    }

    public HashMap<String, Type> getHmType() {
        return this.hmType;
    }

    public HashMap<String, Value> getHmVari() {
        return this.hmVari;
    }

    @Override
    public ArrayList<TDS> getFullTDS() {
        return fullTDS;
    }

    public void addSub(TDS t) {
        this.fullTDS.add(t);
    }

    public void setStartLine(int i) {
        this.startLine = i;
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
        if (var instanceof Value val) {
            val.setDpl(this.nextVarDpl);
            this.hmVari.put(val.getId(), val);
            this.nextVarDpl++;
        } else {
            // Si le type a déjà été utilisé sous forme de NotYetDeclarated,
            // on remplace toutes les occurrences existantes dans les records
            // de la tds actuelle par le vrai type
            hmType.keySet().stream()
                    .map(key -> hmType.get(key))
                    .filter(ty -> ty instanceof RecordType)
                    .map(ty -> (RecordType) ty)
                    .forEach(rt -> rt.getAttributs().stream()
                            .filter(at -> at.getType().getId().equals(var.getId()))
                            .forEach(at -> at.setType((Type) var)));
            this.hmType.put(var.getId(), (Type) var);
        }
    }

    public void addParam(Value v) {
        v.setDpl(this.nextParamDpl);
        this.hmVari.put(v.getId(), v);
        this.nextParamDpl--;
    }

    public boolean hasNoDeclaredType() {
        return hmType.keySet().stream()
                .map(key -> hmType.get(key))
                .filter(ty -> ty instanceof RecordType)
                .map(ty -> (RecordType) ty)
                .anyMatch(rt -> rt.getAttributs().stream()
                        .anyMatch(at -> at.getType() instanceof NotYetDeclarated));
    }

    public TDS nextSubTds() {
        TDS next = this;
        if (this.nextSubTdsIndex >= 0 && this.nextSubTdsIndex < this.fullTDS.size()) {
            next = this.fullTDS.get(this.nextSubTdsIndex);
        }
        this.nextSubTdsIndex++;
        return next;
    }

    public int getNbVar() {
        return this.hmVari.size();
    }

    public int getDiffScopeFunc(String id) {
        if (this instanceof TDSlocal) {
            Type t = this.hmType.get(id);
            if (t == null) {
                return ((TDSlocal) this).getFather().getDiffScopeFunc(id) + 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "\n{[TDSroot]" + "Types:" + this.hmType.toString() + "Variables:" + this.hmVari.toString() + "\n}";
    }

    @Override
    public String toJSONString(String child) {
        StringBuilder s = new StringBuilder("");

        s.append("{ ");
        s.append("\"types\" : [ ");

        int count = 0;

        for (String key : hmType.keySet()) {
            s.append(childJSONString(hmType.get(key)));

            if (count < hmType.size() - 1) {
                s.append(",\n");
            }

            count++;
        }

        s.append(" ],\n");
        s.append("\"variables\" : [ ");

        count = 0;

        for (String key : hmVari.keySet()) {
            s.append(hmVari.get(key).toJSONString());

            if (count < hmVari.size() - 1) {
                s.append(",\n");
            }

            count++;
        }

        s.append(" ]");

        if ((child != null) && (!child.equals(""))) {
            s.append(",\n");
            s.append("\"tdsChild\" : ");
            s.append(child);
        }

        s.append(" }");

        return s.toString();
    }


    public String childJSONString(Type t) {
        //if (t instanceof ArrayType at) {
        //    return at.toJSONString();
        //} else if (t instanceof FunctionType ft) {
        //    return ft.toJSONString();
        //} else if (t instanceof RecordType rt) {
        //    return rt.toJSONString();
        //} else if (t instanceof TypeRename tr) {
        //    return tr.toJSONString();
        //}
        return t.toJSONString();
    }
}
