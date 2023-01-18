package eu.tn.chaoscompiler.tdstool.variable;

import lombok.NoArgsConstructor;

public class NotYetDeclarated extends Type {
    public NotYetDeclarated(String id) {
        super(id);
    }

    @Override
    public String toJSONString() {

        String s = "";
        s += "{ ";
        s += "\"class\" : \"NotYedDeclarated\",\n";
        s += "\"token\" : \"" + Integer.toString(token) + "\",\n";
        s += "\"id\" : \"" + id + "\"";
        s += " }";

        return s;
    }
}
