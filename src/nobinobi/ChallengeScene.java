package nobinobi;

import java.io.PrintWriter;

public class ChallengeScene extends Scene {
    protected String name;
    protected Condition condition;
    protected  String description;
    protected  int strength;
    protected  int technique;
    protected  String winDescription;
    protected  String lostDescription;
    protected  Condition endCondition;

    public ChallengeScene() {
        this.name = "";
        this.condition = null;
        this.description = "";
        this.strength = 0;
        this.technique = 0;
        this.winDescription = "";
        this.lostDescription = "";
        this.endCondition = null;
    }

    public ChallengeScene(String file){
        String[] value = file.split("#");
        name = value[0];
        description=value[1];
        strength = Integer.parseInt(value[2]);
        technique = Integer.parseInt(value[3]);
        winDescription = value[4];
        lostDescription = value[5];
        endCondition = new Condition(Integer.parseInt(value[6]));
        condition = new Condition(Integer.parseInt(value[7]));
    }

    public ChallengeScene(String name , Condition c, String d, int s, int t, String win, String lost, Condition end){
        this.name = name;
        this.condition = c;
        this.description = d;
        this.strength = s;
        this.technique = t;
        this.winDescription = win;
        this.lostDescription = lost;
        this.endCondition = end;
    }

    public String getName() {return name;
    }

    public Condition getCondition () {
        return condition;
    }

    public String getDescription () {
        return description;
    }

    public int getTechnique () {
        return technique;
    }

    public int getStrength () {
        return strength;
    }

    public String getWinDescription () {
        return winDescription;
    }

    public String getLostDescription () {
        return lostDescription;
    }

    public Condition getEndCondition () {
        return endCondition;
    }

    public boolean checkBonus(Character pl, int index){
        if (index >= pl.getAbilityCount()) {
            return false;
        } else {
            if (pl.getAbility(index).getConditions().test(this.condition)) {
                return true;
            }
            else return checkBonus(pl, index+1);
        }
    }

    public int bonusStrength(Character pl, int index){
        if (index >= pl.getAbilityCount()) {
            return 0;
        } else {
            if (pl.getAbility(index).getConditions().test(this.condition)){
                return pl.getAbility(index).getStrength() + bonusStrength(pl, index + 1);
            }
            else return bonusStrength(pl, index + 1);
        }
    }

    public int bonusTechnic(Character pl, int index){
        if (index >= pl.getAbilityCount()) {
            return 0;
        } else {
            if (pl.getAbility(index).getConditions().test(this.condition)){
                return pl.getAbility(index).getTechnique() + bonusTechnic(pl, index + 1);
            }
            else return bonusTechnic(pl, index + 1);
        }
    }

    /**
     * Costruisce la stringa
     * @return stringa
     */
    public String toString(){
        return name;
    }

    @Override
    public void saveToFile(PrintWriter out){
        out.println(name + "#" + description + "#" + strength + "#" + technique + "#" + winDescription + "#" + lostDescription + "#" + endCondition.getCondition() + "#" + condition.getCondition());
    }

    @Override
    public Scene loadFromFile (String line) {
        return new ChallengeScene(line);
    }

    public String saveFinal(){
        return "TITLE: "+name+"\n"+ "DESCRIPTION: "+description+"\n";
    }
}