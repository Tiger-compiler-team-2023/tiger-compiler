package eu.tn.chaoscompiler.tdstool.variable;

public class TypeRename extends Type {
    public Type oriType;

    public TypeRename(String id, Type origType) {
        super(id);
        this.oriType = origType;
    }

    @Override
    public String toString() {
        return "\n\u001B[35m{[TypeRename]\u001B[0m" + super.toString() + ", origType: "
                + this.oriType.toString() + "\n\u001B[35m}\u001B[0m";
    }
}