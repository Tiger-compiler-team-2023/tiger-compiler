package eu.tn.chaoscompiler.tdstool.variable;

import lombok.Getter;

import java.util.ArrayList;

public class RecordType extends Type {
    @Getter public ArrayList<Value> attributs;

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
        return attributs.stream().filter(v -> v.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        attributs.forEach(b::append);
        return "\n\u001B[35m{[RecordType]\u001B[0m" + super.toString() + ", attributs: "
                + b.toString()
                + "\n\u001B[35m}\u001B[0m";
    }
}
