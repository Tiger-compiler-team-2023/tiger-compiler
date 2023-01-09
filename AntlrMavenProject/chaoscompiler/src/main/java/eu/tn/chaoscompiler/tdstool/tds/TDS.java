package eu.tn.chaoscompiler.tdstool.tds;

import eu.tn.chaoscompiler.tdstool.variable.Variable;

public interface TDS {
    Variable findType(String id);
    Boolean existsType(String id);

    Variable findVari(String id);
    Boolean existsVari(String id);

    void add(Variable var);

    @Override
    String toString();
}