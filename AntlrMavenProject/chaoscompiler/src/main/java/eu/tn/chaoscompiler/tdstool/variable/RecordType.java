package eu.tn.chaoscompiler.tdstool.variable;

import java.util.ArrayList;

public class RecordType extends Type {
    public ArrayList<Value> attributs;

    public RecordType(String id, ArrayList<Value> attributs) {
        super(id);
        this.attributs = attributs;
    }

    @Override
    public String toString() {
        return "\n\u001B[35m{[ReccordType]\u001B[0m" + super.toString() + ", attributs: " + this.attributs.toString()
                + "\n\u001B[35m}\u001B[0m";
    }
}
