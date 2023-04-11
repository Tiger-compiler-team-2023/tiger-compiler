package eu.tn.chaoscompiler.tdstool.tds;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.errors.Errors;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tdstool.variable.FunctionType;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import eu.tn.chaoscompiler.tdstool.variable.Variable;

import lombok.Getter;
import org.json.JSONObject;

/**
 * Interface principale avec l'arbre des TDS.
 * Permet de naviguer entre les sous-TDS de différents scopes ainsi que de
 * trouver et ajouter une variable dans l'arbre.
 */
public class TDScontroller {
    @Getter
    protected TDS tds;
    // Singleton
    private static TDScontroller INSTANCE;
    public int depth;
    public int asmVisitorDepth;

    public static TDScontroller getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TDScontroller();
        }
        return INSTANCE;
    }

    public static void reset() {
        INSTANCE = new TDScontroller();
    }

    private TDScontroller() {
        this.tds = new TDSroot();
        this.depth = 0;
        this.asmVisitorDepth = 0;
        add(Type.INT_TYPE);
        add(Type.STRING_TYPE);
        add(Type.VOID_TYPE);
        add(Type.POINTER_TYPE);

        /*
         * BEGIN STANDARD
         */
        // print (-1)
        FunctionType print_ft = new FunctionType("print", Type.VOID_TYPE, -1);
        print_ft.addIn(Type.STRING_TYPE);
        Value print_fct = new Value(print_ft, "print", -1);
        add(print_fct);
        // printi (-2)
        FunctionType printi_ft = new FunctionType("printi", Type.VOID_TYPE, -2);
        printi_ft.addIn(Type.INT_TYPE);
        Value printi_fct = new Value(printi_ft, "printi", -2);
        add(printi_fct);
        // printi16 (-3)
        FunctionType printi16_ft = new FunctionType("printi16", Type.VOID_TYPE, -3);
        printi16_ft.addIn(Type.INT_TYPE);
        Value printi16_fct = new Value(printi16_ft, "printi16", -3);
        add(printi16_fct);
        // inputi (-4)
        FunctionType inputi_ft = new FunctionType("intput", Type.INT_TYPE, -4);
        Value inputi_fct = new Value(inputi_ft, "intput", -4);
        add(inputi_fct);
        // exit (-5)
        FunctionType exit_ft = new FunctionType("exit", Type.VOID_TYPE, -5);
        exit_ft.addIn(Type.INT_TYPE);
        Value exit_fct = new Value(exit_ft, "exit", -5);
        add(exit_fct);
        /*
         * END LIBARY STANDARD
         */

        this.tds.setStartLine(0);
    }

    public Type getTypeOfId(String id) {
        Type res = findType(id);
        if (res == null) {
            // ERREUR
            System.out.println("Le type " + id + " n'existe pas.");
            return null;
        } else {
            return res;
        }
    }

    public Type getTypeOfId(Ast node, String id) {
        Type res = findType(id);
        if (res == null) {
            // ERREUR
            GestionnaireErreur.getInstance().addSemanticError(node, Errors.UNDECLARED_TYPE, id);
            return null;
        } else {
            return res;
        }
    }

    public Type findType(String id) {
        return this.tds.findType(id);
    }

    public Boolean existsType(String id) {
        return this.tds.existsType(id);
    }

    public Boolean existsLocalType(String id) {
        return (this.tds.getHmType().get(id) != null);
    }

    public Value getVariableOfId(String id) {
        Value res = findVar(id);
        if (res == null) {
            // ERREUR
            System.out.println("La variable " + id + " n'existe pas.");
            return null;
        } else {
            return res;
        }
    }

    public Value getVariableOfId(Ast node, String id) {
        Value res = findVar(id);
        if (res == null) {
            // ERREUR
            GestionnaireErreur.getInstance().addSemanticError(node, Errors.UNDECLARED_VARIABLE, id);
            return null;
        } else {
            return res;
        }
    }

    public Value findVar(String id) {
        return this.tds.findVar(id);
    }

    public Boolean existsVar(String id) {
        return this.tds.existsVar(id);
    }

    public Boolean existsLocalVariable(String id) {
        return (this.tds.getHmVari().get(id) != null);
    }

    public Value getFonctionOfId(String id) {
        Value res = findVar(id);
        if (res == null) {
            // ERREUR
            System.out.println("La fonction " + id + " n'existe pas.");
            return null;
        } else {
            return res;
        }
    }

    public void add(Variable var) {
        if (var instanceof Value)
            ((Value) var).depth = this.depth;
        this.tds.add(var);
    }

    public void addParam(Value v) {
        if (v instanceof Value)
            ((Value) v).depth = this.depth;
        this.tds.addParam(v);
    }

    public void down() {
        TDS t = new TDSlocal(this.tds);
        this.tds.addSub(t);
        this.depth++;
        this.tds = t;
    }

    public void down(Ast node) {
        this.down();
        this.tds.setStartLine(node.getNumLigne());
    }

    public void up() {
        if (this.tds instanceof TDSlocal) {
            this.tds = ((TDSlocal) this.tds).getFather();
            this.depth--;
        } else {
            // ERREUR
            throw new IllegalStateException("Impossible de monter plus haut que la racine de la TDS.");
        }
    }

    public void goUp() {
        if (this.tds instanceof TDSlocal t) {
            this.tds = t.getFather();
            this.asmVisitorDepth--;
        }
    }

    public TDScontroller goDown() {
        this.tds = ((TDSroot) this.tds).nextSubTds();
        this.asmVisitorDepth++;
        return this;
    }

    public TDScontroller down(int idxSubTds) {
        this.tds = this.tds.getFullTDS().get(idxSubTds);
        this.depth++;
        return this;
    }

    public int getNbVar() {
        return this.tds.getNbVar();
    }

    public int getDiffScopeFunc(String id) {
        return this.tds.getDiffScopeFunc(id);
    }

    @Override
    public String toString() {
        return "{[TDScontroller]" + this.tds.toString() + "\n}";
    }

    public String toJSONString() {
        return new JSONObject(this.tds.toJSONString("")).toString(4);
    }
}
