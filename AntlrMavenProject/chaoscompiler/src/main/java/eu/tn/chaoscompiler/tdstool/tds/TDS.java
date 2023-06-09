package eu.tn.chaoscompiler.tdstool.tds;

import java.util.ArrayList;
import java.util.HashMap;

import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import eu.tn.chaoscompiler.tdstool.variable.Variable;

public interface TDS {
    public HashMap<String, Type> getHmType();
    public HashMap<String, Value> getHmVari();

    public ArrayList<TDS> getFullTDS();


    Type findType(String id);
    Boolean existsType(String id);

    Value findVar(String id);
    Boolean existsVar(String id);

    void add(Variable var);
    void add(Value val, Value fromRecord);

    void addParam(Value v) ;

    void addSub(TDS t);
    void setStartLine(int i);

    int getNbVar() ;

    int getDiffScopeFunc(String id) ;

    @Override
    String toString();

    String toJSONString(String child) ;
}