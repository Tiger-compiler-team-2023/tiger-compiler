package eu.tn.chaoscompiler.tdstool.tds;

import java.util.HashMap;

import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import eu.tn.chaoscompiler.tdstool.variable.Variable;

public interface TDS {
    public HashMap<String, Type> getHmType();
    public HashMap<String, Value> getHmVari();


    Type findType(String id);
    Boolean existsType(String id);

    Value findVar(String id);
    Boolean existsVar(String id);

    void add(Variable var);

    void addParam(Value v) ;

    void addSub(TDS t);
    void setStartLine(int i);

    @Override
    String toString();

    String toJSONString(String child) ;
}