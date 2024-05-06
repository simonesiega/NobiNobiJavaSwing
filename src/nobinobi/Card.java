package nobinobi;

import java.io.PrintWriter;

public class Card {

    protected String name;
    protected String description;
    protected Condition conditions;
    protected int strength;
    protected int tecnique;

    public Card() {}

    public Card(Condition conditions, int strength, int tecnique) {
        this.conditions = conditions;
        this.strength = strength;
        this.tecnique = tecnique;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Condition getConditions() {
        return conditions;
    }

    public int getStrength() {
        return strength;
    }

    public int getTecnique() {
        return tecnique;
    }




    public void loadFromFile(String line){
        String[] l = line.split("#");
        name = l[0];
        description = l[1];
        conditions = new Condition(Integer.parseInt(l[2]));
        strength = Integer.parseInt(l[3]);
        tecnique = Integer.parseInt(l[4]);
    }


    public void saveToFile(PrintWriter writer){
        writer.write(toFileString());
    }

    public String toFileString(){
        String s = name + "#" + description + "#" + conditions.getCondition() + "#" + strength + "#" + tecnique;
        return s;
    }


}