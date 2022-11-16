package eu.tn.chaoscompiler.errors;

public record SyntaxError(int line, int charPositionInLine, String msg) {

}
