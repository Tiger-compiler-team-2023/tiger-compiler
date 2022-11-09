public class SyntaxError {

    public int line;
    public int charPositionInLine;
    public String msg;

    public SyntaxError(int line, int charPositionInLine, String msg) {
        this.line = line;
        this.charPositionInLine = charPositionInLine;
        this.msg = msg;
    }
}
