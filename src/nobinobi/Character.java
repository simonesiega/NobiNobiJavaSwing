package nobinobi;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Classe che implementa i personaggi del gioco
 */
public class Character {

    // Nome personaggio
    protected String name;
    // Genere personaggio
    protected char genre;
    // Path immagine del personaggio
    protected String image;
    // Descrizione del personaggio
    protected String description;
    // Forza
    protected int strength;
    // Tecnica
    protected int technique;
    // Array che contiene le abilitá (6)
    protected Ability[] abilities;
    // Arraylist che contiene le carte (luce o ombra - nerf/buff)
    protected ArrayList<Card> cards;

    /**
     * Costruttore vuoto
     * Inizializza tutti gli attributi vuoti
     */
    public Character() {
        this.name = "";
        this.genre = ' ';
        this.image = "";
        this.description = "";
        this.strength = 0;
        this.technique = 0;
        this.abilities = new Ability[6];
        this.cards = new ArrayList<>();
    }

    /**
     * Costruttore partendo dalla linea del file di testo
     * @param line linea da tagliare
     */
    public Character(String line) {
        this.cards = new ArrayList<>();
        String[] s = line.split("#");

        name = s[0];
        genre = s[1].charAt(0);
        image = s[2];
        description = s[3];
        strength = Integer.parseInt(s[4]);
        technique = Integer.parseInt(s[5]);

        String[] ab = s[6].split("@@");
        abilities = new Ability[6];
        for (int i = 0; i < ab.length; i++) {
            abilities[i] = new Ability(ab[i]);
            Ability tmp = new Ability(ab[i]);
            tmp.loadFromFile(ab[i]);
        }
    }

    /**
     * Costruttore del Player
     * @param name Nome
     * @param genre Genere
     * @param description Descrizione
     * @param image Path img
     */
    public Character(String name, char genre, String description, String image) {
        this.name = name;
        this.genre = genre;
        this.description = description;
        this.image = image;
        abilities = new Ability[6];
        cards = new ArrayList<>(0);
    }

    public void setImage(String image){
        this.image = image;
    }

    /*
     * Getter
     * i setter sono in editable
     */
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

    /**
     * Ricalcola la forza del Player in base alle abilitá e alle carte
     * @param c la condizione da verificare per il calcolo
     * @return la nuova forza applicata alla situazione
     */
    public int getStrength(Condition c) {
        int str = strength;
        for(Ability ability : abilities){
            if(ability.getConditions().test(c)){
                str += ability.getStrength();
            }
        }
        for(Card a : cards){
            if(a.getConditions().test(c)){
                str += a.getStrength();
            }
        }
        return str;
    }

    public int getTechnique() {
        return technique;
    }

    /**
     * Ricalcola la tecnica del Player in base alle abilitá e alle carte
     * @param c la condizione da verificare per il calcolo
     * @return la nuova forza applicata alla situazione
     */
    public int getTecnique(Condition c) {
        int tec = technique;
        for(Ability a : abilities){
            if(a.getConditions().test(c)){
                tec += a.getTechnique();
            }
        }
        for(Card a : cards){
            if(a.getConditions().test(c)){
                tec += a.getTechnique();
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

    /**
     * Legge una riga del file e la analizza costruendo un player
     * Si usa dopo aver chiamato il costruttore vuote
     * @param line linea del file da analizzare
     */
    public void loadFromFile(String line){
        String[] s = line.split("#");
        name = s[0];
        genre = s[1].charAt(0);
        image = s[2];
        description = s[3];
        strength = Integer.parseInt(s[4]);
        technique = Integer.parseInt(s[5]);
        String[] ab = s[6].split("@@");
        for (int i = 0; i < abilities.length; i++) {
            abilities[i].loadFromFile(ab[i]);
        }
    }

    /**
     * Salva il player in un file
     * @param writer oggetto PrintWriter usato per scrivere
     */
    public void saveToFile(PrintWriter writer){
        writer.write(toFileString());
    }

    /**
     * Costruisce la stringa da stampare nel file
     * @return stringa da stampare
     */
    public String toFileString(){
        StringBuilder s = new StringBuilder(name + "#" + genre + "#" + image + "#" + description + "#" + strength + "#" + technique + "#");

        for(int i = 0; i < abilities.length-1;i++){
            s.append(abilities[i].toFileString());
            s.append("@@");
        }
        s.append(abilities[abilities.length - 1].toFileString());
        s.append("\n");

        return s.toString();
    }

    @Override
    public String toString(){
        /*
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < abilities.length; i++) {
            sb.append(abilities[i].toString());
        }
        sb.append("\n");
        sb.append(name);
        return sb.toString();
        */
        return name;
    }
}
