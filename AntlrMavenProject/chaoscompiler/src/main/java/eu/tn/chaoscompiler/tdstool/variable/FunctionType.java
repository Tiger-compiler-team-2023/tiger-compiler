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

    public FunctionType(String id, Type outType) {
        super(id);
        this.inTypes = new ArrayList<Type>();
        this.outType = outType;
    }

    public void addIn(Type in) {
        this.inTypes.add(in);
    }

    @Override
    public String toString() {
        return "\n\u001B[35m{[FunctionType]\u001B[0m" + super.toString() + ", inTypes: " + this.inTypes.toString()
                + ", outType: " + this.outType.toString() + "\n\u001B[35m}\u001B[0m";
    }

    @Override
    public String toJSONString() {
        StringBuilder s = new StringBuilder("");

        s.append("\"class\" : \"FunctionType\",\n") ;
        s.append("\"token\" : \"" + Integer.toString(token) + "\",\n") ;
        s.append("\"id\" : \"" + id + "\",\n") ;

        if (inTypes.size() > 0) {

            s.append("\"inType1\" : \"" + inTypes.get(0).getId() + "\",\n");

            for (int i = 1 ; i < inTypes.size() ; i++) {
                s.append("\"inType" + (i + 1) + "\" : \"" + inTypes.get(i).getId() + "\",\n");
            }

        }
        s.append("\"outType\" : \"" + outType.getId() + "\"");

        return s.toString() ;
    }
}
