package nobinobi.obj.editable;

import nobinobi.obj.Condition;
import nobinobi.obj.Epilogue;

public class EpilogueEditable extends Epilogue {
    public EpilogueEditable() {
        super();
    }

    public EpilogueEditable(String file) {
        super(file);
    }


    /**metodi setter **/
    public void setName(String s){
        this.name = s;
    }
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setTechnique(int technique) {
        this.technique = technique;
    }

    public void setWinDescription(String winDescription) {
        this.winDescription = winDescription;
    }

    public void setLostDescription(String lostDescription) {
        this.lostDescription = lostDescription;
    }
}
