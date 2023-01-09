package eu.tn.chaoscompiler.tdstool.tds;

import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import eu.tn.chaoscompiler.tdstool.variable.Variable;

public interface TDS {
    Type findType(String id);
    Boolean existsType(String id);

    Value findVari(String id);
    Boolean existsVari(String id);

    void add(Variable var);

    @Override
    String toString();
}