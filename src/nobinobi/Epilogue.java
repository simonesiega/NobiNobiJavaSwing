package nobinobi;
import java.io.PrintWriter;

public class Epilogue extends ChallengeScene {
    /*protected String name;
    protected Condition condition;
    protected String description;
    protected int strength;
    protected int technique;
    protected String winDescription;
    protected String lostDescription;*/

    public Epilogue(){
        this.name = "";
        this.condition = null;
        this.description = "";
        this.strength = 0;
        this.technique = 0;
        this.winDescription = "";
        this.lostDescription = "";
    }

    public Epilogue(String line){
        String[] value = line.split("#");
        name = value[0];
        description=value[1];
        strength = Integer.parseInt(value[2]);
        technique = Integer.parseInt(value[3]);
        winDescription = value[4];
        lostDescription = value[5];
        condition = new Condition(Integer.parseInt(value[6]));
    }

    public Epilogue(String n, Condition c, String d, int s, int t, String win, String lost){
        this.name = n;
        this.condition = c;
        this.description = d;
        this.strength = s;
        this.technique = t;
        this.winDescription = win;
        this.lostDescription = lost;
    }

    public String getName () {
        return name;
    }

    public Condition getCondition () {
        return condition;
    }

    public String getDescription () {
        return description;
    }

    public int getStrength () {
        return strength;
    }

    public int getTechnique () {
        return technique;
    }

    public String getWinDescription () {
        return winDescription;
    }

    public String getLostDescription () {
        return lostDescription;
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

    /**
     * Costruisce la stringa
     * @return stringa
     */
    public String toString(){
        return name;
    }

    @Override
    public void saveToFile(PrintWriter out){
        out.println(name + "#" + description + "#" + strength + "#" + technique + "#" + winDescription + "#" + lostDescription + "#" + condition.getCondition());
    }

    @Override
    public Scene loadFromFile (String line) {
        return new Epilogue(line);
    }
}
