package nobinobi.obj;

import java.io.PrintWriter;

/**
 * Classe che implementa le carte
 * Da aggiungere distinzione Luce e Ombra
 */
public class Card {

    // Nome della carta
    protected String name;
    // Descrizione della carta
    protected String description;
    // Condizione della carta
    protected Condition conditions;

    // Parametri della carta
    protected int strength;
    protected int technique;

    /**
     * Costruttore vuoto
     * Inizializza tutti gli attributi vuoti
     */
    public Card() {
        this.name = "";
        this.description = "";
        this.conditions = new Condition();
        this.strength = 0;
        this.technique = 0;
    }

    public Card(String line){
        String[] l = line.split("#");
        name = l[0];
        description = l[1];
        conditions = new Condition(Integer.parseInt(l[2]));
        strength = Integer.parseInt(l[3]);
        technique = Integer.parseInt(l[4]);
    }

    /**
     * Costruttore della carta
     * @param conditions Condizione
     * @param strength Forza
     * @param technique Tecnica
     */
    public Card(Condition conditions, int strength, int technique) {
        this.conditions = conditions;
        this.strength = strength;
        this.technique = technique;
    }

    /*
     * Getter
     * i setter sono in editable
     */
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
    public int getTechnique() {
        return technique;
    }

    /**
     * Legge una riga del file e la analizza costruendo una carta
     * Si usa dopo aver chiamato il costruttore vuote
     * @param line linea del file da analizzare
     */
    public void loadFromFile(String line){
        String[] l = line.split("#");
        name = l[0];
        description = l[1];
        conditions = new Condition(Integer.parseInt(l[2]));
        strength = Integer.parseInt(l[3]);
        technique = Integer.parseInt(l[4]);
    }

    /**
     * Salva la carta in un file
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
        String s = name + "#" + description + "#" + conditions.getCondition() + "#" + strength + "#" + technique;
        return s;
    }

    @Override
    public String toString(){
        return name;
    }
}