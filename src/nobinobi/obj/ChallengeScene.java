package nobinobi.obj;

import java.io.PrintWriter;

public class ChallengeScene extends Scene{
    private Condition condition;
    private String description;
    private int strength;
    private int technique;
    private String winDescription;
    private String lostDescription;
    private String endCondition;

    public ChallengeScene() {
        this.condition = null;
        this.description = "";
        this.strength = 0;
        this.technique = 0;
        this.winDescription = "";
        this.lostDescription = "";
        this.endCondition = "";
    }

    public ChallengeScene(String file){
        String[] value = file.split("#");
        description=value[0];
        strength = Integer.parseInt(value[1]);
        technique = Integer.parseInt(value[2]);
        winDescription = value[3];
        lostDescription = value[4];
        endCondition = value[5];
        condition = new Condition(Integer.parseInt(value[6]));
    }

    public ChallengeScene(Condition c, String d, int s, int t, String win, String lost, String end){
        this.condition = c;
        this.description = d;
        this.strength = s;
        this.technique = t;
        this.winDescription = win;
        this.lostDescription = lost;
        this.endCondition = end;
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

    public String getEndCondition () {
        return endCondition;
    }

    /**
     * Costruisce la stringa
     * @return stringa
     */
    public String toString(){
        return "CHALLENGESCENE " + description;
    }

    @Override
    public void saveToFile(PrintWriter out){
        out.println(description + "#" + strength + "#" + technique + "#" + winDescription + "#" + lostDescription + "#" + endCondition + "#" + condition);
    }

    @Override
    public Scene loadFromFile (String line) {
        return new ChallengeScene(line);
    }
}
