package eu.tn.chaoscompiler.tdstool.variable;

public class ArrayValue extends Value {
    public int size;
    
    public ArrayValue(Type type, String id, int size) {
        super(type, id);
        this.size = size;
    }
}
