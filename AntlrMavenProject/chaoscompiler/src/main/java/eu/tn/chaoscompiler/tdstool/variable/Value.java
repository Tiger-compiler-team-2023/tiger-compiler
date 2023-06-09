package eu.tn.chaoscompiler.tdstool.variable;

import eu.tn.chaoscompiler.tdstool.TokenGiver;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Value implements Variable {
    protected Type type;
    protected String id;
    protected int token;
    protected int dpl ;
    public int depth;

    public Value(Type type, String id) {
        this.type = type;
        this.id = id;
        this.token = TokenGiver.getToken();
    }

    public Value(Type type, String id, int token) {
        this.type = type;
        this.id = id;
        this.token = token;
    }

    public Type getType() {
        return this.type;
    }

    public String getId() {
        return this.id;
    }

    public int getToken() {
        return this.token;
    }

    public int getDpl() {
        if (this.type instanceof FunctionType)
            return 0;
        else
            return this.dpl;
    }

    @Override
    public String toString() {
        return "\n\u001B[32m{[Value]\u001B[0mtype: " + this.type.toString() + ", id: \u001B[46m" + this.id
                + "\u001B[0m, token: \u001B[43m" + this.token + "\u001B[0m\n\u001B[32m}\u001B[0m";
    }

    public String toJSONString() {

        String s  = "" ;
        s += "{ " ;
        s += "\"class\" : \"Value\",\n" ;
        s += "\"token\" : \"" + Integer.toString(token) + "\",\n" ;
        s += "\"id\" : \"" + id + "\",\n" ;

        if (type instanceof FunctionType tf) {
            s += "\"type\" : " + type.toJSONString() ;
        }
        else {
            s += "\"type\" : \"" + type.getId() + "\"" ;
        }

        s += " }" ;

        return s ;
    }
}
