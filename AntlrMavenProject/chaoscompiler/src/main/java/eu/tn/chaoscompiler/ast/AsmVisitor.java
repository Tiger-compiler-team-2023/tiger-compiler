package eu.tn.chaoscompiler.ast;

import eu.tn.chaoscompiler.asmtools.Arm64Functions;
import eu.tn.chaoscompiler.asmtools.Registre;
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
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tdstool.tds.TDScontroller;
import eu.tn.chaoscompiler.tdstool.variable.FunctionType;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;

import java.util.Stack;

public class AsmVisitor implements AstVisitor<String> {

    private TDScontroller tdsController;
    private int depth;
    private final GestionnaireErreur err = GestionnaireErreur.getInstance();
    private String dataSection;

    private String funcSection ;
    private int stringCounter;
    private int current_id;
    private static int counter_id_if=1;
    private static int counter_id_loop=1;
    private Stack<Integer> stack_id = new Stack<Integer>(){};

    @Override
    public Void visit(Program node) {
        stringCounter = 0;
        dataSection = "";
        funcSection = "" ;

        tdsController = TDScontroller.getInstance();
        this.depth = 0;

        try {
            // Début section data
            dataSection ="""
                // fin FUNCTIONS
                .include "arithmetic_functions.s"
                .include "data_functions.s"
                .include "base_functions.s"
                // DATA
                """;

            funcSection = "";

            //importer les fonctions arm et les macros ;
            String asm = """
                .include \"base_macros.s\"
                // MACROS
                
                
                // fin MACROS
                .section .text
                .global _start
                _start:
                mov     x29,    sp
                // EXECUTION
                    """;

            //Le programme
            asm+=node.expression.accept(this);
            asm+="""
                // fin EXECUTION
                    exit #0
                // FUNCTIONS
                """;

            // section fonctions
            asm += funcSection ;
            // Fin section data
            asm += dataSection;
            asm += """
                // fin DATA

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

    private static boolean idRdOly = true;

    public String appeleAvant() {

        String res  = "" ;

        tdsController.goDown();

        // Empiler @ retour qui est dans LR x30
        res += "push x" + Registre.LR.o() + "\n" ;

        // Empiler ch DYN qui est dans FP x29
        res += "push x" + Registre.FP.o() + "\n" ;

        // Mise à jour base
        res += "mov x" + Registre.FP.o() + ", " + Registre.SP.n() + "\n" ;
        // Empiler ch STAT x28
        res += "push x" + Registre.ch_stat.o() + "\n" ;

        // Réserver place variables locales
        int nbVarLocales = tdsController.getNbVar() ;
        res += "mov x0, #0\n" ;
        for (int i = 0 ; i < nbVarLocales ; i++) {
            res += "push x0\n" ;
        }

        return res ;
    }

    public String appeleApres() {
        String res = "" ;

        // Dépiler variables locales
        int nbVarLocales = tdsController.getNbVar() ;
        for (int i = 0 ; i < nbVarLocales ; i++) {
            res += "pop x0\n" ;
        }

        // Restaurer ancienne base
        res += "ldr x" + Registre.FP.o() + ", [" + Registre.SP.n() + "]\n" ;

        tdsController.goUp();

        return res ;
    }

    @Override
    public String visit(Let letExpr) {
        String res = "// Let\n";

        // Calculer ch. STAT et le mettre ch. STAT dans x28
        res += "add x" + Registre.ch_stat.o() + ", x" + Registre.FP.o() + ", #16\n" ;

        res += appeleAvant() ;
        this.depth ++;

        if (letExpr.decList != null)
            res += letExpr.decList.accept(this);
        res += letExpr.exprSeq.accept(this);

        if (letExpr.getType() != Type.VOID_TYPE) {
            res += "pop x7 // RES\n";
        }
        res += appeleApres() ;

        if (letExpr.getType() != Type.VOID_TYPE) {
            res += "push x7 // RES\n";
        }
        
        this.depth --;
        res += "// END Let\n";
        return res;
    }

    @Override
    public String visit(Id node) {
        String res = "// Id\n";
        res += "// id = ";
        res += node.identifier;
        res += "\n";
        Value val = tdsController.findVar(node.identifier);
        int depth = this.depth - val.depth;
        int depl = val.getDpl();
        res += "push x" + Registre.ch_stat.o() + "\n";
        res += "mov x0, #" + depth + "// depth\n";
        res += "push x0\n";
        res += "mov x0, #" + 16*(depl+2) + "// depl\n";
        res += "push x0\n";
        res += Arm64Functions.CHAINAGE_ST.call();
        if (idRdOly || !val.getType().equals(Type.INT_TYPE)) {
            res += "at // i = *i";
        }
        res += "// END Id\n";
        return res;
    }

    @Override
    public String visit(Sequence node) {
        String res = "// Sequence\n";
        for (Ast subNodes : node.instructions) {
            res+=subNodes.accept(this);
        }
        res += "// END Sequence\n";
        return res;
    }
    @Override
    public String visit(FunctionDeclaration node) {
        String res = "// FunctionDeclaration\n";

        FunctionType ft = (FunctionType) tdsController.findType(((Id)node.objectId).identifier) ;

        String fRes = "// Function " + ft.getId() + "\n" ;

        // label
        fRes += "function_" + ft.getToken() + ":\n" ;

        fRes += appeleAvant() ;

        // instructions
        node.content.accept(this) ;

        fRes += appeleApres() ;

        fRes += "// END Function " + ft.getId() + "\n\n" ;

        this.funcSection += fRes ;

        return res;
    }

    @Override
    public String visit(VariableDeclaration node) {
        String res = "// VariableDeclaration\n";

        // Obtention du type
        Type type = node.value.getType();

        // Valeur d'initialisation
        res += node.value.accept(this);

        // Adresse d'écriture
        idRdOly = false;
        res += node.objectId.accept(this);
        idRdOly = true;
        
        // Ecriture à l'adresse
        res += "pop x0 // adresse\n";
        res += "pop x1 // val\n";
        res += "STR x1, [x0]\n";

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
        res += "MOV     x9,    #" + node.value + "\n";
        res += "push    x9\n";
        res += "// END IntegerNode\n";
        return res;
    }

    // -------------- STRING ----
    @Override
    public String visit(StringNode node) {
        String res = "// StringNode\n";

        String strlbl = addStringLitteral(node);
        res += "ldr x0,    =" + strlbl + "\n";
        res += "push    x0\n";

        res += "// END StringNode\n";
        return res;
    }

    public String addStringLitteral(StringNode node) {
        String name = "str_" + stringCounter;
        dataSection += String.format("%s:\n .asciz  \"%s\"\n", name, node.stringContent);
        stringCounter++;
        return name;
    }
// ----------------- /STRING ----

    @Override
    public String visit(FunctionCall node) {
        String res = "// FunctionCall\n";

        // Recuperer chainage statique
        // Param 1
        res += "push x" + Registre.FP.o() + "\n" ;
        // Param 2 : difference entre scope actuel et scope de la fonction
        int diff = tdsController.getDiffScopeFunc(((Id) node.id).identifier) ;
        res += "mov x0, #" + diff + "\n" ;
        res += "push x0" ;
        // Param 3 : deplacement (ici 0)
        res += "mov x0, #0" ;
        res += "push x0" ;
        // Calcul
        res += Arm64Functions.CHAINAGE_ST.call();
        // Mettre ch STAT dans x28
        res += "pop x" + Registre.ch_stat.o() + "\n" ;

        // empiler arguments
        node.argList.accept(this) ;

        // executer instr
        FunctionType ft = (FunctionType) tdsController.findType(((Id)node.id).identifier) ;
        res += "bl function_" + ft.getToken() + "\n" ;

        // depiler arguments
        for (int i = 0 ; i <  ((ParameterList) node.argList).parameters.size() ; i++) {
            res += "pop x0\n" ;
        }

        res += "// END FunctionCall\n";
        return res;
    }

    @Override
    public String visit(ParameterList node) {
        String res = "// ParameterList\n";

        for (Ast a:node.parameters) {
            a.accept(this) ;
        }

        res += "// END ParameterList\n";
        return res;
    }

    @Override
    public String visit(ArrayAssign node) {
        String res = "// ArrayAssign\n";
        node.nombreDElements.accept(this);
        node.element.accept(this);
        res += Arm64Functions.ARRAY_ASSIGN.call();
        res += "// END ArrayAssign\n";
        return res;
    }

    @Override
    public String visit(ArrayAccess node) {
        String res = "// ArrayAccess\n";
        node.exp.accept(this);
        node.index.accept(this);
        res += Arm64Functions.ARRAY_ACCESS.call();
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
        if(stack_id.empty()){
            stack_id.push(counter_id_loop);
        }
        else{
            stack_id.push(stack_id.peek()+1);
            counter_id_loop++;
        }
        //Initialisaiton de l'identifiant
        current_id=stack_id.peek();
        String res = "// While"+Integer.toString(current_id)+"\n";
        res+="b _loop"+Integer.toString(current_id)+"\n";
        res+="_loop_"+Integer.toString(current_id)+":"+"\n";
        res+=whileExpr.condExpr.accept(this)+"\n";
        //Mise à jour de la valeur actuelle de l'identifiant
        current_id=stack_id.peek();
        res+="pop x1"+"\n";
        res+="cmp x1,#0"+"\n";
        res+="beq _end_loop_"+Integer.toString(current_id)+"\n";
        res+=whileExpr.doExpr.accept(this)+"\n";
        //Mise à jour de la valeur actuelle de l'identifiant
        current_id=stack_id.peek();
        res+="b _loop_"+Integer.toString(current_id)+"\n";
        res+="_end_loop_"+Integer.toString(current_id)+":"+"\n";
        res += "// END While"+Integer.toString(current_id)+"\n";
        stack_id.pop();
        return res;
    }

    @Override
    public String visit(IfThenElse ifThenElseExpr) {
        if(stack_id.empty()){
            stack_id.push(counter_id_if);
        }
        else{
            stack_id.push(stack_id.peek()+1);
            counter_id_if++;
        }
        //Initialisation de la valeur de l'identifiant actuel
        current_id = stack_id.peek();
        String res = "// IfThenElse"+Integer.toString(current_id)+"\n";
        String output_condition = ifThenElseExpr.condExpr.accept(this);
        //Mise ajour de l'identifiant actuel
        current_id = stack_id.peek();
        res+=output_condition+"\n";
        //récupération de la valeur de la condition
        res+="pop x1"+"\n";
        res+="cmp x1,#0"+"\n";
        if(ifThenElseExpr.elseExpr!=null){
            //si le résultat de la condition est vrai (entier non nul, on exécute then sinon on exécute elses)
            res+="beq _else_"+Integer.toString(current_id)+"\n";
            res+="bne _then_"+Integer.toString(current_id)+"\n";

            //Début du block de else
            res+="_else_"+Integer.toString(current_id)+"\n";
            res+=ifThenElseExpr.elseExpr.accept(this);
            //Mise à jour de l'identifiant actuel
            current_id = stack_id.peek();
            res+="b _end_ifthenelse_"+Integer.toString(current_id)+"\n";
        }
        else{//pas de else dans ifThen
            res+="bne _then_"+Integer.toString(current_id)+"_expr"+"\n";
            res+="beq _end_ifthenelse_"+Integer.toString(current_id)+"\n";
        }
        //Début de block de then
        res+="_then_"+Integer.toString(current_id)+"\n";
        res+=ifThenElseExpr.thenExpr.accept(this);
        // Mise à jour de l'identifiant actuel
        current_id = stack_id.peek();
        res+="b _end_ifthenelse_"+Integer.toString(current_id)+"\n";
        //Fin du block conditionnel
        res+="_end_ifthenelse_"+Integer.toString(current_id)+":\n";
        res += "// END IfThenElse"+Integer.toString(current_id)+"\n";
        current_id = stack_id.pop();
        return res;
    }

    @Override
    public String visit(Affect node) {
        String res = "// Affect\n";
        node.rightValue.accept(this);
        idRdOly = false;
        node.leftValue.accept(this);
        idRdOly = true;
        res += "pop x0 // adresse\n";
        res += "pop x1 // val\n";
        res += "STR x1, [x0]\n";
        res += "// END Affect\n";
        return res;
    }

    @Override
    public String visit(DeclarationList node) {
        String res = "// DeclarationList\n";
        res += node.list.stream()
                .map(declaration -> declaration.accept(this) + "\n")
                .reduce(res, String::concat);
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
        //res += "bl ";
        res += "\n" + Arm64Functions.INT_NEG.call();
        return res;
    }

    private String auxVisitBinaryOperator(BinaryOperator node, Arm64Functions arm64Function) {
        String res = "";
        res += node.leftValue.accept(this);
        res += node.rightValue.accept(this);
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
