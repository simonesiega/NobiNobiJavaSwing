package nobinobi.editable;

import nobinobi.ChallengeScene;
import nobinobi.Condition;

public class ChallengeSceneEditable extends ChallengeScene {
    public ChallengeSceneEditable() {
        super();
    }

    public ChallengeSceneEditable(String file) {
        super(file);
    }


    /**setter               */
    public void setName(String n){
        this.name = n;
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

    public void setEndCondition(Condition endCondition) {
        this.endCondition = endCondition;
    }
}