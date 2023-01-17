package eu.tn.chaoscompiler.tdstool.tds;

public class TDSlocal extends TDSroot {
    protected TDS father;

    public TDSlocal(TDS father) {
        super();
        this.father = father;
    }

    public TDS getFather() {
        return this.father;
    }

    @Override
    public String toString() {
        return "\n{[TDSlocal]" +"Types:" + this.hmType.toString() + "Variables:" + this.hmVari.toString() + "father:" + this.father.toString() + "\n}";
    }

    @Override
    public String toJSONString(String child) {
        StringBuilder s = new StringBuilder("") ;

        s.append("{ ") ;
        s.append("\"types\" : [ ") ;

        int count = 0 ;

        for (String key:hmType.keySet()) {
            s.append(childJSONString(hmType.get(key))) ;

            if (count < hmType.size() - 1) {
                s.append(",\n") ;
            }

            count++ ;
        }

        s.append(" ],\n") ;
        s.append("\"variables\" : [ ") ;

        count = 0 ;

        for (String key:hmVari.keySet()) {
            s.append(hmVari.get(key).toJSONString()) ;

            if (count < hmVari.size() - 1) {
                s.append(",\n") ;
            }

            count++ ;
        }

        s.append(" ]") ;

        if ((child != null) && (!child.equals(""))) {
            s.append(",\n") ;
            s.append("\"tdsChild\" : ") ;
            s.append(child) ;
        }

        s.append(" }") ;

        return father.toJSONString(s.toString()) ;
    }
}
