package nobinobi.obj;

import java.io.PrintWriter;

/**
 * Classe che implementa le abilitá di ogni personaggio
 */
public class Ability {

    // Nome abilitá
    protected String name;
    // Descrizione dell abilitá
    protected String description;
    // Condizioni in cui si puó usare l abilitá
    protected Condition conditions;

    // Parametri dell abilitá
    protected int strength;
    protected int technique;

    /**
     * Costruttore vuoto
     * Inizializza tutti gli attributi vuoti
     */
    public Ability() {
        this.name = "";
        this.description = "";
        this.conditions = new Condition();
        this.strength = 0;
        this.technique = 0;
    }

    /**
     * Costruttore di Abilitá che parte dalla stringa letta da un file
     * @param line riga da analizzare
     */
    public Ability(String line) {
        String[] l = line.split("@");
        this.name = l[0];
        this.description = l[1];
        this.conditions = new Condition(Integer.parseInt(l[2]));
        this.strength = Integer.parseInt(l[3]);
        this.technique = Integer.parseInt(l[4]);
    }

    /**
     * Costruttore di Abilitá
     * @param conditions Condizione
     * @param strength Forza
     * @param technique Tecnica
     */
    public Ability(Condition conditions, int strength, int technique) {
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
     * Legge una riga del file e la analizza costruendo un abilitá
     * Si usa dopo aver chiamato il costruttore vuote
     * @param line linea del file da analizzare
     */
    public void loadFromFile(String line){
        String[] l = line.split("@");
        name = l[0];
        description = l[1];
        conditions = new Condition(Integer.parseInt(l[2]));
        strength = Integer.parseInt(l[3]);
        technique = Integer.parseInt(l[4]);
    }

    /**
     * Salva l'abilitá in un file
     * @param writer oggetto PrintWriter usato per scrivere
     */
    public void saveToFile(PrintWriter writer){
        writer.write(toFileString() + "\n");
    }

    /**
     * Costruisce la stringa da stampare nel file
     * @return stringa da stampare
     */
    public String toFileString(){
        return name + "@" + description + "@" + conditions.getCondition() + "@" + strength + "@" + technique;
    }

    /**
     * Metodo toString della classe obj
     * @return String
     */
    @Override
    public String toString(){
        return name;
    }

}
