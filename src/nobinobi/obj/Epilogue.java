package nobinobi.obj;

import java.io.PrintWriter;

public class Epilogue extends Scene{
    private Condition condition;
    private String description;
    private int strength;
    private int technique;
    private String winDescription;
    private String lostDescription;

    public Epilogue(){
        this.condition = null;
        this.description = "";
        this.strength = 0;
        this.technique = 0;
        this.winDescription = "";
        this.lostDescription = "";
    }

    public Epilogue(String file){
        String[] value = file.split("#");
        description=value[0];
        strength = Integer.parseInt(value[1]);
        technique = Integer.parseInt(value[2]);
        winDescription = value[3];
        lostDescription = value[4];
        condition = new Condition(Integer.parseInt(value[5]));
    }

    public Epilogue(Condition c, String d, int s, int t, String win, String lost){
        this.condition = c;
        this.description = d;
        this.strength = s;
        this.technique = t;
        this.winDescription = win;
        this.lostDescription = lost;
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

    /**
     * Costruisce la stringa
     * @return stringa
     */
    public String toString(){
        return "EPILOGUE " + description;
    }

    @Override
    public void saveToFile(PrintWriter out){
        out.println(description + "#" + strength + "#" + technique + "#" + winDescription + "#" + lostDescription + "#" + condition);
    }

    @Override
    public Scene loadFromFile (String line) {
        return new Epilogue(line);
    }
}
