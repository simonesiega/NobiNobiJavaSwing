package nobinobi.obj.editable;

import nobinobi.obj.Condition;

/**
 * Classe che implementa una condizione
 * NB é una classe editable
 */
public class ConditionEditable extends Condition {
    /**
     * Super del costruttore vuoto
     */
    public ConditionEditable() {
        super();
    }

    /*
     * Setter della classe condition
     */
    public void setFlag(int n){
        flag = n;
    }

}
