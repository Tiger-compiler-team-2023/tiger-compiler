package eu.tn.chaoscompiler.graphViz;

import eu.tn.chaoscompiler.ast.AstVisitor;
import eu.tn.chaoscompiler.ast.nodes.Program;
import eu.tn.chaoscompiler.ast.nodes.Sequence;
import eu.tn.chaoscompiler.ast.nodes.declarations.Declaration;
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

    public enum NodeType {
        ID, OPERATION, DECLARATION, CONTROL, TYPE, SEQUENCE, VALUE, ACCESS, LET, DEFAULT;
    }

    private int state;
    private String nodeBuffer;
    private String linkBuffer;


    public GraphVizVisitor() {
        this.state = 0;
        this.nodeBuffer = "digraph \"ast\"{\n\n\tnodesep=1;\n\tranksep=1;\nnode [style=filled]\n";
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
        addNode(node, label, NodeType.DEFAULT);
    }

    private void addNode(String node, String label, NodeType type) {
        String shape = switch (type) {
            case ID -> "ellipse";
            case OPERATION -> "diamond";
            case DECLARATION -> "parallelogram";
            case CONTROL -> "trapezium";
            case TYPE -> "house";
            case SEQUENCE -> "invhouse";
            case VALUE -> "invtrapezium";
            case ACCESS -> "folder";
            case DEFAULT -> "rectangle";
            case LET -> "Mdiamond";
        };
        String color = switch (type){
            case ID -> "pink";
            case OPERATION -> "brown1";
            case DECLARATION -> "aquamarine";
            case CONTROL -> "yellow";
            case TYPE -> "orange";
            case SEQUENCE -> "darkorchid1";
            case VALUE -> "pink";
            case ACCESS -> "grey";
            case DEFAULT -> "gray88";
            case LET -> "darkgoldenrod1";
        };
        this.nodeBuffer += String.format("\t%s [label=\"%s\", shape=\"%s\", fillcolor=\"%s\"];\n",
                node, label, shape, color);
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
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Sequence", NodeType.SEQUENCE);

        int n = node.instructions.size();
        for (int i = 0; i < n; i++) {
            String instr = node.instructions.get(i).accept(this);
            this.addTransition(nodeIdentifier, instr);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(FunctionDeclaration node) {
        //function id ( type-fieldsopt ) : type-id = expr

        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "FunctionDeclaration", NodeType.DECLARATION);
        //nom de la fonction
        String function_id = node.objectId.accept(this);
        this.addTransition(nodeIdentifier, function_id);

        //le type de retour de la fonction(le type est optionnel)
        if (node.returnType != null) {
            String returnType = node.returnType.accept(this);
            this.addTransition(nodeIdentifier, returnType);
        }

        //le contenu de la fonction
        String content = node.content.accept(this);
        this.addTransition(nodeIdentifier, content);

        //Les arguments de la fonction (la fonction peut ne pas contenir des arguments)
        String fields = node.fields.accept(this);
        this.addTransition(nodeIdentifier, fields);

        return nodeIdentifier;
    }


    @Override
    public String visit(VariableDeclaration node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "VariableDeclaration", NodeType.DECLARATION);
        //ajout du nom de la variable
        String varId = node.objectId.accept(this);
        this.addTransition(nodeIdentifier, varId);

        //ajout de type de la variable (le type est optionnel)
        if (node.typeId != null) {
            String typeId = node.typeId.accept(this);
            this.addTransition(nodeIdentifier, typeId);
        }
        //ajout de la valeur de la variable
        String value = node.value.accept(this);
        this.addTransition(nodeIdentifier, value);

        return nodeIdentifier;
    }

    @Override
    public String visit(ArrayTypeDeclaration node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "ArrayTypeDeclaration", NodeType.DECLARATION);

        String objectId = node.objectId.accept(this);
        this.addTransition(nodeIdentifier, objectId);

        String baseTypeId = node.baseTypeId.accept(this);
        this.addTransition(nodeIdentifier, baseTypeId);

        return nodeIdentifier;
    }

    @Override
    public String visit(NoRecordTypeDeclaration node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "NoRecordTypeDeclaration", NodeType.DECLARATION);

        String objectId = node.objectId.accept(this);
        this.addTransition(nodeIdentifier, objectId);

        String baseTypeId = node.baseTypeId.accept(this);
        this.addTransition(nodeIdentifier, baseTypeId);

        return nodeIdentifier;
    }

    @Override
    public String visit(RecordTypeDeclaration node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "RecordTypeDeclaration", NodeType.DECLARATION);

        String objectId = node.objectId.accept(this);
        this.addTransition(nodeIdentifier, objectId);

        String fields = node.fields.accept(this);
        this.addTransition(nodeIdentifier, fields);

        return nodeIdentifier;
    }

    @Override
    public String visit(For node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "For", NodeType.CONTROL);

        String id = node.id.accept(this);
        this.addTransition(nodeIdentifier, id);

        String startExpr = node.startExpr.accept(this);
        this.addTransition(nodeIdentifier, startExpr);

        String endExpr = node.endExpr.accept(this);
        this.addTransition(nodeIdentifier, endExpr);

        String doExpr = node.doExpr.accept(this);
        this.addTransition(nodeIdentifier, doExpr);

        return nodeIdentifier;
    }

    @Override
    public String visit(IfThenElse node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "IfThenElse", NodeType.CONTROL);

        String condExpr = node.condExpr.accept(this);
        this.addTransition(nodeIdentifier, condExpr);

        String thenExpr = node.thenExpr.accept(this);
        this.addTransition(nodeIdentifier, thenExpr);

        if (node.elseExpr != null) {
            String elseExpr = node.elseExpr.accept(this);
            this.addTransition(nodeIdentifier, elseExpr);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(Let node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Let", NodeType.LET);

        String decList = node.decList.accept(this);
        this.addTransition(nodeIdentifier, decList);

        String exprSeq = node.exprSeq.accept(this);
        this.addTransition(nodeIdentifier, exprSeq);

        return nodeIdentifier;
    }

    @Override
    public String visit(While node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "While", NodeType.CONTROL);

        String condExpr = node.condExpr.accept(this);
        this.addTransition(nodeIdentifier, condExpr);

        String doExpr = node.doExpr.accept(this);
        this.addTransition(nodeIdentifier, doExpr);

        return nodeIdentifier;
    }

    @Override
    public String visit(Addition node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Addition", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Affect node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Affect");

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(And node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "And", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Division node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Division", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Equals node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Equals", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Inferior node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Inferior", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(InferiorOrEquals node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "InferiorOrEquals", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Multiplication node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Multiplication", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Negation node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Negation", NodeType.OPERATION);

        String negationTail = node.negationTail.accept(this);
        this.addTransition(nodeIdentifier, negationTail);

        return nodeIdentifier;
    }

    @Override
    public String visit(NotEquals node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "NotEquals", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Or node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Or", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Soustraction node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Soustraction", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(Superior node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "Superior", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(SuperiorOrEquals node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "SuperiorOrEquals", NodeType.OPERATION);

        String left = node.leftValue.accept(this);
        this.addTransition(nodeIdentifier, left);

        String right = node.rightValue.accept(this);
        this.addTransition(nodeIdentifier, right);

        return nodeIdentifier;
    }

    @Override
    public String visit(ArrayAccess node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "ArrayAccess", NodeType.ACCESS);

        String exp = node.exp.accept(this);
        this.addTransition(nodeIdentifier, exp);

        String index = node.index.accept(this);
        this.addTransition(nodeIdentifier, index);

        return nodeIdentifier;
    }

    @Override
    public String visit(ArrayAssign node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "ArrayAssign", NodeType.ACCESS);

        String type = node.type.accept(this);
        this.addTransition(nodeIdentifier, type);

        String nombreDElements = node.nombreDElements.accept(this);
        this.addTransition(nodeIdentifier, nombreDElements);

        String element = node.element.accept(this);
        this.addTransition(nodeIdentifier, element);

        return nodeIdentifier;
    }

    @Override
    public String visit(FieldCreate node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "FieldCreate", NodeType.DECLARATION);

        String id = node.id.accept(this);
        this.addTransition(nodeIdentifier, id);

        String expr = node.expr.accept(this);
        this.addTransition(nodeIdentifier, expr);

        return nodeIdentifier;
    }

    @Override
    public String visit(FieldDeclaration node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "FieldDeclaration", NodeType.DECLARATION);

        String fieldId = node.fieldId.accept(this);
        this.addTransition(nodeIdentifier, fieldId);

        String baseType = node.baseType.accept(this);
        this.addTransition(nodeIdentifier, baseType);

        return nodeIdentifier;
    }

    @Override
    public String visit(FieldDecList node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "FieldDecList", NodeType.DECLARATION);

        for (FieldDeclaration field : node.list) {
            String fieldId = field.accept(this);
            this.addTransition(nodeIdentifier, fieldId);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(FunctionCall node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "FunctionCall", NodeType.ACCESS);

        String id = node.id.accept(this);
        this.addTransition(nodeIdentifier, id);

        String argList = node.argList.accept(this);
        this.addTransition(nodeIdentifier, argList);

        return nodeIdentifier;
    }

    @Override
    public String visit(ParameterList node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "ParameterList", NodeType.ACCESS);

        int n = node.parameters.size();
        for (int i = 0; i < n; i++) {
            String param = node.parameters.get(i).accept(this);
            this.addTransition(nodeIdentifier, param);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(DeclarationList node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "DeclarationList", NodeType.DECLARATION);

        for (Declaration dec : node.list) {
            String decId = dec.accept(this);
            this.addTransition(nodeIdentifier, decId);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(RecordAccess node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "RecordAccess", NodeType.ACCESS);

        String exp = node.exp.accept(this);
        this.addTransition(nodeIdentifier, exp);

        String index = node.index.accept(this);
        this.addTransition(nodeIdentifier, index);

        return nodeIdentifier;
    }

    @Override
    public String visit(RecordCreate node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "RecordCreate", NodeType.DECLARATION);

        String id = node.idObject.accept(this);
        this.addTransition(nodeIdentifier, id);

        int n = node.args.size();
        for (int i = 0; i < n; i++) {
            String arg = node.args.get(i).accept(this);
            this.addTransition(nodeIdentifier, arg);
        }

        return nodeIdentifier;
    }

    @Override
    public String visit(Id node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, node.identifier, NodeType.ID);
        return nodeIdentifier;
    }

    @Override
    public String visit(IntegerNode node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, Integer.toString(node.value), NodeType.VALUE);
        return nodeIdentifier;
    }

    @Override
    public String visit(StringNode node) {
        String nodeIdentifier = this.nextState();
        this.addNode(nodeIdentifier, "\\\"" + node.stringContent + "\\\"", NodeType.VALUE);
        return nodeIdentifier;
    }
}
