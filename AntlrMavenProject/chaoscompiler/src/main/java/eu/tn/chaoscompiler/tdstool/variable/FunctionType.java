package eu.tn.chaoscompiler.tdstool.variable;

import java.util.ArrayList;

public class FunctionType extends Type {
    public ArrayList<Type> inTypes;
    public Type outType;

    public FunctionType(String id, ArrayList<Type> inTypes, Type outType) {
        super(id);
        this.inTypes = inTypes;
        this.outType = outType;
    }

    @Override
    public String toString() {
        return "\n\u001B[35m{[FunctionType]\u001B[0m" + super.toString() + ", inTypes: " + this.inTypes.toString()
                + ", outType: " + this.outType.toString() + "\n\u001B[35m}\u001B[0m";
    }
}
