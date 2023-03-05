package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.asmtools.Arm64Functions;
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
import eu.tn.chaoscompiler.errors.ChaosError;
import eu.tn.chaoscompiler.errors.Errors;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.Type;

public class AsmVisitor implements AstVisitor<String> {

    private TDScontroller tdsController;
    private final GestionnaireErreur err = GestionnaireErreur.getInstance();

    @Override
    public Void visit(Program node) {
        tdsController = TDScontroller.getInstance();

        String asm = node.accept(this) + "\nEND";
        try {
            node.expression.accept(this);
        } catch (Exception e) {
            e.printStackTrace();
            err.addUnrecognisedError(
                    "Erreur durant l'écriture du code assembleur",
                    ChaosError.typeError.SEMANTIC_ERROR);
        }
        System.out.println(asm);
        return null;
    }

    @Override
    public String visit(Let letExpr) {
        String res = "// Let\n";
        res += letExpr.decList.accept(this);
        res += letExpr.exprSeq.accept(this);
        res += "// END Let\n";
        return res;
    }

    @Override
    public String visit(Id node) {
        String res = "// Id\n";
        res += "idetifier = ";
        res += node.identifier;
        res += "\n";
        res += "// END Id\n";
        return res;
    }

    @Override
    public String visit(Sequence node) {
        String res = "// Sequence\n";
        for (Ast subNodes : node.instructions) {
            subNodes.accept(this);
        }
        res += "// END Sequence\n";
        return res;
    }

    @Override
    public String visit(FunctionDeclaration node) {
        String res = "// FunctionDeclaration\n";

        res += "// END FunctionDeclaration\n";
        return res;
    }

    @Override
    public String visit(VariableDeclaration node) {
        String res = "// VariableDeclaration\n";

        res += "// END VariableDeclaration\n";
        return res;
    }

    @Override
    public String visit(ArrayTypeDeclaration node) {
        String res = "// ArrayTypeDeclaration\n";

        res += "// END ArrayTypeDeclaration\n";
        return res;
    }

    @Override
    public String visit(NoRecordTypeDeclaration node) {
        String res = "// NoRecordTypeDeclaration\n";

        res += "// END NoRecordTypeDeclaration\n";
        return res;
    }

    @Override
    public String visit(RecordTypeDeclaration node) {
        String res = "// RecordTypeDeclaration\n";

        res += "// END RecordTypeDeclaration\n";
        return res;
    }

    @Override
    public String visit(IntegerNode node) {
        String res = "// IntegerNode\n";

        res += "// END IntegerNode\n";
        return res;
    }

    @Override
    public String visit(StringNode node) {
        String res = "// StringNode\n";
        res += "string = \"";
        res += node.stringContent;
        res += "\"\n";
        res += "// END StringNode\n";
        return res;
    }

    @Override
    public String visit(FunctionCall node) {
        String res = "// FunctionCall\n";

        res += "// END FunctionCall\n";
        return res;
    }

    @Override
    public String visit(ParameterList node) {
        String res = "// ParameterList\n";

        res += "// END ParameterList\n";
        return res;
    }

    @Override
    public String visit(ArrayAssign node) {
        String res = "// ArrayAssign\n";

        res += "// END ArrayAssign\n";
        return res;
    }

    @Override
    public String visit(ArrayAccess node) {
        String res = "// ArrayAccess\n";

        res += "// END ArrayAccess\n";
        return res;
    }

    @Override
    public String visit(RecordCreate node) {
        String res = "// RecordCreate\n";

        res += "// END RecordCreate\n";
        return res;
    }

    @Override
    public String visit(RecordAccess node) {
        String res = "// RecordAccess\n";

        res += "// END RecordAccess\n";
        return res;
    }

    @Override
    public String visit(FieldCreate node) {
        String res = "// FieldCreate\n";

        res += "// END FieldCreate\n";
        return res;
    }

    @Override
    public String visit(FieldDeclaration node) {
        String res = "// FieldDeclaration\n";

        res += "// END FieldDeclaration\n";
        return res;
    }

    @Override
    public String visit(FieldDecList node) {
        String res = "// FieldDecList\n";

        res += "// END FieldDecList\n";
        return res;
    }

    @Override
    public String visit(For forExpr) {
        String res = "// For\n";

        res += "// END For\n";
        return res;
    }

    @Override
    public String visit(While whileExpr) {
        String res = "// While\n";

        res += "// END While\n";
        return res;
    }

    @Override
    public String visit(IfThenElse ifThenElseExpr) {
        String res = "// IfThenElse\n";

        res += "// END IfThenElse\n";
        return res;
    }

    @Override
    public String visit(Affect node) {
        String res = "// Affect\n";

        res += "// END Affect\n";
        return res;
    }

    @Override
    public String visit(DeclarationList node) {
        String res = "// DeclarationList\n";

        res += "// END DeclarationList\n";
        return res;
    }

    /*
     * ******** ******** ********
     * OPERATEURS UNAIRES ET BINAIRES
     * ******** ******** ********
     */
    @Override
    public String visit(Negation node) {
        String res = "";
        res += node.negationTail.accept(this);
        res += "bl ";
        res += Arm64Functions.INT_NEG.call();
        return res;
    }

    private String auxVisitBinaryOperator(BinaryOperator node, Arm64Functions arm64Function) {
        String res = "";
        res += node.leftValue.accept(this);
        res += node.leftValue.accept(this);
        res += "bl ";
        res += arm64Function.call();
        return res;
    }

    @Override
    public String visit(Addition node) {
        return auxVisitBinaryOperator(node, Arm64Functions.INT_ADD);
    }

    @Override
    public String visit(Soustraction node) {
        return auxVisitBinaryOperator(node, Arm64Functions.INT_SUB);
    }

    @Override
    public String visit(Multiplication node) {
        return auxVisitBinaryOperator(node, Arm64Functions.INT_MUL);
    }

    @Override
    public String visit(Division node) {
        return auxVisitBinaryOperator(node, Arm64Functions.INT_DIV);
    }

    @Override
    public String visit(Equals node) {
        Arm64Functions af;
        if (node.getType() == Type.STRING_TYPE) {
            af = Arm64Functions.STR_EQ;
        } else {
            // Si ce n'est pas une chaîne de caractères, alors on traite ça comme un entier
            // ou une adresse.
            af = Arm64Functions.INT_EQ;
        }
        return auxVisitBinaryOperator(node, af);
    }

    @Override
    public String visit(NotEquals node) {
        Arm64Functions af;
        if (node.getType() == Type.STRING_TYPE) {
            af = Arm64Functions.STR_NE;
        } else {
            // Si ce n'est pas une chaîne de caractères, alors on traite ça comme un entier
            // ou une adresse.
            af = Arm64Functions.INT_NE;
        }
        return auxVisitBinaryOperator(node, af);
    }

    @Override
    public String visit(Superior node) {
        Arm64Functions af;
        if (node.getType() == Type.STRING_TYPE) {
            af = Arm64Functions.STR_GT;
        } else {
            // Si ce n'est pas une chaîne de caractères, alors on traite ça comme un entier
            // ou une adresse.
            af = Arm64Functions.INT_GT;
        }
        return auxVisitBinaryOperator(node, af);
    }

    @Override
    public String visit(Inferior node) {
        Arm64Functions af;
        if (node.getType() == Type.STRING_TYPE) {
            af = Arm64Functions.STR_LT;
        } else {
            // Si ce n'est pas une chaîne de caractères, alors on traite ça comme un entier
            // ou une adresse.
            af = Arm64Functions.INT_LT;
        }
        return auxVisitBinaryOperator(node, af);
    }

    @Override
    public String visit(SuperiorOrEquals node) {
        Arm64Functions af;
        if (node.getType() == Type.STRING_TYPE) {
            af = Arm64Functions.STR_GE;
        } else {
            // Si ce n'est pas une chaîne de caractères, alors on traite ça comme un entier
            // ou une adresse.
            af = Arm64Functions.INT_GE;
        }
        return auxVisitBinaryOperator(node, af);
    }

    @Override
    public String visit(InferiorOrEquals node) {
        Arm64Functions af;
        if (node.getType() == Type.STRING_TYPE) {
            af = Arm64Functions.STR_LE;
        } else {
            // Si ce n'est pas une chaîne de caractères, alors on traite ça comme un entier
            // ou une adresse.
            af = Arm64Functions.INT_LE;
        }
        return auxVisitBinaryOperator(node, af);
    }

    @Override
    public String visit(Or node) {
        return auxVisitBinaryOperator(node, Arm64Functions.LOG_OR);
    }

    @Override
    public String visit(And node) {
        return auxVisitBinaryOperator(node, Arm64Functions.LOG_AND);
    }
}
