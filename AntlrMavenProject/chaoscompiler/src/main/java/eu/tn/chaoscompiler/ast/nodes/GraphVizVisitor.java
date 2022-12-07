package eu.tn.chaoscompiler.ast.nodes;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.declarations.FunctionDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.VariableDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.ArrayTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.NoRecordTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.declarations.types.RecordTypeDeclaration;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.For;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.IfThenElse;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.Let;
import eu.tn.chaoscompiler.ast.nodes.looporcondition.While;
import eu.tn.chaoscompiler.ast.nodes.operators.*;
import eu.tn.chaoscompiler.ast.nodes.references.*;
import eu.tn.chaoscompiler.ast.nodes.terminals.Id;
import eu.tn.chaoscompiler.ast.nodes.terminals.IntegerNode;
import eu.tn.chaoscompiler.ast.nodes.terminals.StringNode;

import java.io.FileOutputStream;
import java.io.IOException;

public class GraphVizVisitor implements AstVisitor<String> {

    private int state;
    private String nodeBuffer;
    private String linkBuffer;

    public GraphVizVisitor() {
        this.state = 0;
        this.nodeBuffer = "digraph \"ast\"{\n\n\tnodesep=1;\n\tranksep=1;\n\n";
        this.linkBuffer = "\n";
    }

    public void dumpGraph(String filepath) throws IOException {
        FileOutputStream output = new FileOutputStream(filepath);
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.nodeBuffer).append(this.linkBuffer).append("\n}");
        byte[] strToBytes = buffer.toString().getBytes();
        output.write(strToBytes);
        output.close();
    }

    private String nextState() {
        int returnedState = this.state;
        this.state++;
        return "N" + returnedState;
    }

    private void addTransition(String from, String dest) {
        this.linkBuffer += String.format("\t%s -> %s; \n", from, dest);

    }

    private void addNode(String node, String label) {
        this.nodeBuffer += String.format("\t%s [label=\"%s\", shape=\"box\"];\n", node, label);

    }

    @Override
    public String visit(Program node) {
        String nodeIdentifier = this.nextState();
        String instructionsState = node.expression.accept(this);

        this.addNode(nodeIdentifier, "Program");
        this.addTransition(nodeIdentifier, instructionsState);
        return nodeIdentifier;
    }

    @Override
    public String visit(Sequence node) {
        return null;
    }

    @Override
    public String visit(FunctionDeclaration node) {
        return null;
    }

    @Override
    public String visit(VariableDeclaration node) {
        return null;
    }

    @Override
    public String visit(ArrayTypeDeclaration node) {
        return null;
    }

    @Override
    public String visit(NoRecordTypeDeclaration node) {
        return null;
    }

    @Override
    public String visit(RecordTypeDeclaration node) {
        return null;
    }

    @Override
    public String visit(For forExpr) {
        return null;
    }

    @Override
    public String visit(IfThenElse ifThenElseExpr) {
        return null;
    }

    @Override
    public String visit(Let letExpr) {
        return null;
    }

    @Override
    public String visit(While whileExpr) {
        return null;
    }

    @Override
    public String visit(Addition node) {
        return null;
    }

    @Override
    public String visit(Affect node) {
        return null;
    }

    @Override
    public String visit(And node) {
        return null;
    }

    @Override
    public String visit(Division node) {
        return null;
    }

    @Override
    public String visit(Equals node) {
        return null;
    }

    @Override
    public String visit(Inferior node) {
        return null;
    }

    @Override
    public String visit(InferiorOrEquals node) {
        return null;
    }

    @Override
    public String visit(Multiplication node) {
        return null;
    }

    @Override
    public String visit(Negation node) {
        return null;
    }

    @Override
    public String visit(NotEquals node) {
        return null;
    }

    @Override
    public String visit(Or node) {
        return null;
    }

    @Override
    public String visit(Soustraction node) {
        return null;
    }

    @Override
    public String visit(Superior node) {
        return null;
    }

    @Override
    public String visit(SuperiorOrEquals node) {
        return null;
    }

    @Override
    public String visit(ArrayAccess node) {
        return null;
    }

    @Override
    public String visit(ArrayAssign node) {
        return null;
    }

    @Override
    public String visit(FieldCreate node) {
        return null;
    }

    @Override
    public String visit(FieldDeclaration node) {
        return null;
    }

    @Override
    public String visit(FunctionCall node) {
        return null;
    }

    @Override
    public String visit(ParameterList node) {
        return null;
    }

    @Override
    public String visit(RecordAccess node) {
        return null;
    }

    @Override
    public String visit(RecordCreate node) {
        return null;
    }

    @Override
    public String visit(Id node) {
        return null;
    }

    @Override
    public String visit(IntegerNode node) {
        return null;
    }

    @Override
    public String visit(StringNode node) {
        return null;
    }
}
