package nobinobi.obj.editable;

import nobinobi.obj.Card;
import nobinobi.obj.Condition;

/**
 * Classe che implementa le carte del gioco
 * NB é una classe editable
 */
public class CardEditable extends Card {
    /**
     * Super del costruttore vuoto
     */
    public CardEditable() {
        super();
    }

    /*
     * Setter della classe ability
     */
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setConditions(Condition conditions) {
        this.conditions = conditions;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setTechnique(int technique) {
        this.technique = technique;
    }



}