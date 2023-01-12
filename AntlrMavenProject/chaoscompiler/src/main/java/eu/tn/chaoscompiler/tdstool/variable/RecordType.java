package eu.tn.chaoscompiler.tdstool.variable;

import java.util.ArrayList;

public class RecordType extends Type {
    public ArrayList<Value> attributs;

    public RecordType(String id, ArrayList<Value> attributs) {
        super(id);
        this.attributs = attributs;
    }

    public RecordType(String id) {
        super(id);
        this.attributs = new ArrayList<Value>();
    }

    public void addAttribut(Value val) {
        this.attributs.add(val);
    }

    public Value getAttribut(String id) {
        for (Value v:this.attributs) {
            if (v.getId().equals(id)) {
                return v ;
            }
        }
        return null ;
    }

    @Override
    public String toString() {
        return "\n\u001B[35m{[ReccordType]\u001B[0m" + super.toString() + ", attributs: " + this.attributs.toString()
                + "\n\u001B[35m}\u001B[0m";
    }
}
