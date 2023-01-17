package eu.tn.chaoscompiler.tdstool.tds;

import eu.tn.chaoscompiler.ast.Ast;
import eu.tn.chaoscompiler.errors.Errors;
import eu.tn.chaoscompiler.errors.GestionnaireErreur;
import eu.tn.chaoscompiler.tdstool.variable.FunctionType;
import eu.tn.chaoscompiler.tdstool.variable.Type;
import eu.tn.chaoscompiler.tdstool.variable.Value;
import eu.tn.chaoscompiler.tdstool.variable.Variable;

/**
 * Interface principale avec l'arbre des TDS.
 * Permet de naviguer entre les sous-TDS de différents scopes ainsi que de
 * trouver et ajouter une variable dans l'arbre.
 */
public class TDScontroller {
    protected TDS tds;
    // Singleton
    private static TDScontroller INSTANCE;

    public static TDScontroller getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TDScontroller();
        }
        return INSTANCE;
    }

    public static void reset (){
        INSTANCE = new TDScontroller();
    }

    private TDScontroller() {
        this.tds = new TDSroot();
        add(Type.INT_TYPE);
        add(Type.STRING_TYPE);
        add(Type.VOID_TYPE);

        FunctionType ft = new FunctionType("", Type.VOID_TYPE);
        ft.addIn(Type.STRING_TYPE);
        Value print = new Value(ft, "print");
        add(print);
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
        this.tds.add(var);
    }

    public void down() {
        this.tds = new TDSlocal(this.tds);
    }

    public void up() {
        if (this.tds instanceof TDSlocal) {
            this.tds = ((TDSlocal) this.tds).getFather();
        } else {
            // ERREUR
            throw new IllegalStateException("Impossible de monter plus haut que la racine de la TDS.");
        }
    }

    @Override
    public String toString() {
        return "{[TDScontroller]" + this.tds.toString() + "\n}";
    }

    public String toJSONString() {
        return this.tds.toJSONString("") ;
    }
}
