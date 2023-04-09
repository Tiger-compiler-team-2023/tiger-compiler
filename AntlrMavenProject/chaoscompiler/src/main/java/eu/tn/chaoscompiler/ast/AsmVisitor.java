package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.asmtools.Arm64Functions;
import eu.tn.chaoscompiler.asmtools.Registre;
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
import eu.tn.chaoscompiler.errors.ChaosError;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.FunctionType;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;

import java.util.Stack;

public class AsmVisitor implements AstVisitor<String> {

    private TDScontroller tdsController;
    private final GestionnaireErreur err = GestionnaireErreur.getInstance();
    private int stringCounter;
    private int current_id;
    private static int counter_id_if = 1;
    private static int counter_id_loop = 1;
    private Stack<Integer> stack_id = new Stack<Integer>() {
    };

    private class AsmCode {
        public String txt;
        public String section_name;

        public AsmCode(String section_name) {
            this.txt = "// BEGIN " + section_name + "\n";
            this.section_name = section_name;
        }

        @Override
        public String toString() {
            return this.txt;
        }

        public void addTxt(String txt) {
            this.addTxt(txt, true);
        }

        public void addTxt(String txt, boolean first_indent) {
            String tab = "    ";
            if (first_indent)
                this.txt += tab;
            for (char ch : txt.toCharArray()) {
                if (ch == '\n') {
                    this.txt += "\n" + tab;
                } else {
                    this.txt += ch;
                }
            }
            this.txt += "\n";
        }

        public String leaveSection() {
            this.addTxt("// END " + this.section_name, false);
            return this.toString();
        }
    }

    private AsmCode dataSection;
    private AsmCode funcSection;

    @Override
    public Void visit(Program node) {
        stringCounter = 0;

        dataSection = new AsmCode("DATA");
        funcSection = new AsmCode("FUNCTIONS");

        tdsController = TDScontroller.getInstance();

        try {
            ;

            String asm = """
                    .include \"base_macros.s\"
                    // MACROS
                        // Pas de macro à ajouter
                    // fin MACROS
                        .section .text
                        .global _start
                        _start:
                        mov x29, sp
                    // EXECUTION
                    """;

            asm += node.expression.accept(this);

            boolean debug = false;
            if (debug) {
                asm += """
                        // fin EXECUTION
                            pop x0
                            exit x0
                        """;
            } else {
                asm += """
                        // fin EXECUTION
                            exit #0
                        """;
            }

            asm += funcSection.leaveSection();

            asm += """
                        .include "arithmetic_functions.s"
                        .include "data_functions.s"
                        .include "base_functions.s"
                    """;

            asm += dataSection.leaveSection();

            asm += """

                    """;

            System.out.println(asm);

        } catch (Exception e) {
            e.printStackTrace();
            err.addUnrecognisedError(
                    "Erreur durant l'écriture du code assembleur",
                    ChaosError.typeError.SEMANTIC_ERROR);
        }

        return null;
    }

    private static boolean idRdOly = true; // Suis-je dans une situation de readonly d'entier
    private static boolean idFctEnv = false; // Suis-je dans une situation de chainage statique de fonction
    private static Stack<Integer> idCallStack = new Stack<Integer>(); // Pile des définitions de fonction pour trouver
                                                                      // les fonctions récurcives et adapter le chaînage
                                                                      // statique à cette situation

    @Override
    public String visit(Let node) {
        AsmCode res = new AsmCode("Let");
        res.addTxt("// GESTION DU NOUVEAU SCOPE");
        res.addTxt("push " + Registre.ch_stat.n());
        res.addTxt("push " + Registre.ch_dyn.n());
        res.addTxt("mov " + Registre.ch_dyn.n() + ", " + Registre.SP.n() + " // Ch. DYN");
        res.addTxt("push " + Registre.ch_stat.n());
        res.addTxt("add " + Registre.ch_stat.n() + ", " + Registre.SP.n() + ", #16 // Ch. STAT");

        tdsController.goDown();

        res.addTxt("mov x0, #0");
        for (int i = 0; i < tdsController.getNbVar(); i++)
            res.addTxt("push x0 // var " + (i + 1) + "/" + tdsController.getNbVar());

        if (node.decList != null)
            res.addTxt(node.decList.accept(this));
        res.addTxt(node.exprSeq.accept(this));

        // Dépiler
        res.addTxt("// GESTION FIN DU NOUVEAU SCOPE");
        if (node.getType() != Type.VOID_TYPE) {
            res.addTxt("pop x7 // RES");
        }

        res.addTxt("mov " + Registre.SP.n() + ", " + Registre.ch_dyn.n() + " // Ch. DYN");
        res.addTxt("pop " + Registre.ch_dyn.n() + " // Ch. DYN");
        res.addTxt("pop " + Registre.ch_stat.n() + " // Ch. STAT");

        if (node.getType() != Type.VOID_TYPE) {
            res.addTxt("push x7 // RES");
        }

        tdsController.goUp();
        return res.leaveSection();
    }

    @Override
    public String visit(Id node) {
        AsmCode res = new AsmCode("Id " + node.identifier);
        Value val = tdsController.findVar(node.identifier);
        int depth = val.depth;
        int depl = val.getDpl();
        res.addTxt("push " + Registre.ch_stat.n());
        res.addTxt("mov x0, #" + (depth - tdsController.asmVisitorDepth) + " // depth");
        res.addTxt("push x0");
        res.addTxt("mov x0, #" + 16 * depl + " // depl");
        res.addTxt("push x0");
        res.addTxt(Arm64Functions.CHAINAGE_ST.call());
        if (!idFctEnv && (idRdOly || !val.getType().equals(Type.INT_TYPE))) {
            res.addTxt("at // i = *i");
        }
        return res.leaveSection();
    }

    @Override
    public String visit(Sequence node) {
        AsmCode res = new AsmCode("Sequence");
        for (Ast subNodes : node.instructions) {
            res.addTxt(subNodes.accept(this));
        }
        return res.leaveSection();
    }

    @Override
    public String visit(FunctionDeclaration node) {
        FunctionType ft = (FunctionType) node.objectId.getType();
        AsmCode res = new AsmCode("FunctionDeclaration");

        idCallStack.push(ft.getToken());

        AsmCode fRes = new AsmCode("Function " + ft.getId());

        // label
        fRes.addTxt("function_" + ft.getToken() + ":");

        fRes.addTxt("push " + Registre.LR.n() + " // @retour");

        // instructions
        tdsController.goDown(); // ne pas changer l'ordre des lignes
        int depthMem = tdsController.asmVisitorDepth; // ne pas changer l'ordre des lignes
        tdsController.asmVisitorDepth = 0; // ne pas changer l'ordre des lignes
        fRes.addTxt(node.content.accept(this)); // ne pas changer l'ordre des lignes
        tdsController.asmVisitorDepth = depthMem; // ne pas changer l'ordre des lignes
        tdsController.goUp(); // ne pas changer l'ordre des lignes

        if (ft.outType != Type.VOID_TYPE) {
            fRes.addTxt("pop x7 // RES");
        }

        fRes.addTxt("pop " + Registre.LR.n() + " // @retour");

        if (ft.outType != Type.VOID_TYPE) {
            fRes.addTxt("push x7 // RES");
        }

        fRes.addTxt("ret");

        this.funcSection.addTxt(fRes.leaveSection());

        idCallStack.pop();

        return res.leaveSection();
    }

    @Override
    public String visit(VariableDeclaration node) {
        AsmCode res = new AsmCode("VariableDeclaration");

        // Valeur d'initialisation
        res.addTxt(node.value.accept(this));

        // Adresse d'écriture
        idRdOly = false;
        res.addTxt(node.objectId.accept(this));
        idRdOly = true;

        // Ecriture à l'adresse
        res.addTxt("pop x0 // adresse");
        res.addTxt("pop x1 // val");
        res.addTxt("STR x1, [x0]");
        return res.leaveSection();
    }

    @Override
    public String visit(ArrayTypeDeclaration node) {
        AsmCode res = new AsmCode("ArrayTypeDeclaration");
        return res.leaveSection();
    }

    @Override
    public String visit(NoRecordTypeDeclaration node) {
        AsmCode res = new AsmCode("NoRecordTypeDeclaration");
        return res.leaveSection();
    }

    @Override
    public String visit(RecordTypeDeclaration node) {
        AsmCode res = new AsmCode("RecordTypeDeclaration");
        return res.leaveSection();
    }

    @Override
    public String visit(IntegerNode node) {
        AsmCode res = new AsmCode("IntegerNode");
        res.addTxt("MOV x9, #" + node.value);
        res.addTxt("push x9");
        return res.leaveSection();
    }

    @Override
    public String visit(StringNode node) {
        AsmCode res = new AsmCode("StringNode");
        AsmCode dRes = new AsmCode("String lit Ln " + node.getNumLigne() + ", Col " + node.getNumColonne());

        dRes.addTxt(String.format("%s:\n .asciz \"%s\"\n", "str_" + stringCounter, node.stringContent));

        res.addTxt("ldr x0, =str_" + stringCounter);
        res.addTxt("push x0");

        dataSection.addTxt(dRes.leaveSection());

        stringCounter++;
        return res.leaveSection();
    }

    @Override
    public String visit(FunctionCall node) {
        FunctionType ft = (FunctionType) node.id.getType();
        AsmCode res = new AsmCode("FunctionCall");
        if (ft.getToken() < 0) {
            // empiler arguments
            res.addTxt(node.argList.accept(this));

            res.addTxt("// Fonction de la stdlib (id=" + ft.getId() + ")");
            switch (ft.getToken()) {
                case -1:
                    res.addTxt(Arm64Functions.PRINT_STR.call());
                    break;
                case -2:
                    res.addTxt(Arm64Functions.PRINT_INT10.call());
                    break;
                case -3:
                    res.addTxt(Arm64Functions.PRINT_INT16.call());
                    break;
                case -4:
                    res.addTxt(Arm64Functions.INPUT_INT10.call());
                    break;
                case -5:
                    res.addTxt("pop x0");
                    res.addTxt("exit x0");
                    break;
                default:
                    res.addTxt("exit #1");
                    break;
            }
        } else {
            res.addTxt("// GESTION DU NOUVEAU SCOPE");
            res.addTxt("push " + Registre.ch_stat.n());
            res.addTxt("push " + Registre.ch_dyn.n());
            res.addTxt("mov " + Registre.ch_dyn.n() + ", " + Registre.SP.n() + " // Ch. DYN");
            if (!idCallStack.isEmpty() && idCallStack.peek() == ft.getToken()) {
                res.addTxt("push " + Registre.ch_stat.n() + " // Appel récursif");
            } else if (ft.getToken() < 0) {
                res.addTxt("mov x0, #0 // stdlib function -> no Ch. STAT");
                res.addTxt("push x0 // stdlib function -> no Ch. STAT");
            } else {
                idFctEnv = true;
                res.addTxt(node.id.accept(this));
                idFctEnv = false;
            }
            res.addTxt("add " + Registre.ch_stat.n() + ", " + Registre.SP.n() + ", #16 // Ch. STAT");

            // empiler arguments
            tdsController.asmVisitorDepth--;
            res.addTxt(node.argList.accept(this));
            tdsController.asmVisitorDepth++;

            // executer instr
            res.addTxt("bl function_" + ft.getToken());

            // Dépiler
            res.addTxt("// GESTION FIN DU NOUVEAU SCOPE");
            if (node.getType() != Type.VOID_TYPE) {
                res.addTxt("pop x7 // RES");
            }

            res.addTxt("mov " + Registre.SP.n() + ", " + Registre.ch_dyn.n() + " // Ch. DYN");
            res.addTxt("pop " + Registre.ch_dyn.n() + " // Ch. DYN");
            res.addTxt("pop " + Registre.ch_stat.n() + " // Ch. STAT");

            if (node.getType() != Type.VOID_TYPE) {
                res.addTxt("push x7 // RES");
            }
        }

        return res.leaveSection();
    }

    @Override
    public String visit(ParameterList node) {
        AsmCode res = new AsmCode("ParameterList");

        for (Ast a : node.parameters) {
            res.addTxt(a.accept(this));
        }

        return res.leaveSection();
    }

    @Override
    public String visit(ArrayAssign node) {
        AsmCode res = new AsmCode("ArrayAssign");
        res.addTxt(node.nombreDElements.accept(this));
        res.addTxt(node.element.accept(this));
        res.addTxt(Arm64Functions.ARRAY_ASSIGN.call());
        return res.leaveSection();
    }

    @Override
    public String visit(ArrayAccess node) {
        AsmCode res = new AsmCode("ArrayAccess");
        res.addTxt(node.exp.accept(this));
        res.addTxt(node.index.accept(this));
        res.addTxt(Arm64Functions.ARRAY_ACCESS.call());
        return res.leaveSection();
    }

    @Override
    public String visit(RecordCreate node) {
        AsmCode res = new AsmCode("RecordCreate");

        return res.leaveSection();
    }

    @Override
    public String visit(RecordAccess node) {
        AsmCode res = new AsmCode("RecordAccess");

        return res.leaveSection();
    }

    @Override
    public String visit(FieldCreate node) {
        AsmCode res = new AsmCode("FieldCreate");

        return res.leaveSection();
    }

    @Override
    public String visit(FieldDeclaration node) {
        AsmCode res = new AsmCode("FieldDeclaration");

        return res.leaveSection();
    }

    @Override
    public String visit(FieldDecList node) {
        AsmCode res = new AsmCode("FieldDecList");

        return res.leaveSection();
    }

    @Override
    public String visit(For forExpr) {
        AsmCode res = new AsmCode("For");

        return res.leaveSection();
    }

    @Override
    public String visit(While whileExpr) {
        if (stack_id.empty()) {
            stack_id.push(counter_id_loop);
        } else {
            stack_id.push(stack_id.peek() + 1);
            counter_id_loop++;
        }
        // Initialisaiton de l'identifiant
        current_id = stack_id.peek();
        AsmCode res = new AsmCode("While" + Integer.toString(current_id));
        res.addTxt("b _loop_" + Integer.toString(current_id));
        res.addTxt("_loop_" + Integer.toString(current_id) + ":");
        res.addTxt(whileExpr.condExpr.accept(this));
        // Mise à jour de la valeur actuelle de l'identifiant
        current_id = stack_id.peek();
        res.addTxt("pop x1");
        res.addTxt("cmp x1,#0");
        res.addTxt("beq _end_loop_" + Integer.toString(current_id));
        res.addTxt(whileExpr.doExpr.accept(this));
        // Mise à jour de la valeur actuelle de l'identifiant
        current_id = stack_id.peek();
        res.addTxt("b _loop_" + Integer.toString(current_id));
        res.addTxt("_end_loop_" + Integer.toString(current_id) + ":");
        stack_id.pop();
        return res.leaveSection();
    }

    @Override
    public String visit(IfThenElse ifThenElseExpr) {
        if (stack_id.empty()) {
            stack_id.push(counter_id_if);
        } else {
            stack_id.push(stack_id.peek() + 1);
            counter_id_if++;
        }
        // Initialisation de la valeur de l'identifiant actuel
        current_id = stack_id.peek();
        AsmCode res = new AsmCode("IfThenElse" + Integer.toString(current_id));
        String output_condition = ifThenElseExpr.condExpr.accept(this);
        // Mise ajour de l'identifiant actuel
        current_id = stack_id.peek();
        res.addTxt(output_condition);
        // récupération de la valeur de la condition
        res.addTxt("pop x1");
        res.addTxt("cmp x1,#0");
        if (ifThenElseExpr.elseExpr != null) {
            // si le résultat de la condition est vrai (entier non nul, on exécute then
            // sinon on exécute elses)
            res.addTxt("beq _else_" + Integer.toString(current_id));
            res.addTxt("bne _then_" + Integer.toString(current_id));

            // Début du block de else
            res.addTxt("_else_" + Integer.toString(current_id) + ":");
            res.addTxt(ifThenElseExpr.elseExpr.accept(this));
            // Mise à jour de l'identifiant actuel
            current_id = stack_id.peek();
            res.addTxt("b _end_ifthenelse_" + Integer.toString(current_id));
        } else {// pas de else dans ifThen
            res.addTxt("bne _then_" + Integer.toString(current_id) + "_expr");
            res.addTxt("beq _end_ifthenelse_" + Integer.toString(current_id));
        }
        // Début de block de then
        res.addTxt("_then_" + Integer.toString(current_id) + ":");
        res.addTxt(ifThenElseExpr.thenExpr.accept(this));
        // Mise à jour de l'identifiant actuel
        current_id = stack_id.peek();
        res.addTxt("b _end_ifthenelse_" + Integer.toString(current_id));
        // Fin du block conditionnel
        res.addTxt("_end_ifthenelse_" + Integer.toString(current_id) + ":");
        current_id = stack_id.pop();
        return res.leaveSection();
    }

    @Override
    public String visit(Affect node) {
        AsmCode res = new AsmCode("Affect");
        res.addTxt(node.rightValue.accept(this));
        idRdOly = false;
        res.addTxt(node.leftValue.accept(this));
        idRdOly = true;
        res.addTxt("pop x0 // adresse");
        res.addTxt("pop x1 // val");
        res.addTxt("STR x1, [x0]");
        return res.leaveSection();
    }

    @Override
    public String visit(DeclarationList node) {
        AsmCode res = new AsmCode("DeclarationList");
        for (Declaration d : node.list) {
            res.addTxt(d.accept(this));
        }
        return res.leaveSection();
    }

    /*
     * ******** ******** ********
     * OPERATEURS UNAIRES ET BINAIRES
     * ******** ******** ********
     */
    @Override
    public String visit(Negation node) {
        AsmCode res = new AsmCode("Negation");
        res.addTxt(node.negationTail.accept(this));
        // res.addTxt("bl ";
        res.addTxt("\n" + Arm64Functions.INT_NEG.call());
        return res.leaveSection();
    }

    private String auxVisitBinaryOperator(BinaryOperator node, Arm64Functions arm64Function) {
        AsmCode res = new AsmCode("Operateur binaire");
        res.addTxt(node.leftValue.accept(this));
        res.addTxt(node.rightValue.accept(this));
        res.addTxt(arm64Function.call());
        return res.leaveSection();
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
        if (node.leftValue.getType() == Type.STRING_TYPE) {
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
        if (node.leftValue.getType() == Type.STRING_TYPE) {
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
        if (node.leftValue.getType() == Type.STRING_TYPE) {
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
        if (node.leftValue.getType() == Type.STRING_TYPE) {
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
        if (node.leftValue.getType() == Type.STRING_TYPE) {
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
        if (node.leftValue.getType() == Type.STRING_TYPE) {
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
