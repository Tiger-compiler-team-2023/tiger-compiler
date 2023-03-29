package eu.tn.chaoscompiler.tdstool.variable;

import java.util.ArrayList;
import java.util.List;

public class ArrayRecordType extends ArrayType {

    List<String> fields;

    public ArrayRecordType(String id, List<String> fields) {
        super(id, Type.POINTER_TYPE);
        this.fields = fields;
    }

    public int getIndexOfField(String id) {
        return fields.indexOf(id);
    }

    public int getNbFields(){
        return fields.size();
    }
}