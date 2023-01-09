package eu.tn.chaoscompiler.tdstool.tds;

import eu.tn.chaoscompiler.tdstool.variable.Variable;

/**
 * Interface principale avec l'arbre des TDS.
 * Permet de naviguer entre les sous-TDS de différents scopes ainsi que de
 * trouver et ajouter une variable dans l'arbre.
 */
public class TDScontroller {
    protected TDS tds;

    public TDScontroller() {
        this.tds = new TDSroot();
    }

    public Variable getTypeOfId(String id) {
        Variable res = findType(id);
        if (res == null) {
            // ERREUR
            throw new IllegalStateException("Le type " + id + " n'existe pas.");
        } else {
            return res;
        }
    }

    public Variable findType(String id) {
        if (this.tds instanceof TDSlocal) {
            if (this.tds.existsType(id)) {
                return this.tds.findType(id);
            } else {
                return ((TDSlocal) this.tds).getFather().findType(id);
            }
        } else {
            return this.tds.findType(id);
        }
    }

    public Boolean existsType(String id) {
        if (this.tds instanceof TDSlocal) {
            return this.tds.existsType(id) | ((TDSlocal) this.tds).getFather().existsType(id);
        } else {
            return this.tds.existsType(id);
        }
    }

    public Variable getVariableOfId(String id) {
        Variable res = findVari(id);
        if (res == null) {
            // ERREUR
            throw new IllegalStateException("La variable " + id + " n'existe pas.");
        } else {
            return res;
        }
    }

    public Variable findVari(String id) {
        if (this.tds instanceof TDSlocal) {
            if (this.tds.existsVari(id)) {
                return this.tds.findVari(id);
            } else {
                return ((TDSlocal) this.tds).getFather().findVari(id);
            }
        } else {
            return this.tds.findVari(id);
        }
    }

    public Boolean existsVari(String id) {
        if (this.tds instanceof TDSlocal) {
            return this.tds.existsVari(id) | ((TDSlocal) this.tds).getFather().existsVari(id);
        } else {
            return this.tds.existsVari(id);
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
