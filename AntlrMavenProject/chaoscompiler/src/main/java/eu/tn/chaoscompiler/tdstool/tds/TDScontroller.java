package eu.tn.chaoscompiler.tdstool.tds;

import eu.tn.chaoscompiler.tdstool.variable.Variable;

/**
 * Interface principale avec l'arbre des TDS.
 * Permet de naviguer entre les sous-TDS de différents scopes ainsi que de trouver et ajouter une variable dans l'arbre.
 */
public class TDScontroller {
    protected TDS tds;
    private static TDScontroller tdsController;

    public TDScontroller() {
        this.tds = new TDSroot();
    }

    public static TDScontroller getInstance() {
        if (tdsController == null) {
            tdsController = new TDScontroller();
        }
        return tdsController;
    }

    public Variable find(String id) {
        if (this.tds instanceof TDSlocal) {
            if (this.tds.exists(id)) {
                return this.tds.find(id);
            } else {
                return ((TDSlocal) this.tds).getFather().find(id);
            }
        } else {
            return this.tds.find(id);
        }
    }

    public Variable getVariableOfId(String id) {
        Variable res = find(id);
        if (res == null) {
            // ERREUR
            throw new IllegalStateException("La variable " + id + " n'existe pas.");
        } else {
            return res;
        }
    }

    public Boolean exists(String id) {
        if (this.tds instanceof TDSlocal) {
            return this.tds.exists(id) | ((TDSlocal) this.tds).getFather().exists(id);
        } else {
            return this.tds.exists(id);
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
}
