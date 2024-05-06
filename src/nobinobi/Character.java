package nobinobi;

import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Character {
    protected String name;
    protected char genre;
    protected String image;
    protected String description;
    protected int strength;
    protected int tecnique;
    protected Ability[] abilities;
    protected ArrayList<Card> cards;

    public Character() {
    }

    public Character(String name, char genre, String description, String image) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.image = image;
        abilities = new Ability[6];
        cards = new ArrayList<Card>(0);
    }



    public String getName() {
        return name;
    }

    public char getGenre() {
        return genre;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getStrength() {
        return strength;
    }

    public int getStrength(Condition c) {
        int str = strength;
        for(Ability a : abilities){
            if(a.getConditions().test(c)){
                str += a.getStrength();
            }
        }
        for(Card a : cards){
            if(a.getConditions().test(c)){
                str += a.getStrength();
            }
        }
        return str;
    }

    public int getTecnique() {
        return tecnique;
    }

    public int getTecnique(Condition c) {
        int tec = tecnique;
        for(Ability a : abilities){
            if(a.getConditions().test(c)){
                tec += a.getTecnique();
            }
        }
        for(Card a : cards){
            if(a.getConditions().test(c)){
                tec += a.getTecnique();
            }
        }
        return tec;
    }

    public Ability getAbility(int index){
        return abilities[index];
    }

    public int getAbilityCount(){
        return abilities.length;
    }

    public void addCard(Card c){
        cards.add(c);
    }

    public Card getCard(int index){
        return cards.get(index);
    }

    public int getCardCount(){
        return cards.size();
    }

    public void saveToFile(PrintWriter writer){
        writer.write(toFileString());
    }

    public void loadFromFile(String line){
        String[] s = line.split("#");
        name = s[0];
        genre = s[1].charAt(0);
        image = s[2];
        description = s[3];
        strength = Integer.parseInt(s[4]);
        tecnique = Integer.parseInt(s[5]);
        String[] ab = s[6].split("@@");
        for (int i = 0; i < abilities.length; i++) {
            abilities[i].loadFromFile(ab[i]);
        }
    }

    public String toFileString(){
        String s = name + "#" + genre + "#" + image + "#" + description + "#" + strength + "#" + tecnique + "#";

        for(int i = 0; i < abilities.length-1;i++){
            s += abilities[i].toFileString();
            s += "@@";
        }
        s += abilities[abilities.length-1].toFileString();

        return s;
    }










}
