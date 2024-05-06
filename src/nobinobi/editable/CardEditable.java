package nobinobi.editable;

import nobinobi.Card;
import nobinobi.Condition;

import java.io.PrintWriter;

public class CardEditable extends Card {


    public CardEditable() {}


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

    public void setTecnique(int tecnique) {
        this.tecnique = tecnique;
    }



}