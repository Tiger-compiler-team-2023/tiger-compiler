package eu.tn.chaoscompiler.tdstool.tds;

import eu.tn.chaoscompiler.tdstool.variable.Variable;

public interface TDS {
    Variable find(String id);

    Boolean exists(String id);

    void add(Variable var);

    @Override
    String toString();
}