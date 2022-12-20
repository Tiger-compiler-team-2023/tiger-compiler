package eu.tn.chaoscompiler.graphViz;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.Sequence;
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
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Sequence") ;

        int n = node.instructions.size() ;
        for (int i = 0 ; i < n ; i++) {
            String instr = node.instructions.get(i).accept(this) ;
            this.addTransition(nodeIdentifier, instr);
        }

        return nodeIdentifier ;
    }

    @Override
    public String visit(FunctionDeclaration node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "FunctionDeclaration") ;

        for (FieldDeclaration arg:node.args) {
            String argId = arg.accept(this) ;
            this.addTransition(nodeIdentifier, argId) ;
        }

        String returnType = node.returnType.accept(this) ;
        this.addTransition(nodeIdentifier, returnType) ;

        String content = node.content.accept(this) ;
        this.addTransition(nodeIdentifier, content);

        return nodeIdentifier;
    }

    @Override
    public String visit(VariableDeclaration node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "VariableDeclaration") ;

        if (node.typeId != null) {
            String typeId = node.typeId.accept(this);
            this.addTransition(nodeIdentifier, typeId);
        }

        String value = node.value.accept(this) ;
        this.addTransition(nodeIdentifier, value);

        return nodeIdentifier;
    }

    @Override
    public String visit(ArrayTypeDeclaration node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "ArrayTypeDeclaration") ;

        String objectId = node.objectId.accept(this) ;
        this.addTransition(nodeIdentifier, objectId);

        String baseTypeId = node.baseTypeId.accept(this) ;
        this.addTransition(nodeIdentifier, baseTypeId);

        return nodeIdentifier;
    }

    @Override
    public String visit(NoRecordTypeDeclaration node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "NoRecordTypeDeclaration") ;

        String objectId = node.objectId.accept(this) ;
        this.addTransition(nodeIdentifier, objectId);

        String baseTypeId = node.baseTypeId.accept(this) ;
        this.addTransition(nodeIdentifier, baseTypeId);

        return nodeIdentifier;
    }

    @Override
    public String visit(RecordTypeDeclaration node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "RecordTypeDeclaration") ;

        String objectId = node.objectId.accept(this) ;
        this.addTransition(nodeIdentifier, objectId);

        for (FieldDeclaration field:node.fields) {
            String fieldId = field.accept(this) ;
            this.addTransition(nodeIdentifier, fieldId) ;
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(For node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "For") ;

        String id = node.id.accept(this) ;
        this.addTransition(nodeIdentifier, id) ;

        String startExpr = node.startExpr.accept(this) ;
        this.addTransition(nodeIdentifier, startExpr) ;

        String endExpr = node.endExpr.accept(this) ;
        this.addTransition(nodeIdentifier, endExpr) ;

        String doExpr = node.doExpr.accept(this) ;
        this.addTransition(nodeIdentifier, doExpr) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(IfThenElse node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "IfThenElse") ;

        String condExpr = node.condExpr.accept(this) ;
        this.addTransition(nodeIdentifier, condExpr) ;

        String thenExpr = node.thenExpr.accept(this) ;
        this.addTransition(nodeIdentifier, thenExpr) ;

        String elseExpr = node.elseExpr.accept(this) ;
        this.addTransition(nodeIdentifier, elseExpr) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Let node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Let") ;

        int n = node.declarationList.size() ;
        for (int i = 0 ; i < n ; i++) {
            String dec = node.declarationList.get(i).accept(this) ;
            this.addTransition(nodeIdentifier, dec) ;
        }

        String exprSeq = node.exprSeq.accept(this) ;
        this.addTransition(nodeIdentifier, exprSeq) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(While node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "While") ;

        String condExpr = node.condExpr.accept(this) ;
        this.addTransition(nodeIdentifier, condExpr) ;

        String doExpr = node.doExpr.accept(this) ;
        this.addTransition(nodeIdentifier, doExpr) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Addition node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Addition") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Affect node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Affect") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(And node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "And") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Division node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Division") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Equals node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Equals") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Inferior node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Inferior") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(InferiorOrEquals node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "InferiorOrEquals") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Multiplication node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Multiplication") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Negation node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Negation") ;

        String negationTail = node.negationTail.accept(this) ;
        this.addTransition(nodeIdentifier, negationTail) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(NotEquals node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "NotEquals") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Or node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Or") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Soustraction node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Soustraction") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(Superior node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "Superior") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(SuperiorOrEquals node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "SuperiorOrEquals") ;

        String left = node.leftValue.accept(this) ;
        this.addTransition(nodeIdentifier, left) ;

        String right = node.rightValue.accept(this) ;
        this.addTransition(nodeIdentifier, right) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(ArrayAccess node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "ArrayAccess") ;

        String exp = node.exp.accept(this) ;
        this.addTransition(nodeIdentifier, exp) ;

        String index = node.index.accept(this) ;
        this.addTransition(nodeIdentifier, index) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(ArrayAssign node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "ArrayAssign") ;

        String type = node.type.accept(this) ;
        this.addTransition(nodeIdentifier, type) ;

        String nombreDElements = node.nombreDElements.accept(this) ;
        this.addTransition(nodeIdentifier, nombreDElements) ;

        String element = node.element.accept(this) ;
        this.addTransition(nodeIdentifier, element) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(FieldCreate node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "FieldCreate") ;

        String id = node.id.accept(this) ;
        this.addTransition(nodeIdentifier, id) ;

        String expr = node.expr.accept(this) ;
        this.addTransition(nodeIdentifier, expr) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(FieldDeclaration node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "FieldDeclaration") ;

        String fieldId = node.fieldId.accept(this) ;
        this.addTransition(nodeIdentifier, fieldId) ;

        String baseType = node.baseType.accept(this) ;
        this.addTransition(nodeIdentifier, baseType) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(FunctionCall node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "FunctionCall") ;

        String id = node.id.accept(this) ;
        this.addTransition(nodeIdentifier, id) ;

        String argList = node.argList.accept(this);
        this.addTransition(nodeIdentifier, argList);


        return nodeIdentifier;
    }

    @Override
    public String visit(ParameterList node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "ParameterList") ;

        int n = node.parameters.size() ;
        for (int i = 0 ; i < n ; i++) {
            String param = node.parameters.get(i).accept(this);
            this.addTransition(nodeIdentifier, param);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(RecordAccess node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "RecordAccess") ;

        String exp = node.exp.accept(this) ;
        this.addTransition(nodeIdentifier, exp) ;

        String index = node.index.accept(this) ;
        this.addTransition(nodeIdentifier, index) ;

        return nodeIdentifier;
    }

    @Override
    public String visit(RecordCreate node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "RecordCreate") ;

        int n = node.args.size() ;
        for (int i = 0 ; i < n ; i++) {
            String arg = node.args.get(i).accept(this) ;
            this.addTransition(nodeIdentifier, arg);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(Id node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, node.identifier) ;
        return nodeIdentifier;
    }

    @Override
    public String visit(IntegerNode node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, Integer.toString(node.value)) ;
        return nodeIdentifier;
    }

    @Override
    public String visit(StringNode node) {
        String nodeIdentifier = this.nextState() ;
        this.addNode(nodeIdentifier, "\\\"" + node.stringContent + "\\\"") ;
        return nodeIdentifier;
    }
}
