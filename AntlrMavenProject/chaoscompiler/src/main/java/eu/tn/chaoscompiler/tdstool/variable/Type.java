package eu.tn.chaoscompiler.tdstool.variable;

import eu.tn.chaoscompiler.tdstool.TokenGiver;
import lombok.Getter;

public class Type implements Variable {
    @Getter
    protected String id;
    @Getter
    protected int token;

    // Types de base
    public final static Type
            INT_TYPE = new Type("int"),
            STRING_TYPE = new Type("string"),
            VOID_TYPE = new Type("void"),
            INCR_TYPE = new Type("int"),
            POINTER_TYPE = new Type("pointer");

    @Override
    public boolean equals(Object obj) {
        if ((this == INT_TYPE && obj == INCR_TYPE) || (obj == INT_TYPE && this == INCR_TYPE)) {
            return true;
        } else {
            return super.equals(obj);
        }
    }

    public Type() {
        this.id = "type";
        this.token = 0;
    }

    public Type(String id) {
        this.id = id;
        this.token = TokenGiver.getToken();
    }

    public Type getType() {
        return this;
    }

    public boolean isIncr() {
        return this == INCR_TYPE;
    }

    @Override
    public String toString() {
        return "\n\u001B[35m{[Type]\u001B[0m id: \u001B[46m" + this.id + "\u001B[0m, token: \u001B[43m" + this.token
                + "\u001B[0m\n\u001B[35m}\u001B[0m";
    }

    @Override
    public String toJSONString() {

        String s = "";
        s += "{ ";
        s += "\"class\" : \"Type\",\n";
        s += "\"token\" : \"" + Integer.toString(token) + "\",\n";
        s += "\"id\" : \"" + id + "\"";
        s += " }";

        return s;
    }
}
