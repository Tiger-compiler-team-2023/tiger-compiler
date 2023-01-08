package eu.tn.chaoscompiler.tdstool.variable;

public interface Variable {
    public Type getType();

    public String getId();

    public int getToken();

    @Override
    String toString();
}
