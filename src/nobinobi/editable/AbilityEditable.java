package nobinobi.editable;

import nobinobi.Ability;
import nobinobi.Condition;

/**
 * Classe che implementa le abilitá di ogni personaggio
 * NB é una classe editable
 */
public class AbilityEditable extends Ability {
    /**
     * Super del costruttore vuoto
     */
    public AbilityEditable() {
        super();
    }

    public AbilityEditable(String line) {
        super(line);
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
