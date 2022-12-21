package eu.tn.chaoscompiler.tdstool.variable;

import eu.tn.chaoscompiler.tdstool.TokenGiver;

public class Type implements Variable {
  protected String id;
  protected int token;

  public Type() {
    this.id = "type";
    this.token = 0;
  }

  public Type(String id) {
    this.id = id;
    this.token = TokenGiver.getToken();
  }

  public Type getType() {
    return new Type();
  }

  public String getId() {
    return this.id;
  }

  public int getToken() {
    return this.token;
  }

  @Override
  public String toString() {
    return "\n\u001B[35m{[Type]\u001B[0m id: \u001B[46m" + this.id + "\u001B[0m, token: \u001B[43m" + this.token
        + "\u001B[0m\n\u001B[35m}\u001B[0m";
  }
}