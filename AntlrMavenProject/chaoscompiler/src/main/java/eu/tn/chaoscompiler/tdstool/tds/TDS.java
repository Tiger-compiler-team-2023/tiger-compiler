package eu.tn.chaoscompiler.tdstool.tds;

import eu.tn.chaoscompiler.tdstool.variable.Variable;

public interface TDS {
  public Variable find(String id);

  public Boolean exists(String id);

  public void add(Variable var);

  @Override
  String toString();
}