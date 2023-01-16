package eu.tn.chaoscompiler.tdstool.variable;

public class ArrayType extends Type {
    public Type elementsType;

    public ArrayType(String id, Type elementsType) {
        super(id);
        this.elementsType = elementsType;
    }

    @Override
    public String toString() {
        return "\n\u001B[35m{[ArrayType]\u001B[0m" + super.toString() + ", elementsType: "
                + this.elementsType.toString() + "\n\u001B[35m}\u001B[0m";
    }

    @Override
    public String toJSONString() {
        String s = "" ;
        s += "\"class\" : \"ArrayType\",\n" ;
        s += "\"token\" : \"" + Integer.toString(token) + "\",\n" ;
        s += "\"id\" : \"" + this.getId() + "\",\n" ;
        s += "\"elementsType\" : \"" + elementsType.getId() + "\"" ;
        return s ;
    }
}
