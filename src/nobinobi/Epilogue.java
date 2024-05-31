package nobinobi;
import java.io.PrintWriter;

/**
 * Classe epilogo
 * Nella nuova implementazione estende cs
 */
public class Epilogue extends ChallengeScene {

    /**
     * Costruttore vuoto della classe
     * Imposta tutto a vuoto/null
     */
    public Epilogue(){
        this.name = "";
        this.condition = null;
        this.description = "";
        this.strength = 0;
        this.technique = 0;
        this.winDescription = "";
        this.lostDescription = "";
    }

    /**
     * Costruttore partendo dalla linea del file di testo
     * @param line linea da tagliare
     */
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

    /**
     * Costruttore completo
     * @param n nome
     * @param c condizione
     * @param d descrizione
     * @param s forza
     * @param t tecnica
     * @param win descrizione caso vittoria
     * @param lost descrizione caso persa
     */
    public Epilogue(String n, Condition c, String d, int s, int t, String win, String lost){
        this.name = n;
        this.condition = c;
        this.description = d;
        this.strength = s;
        this.technique = t;
        this.winDescription = win;
        this.lostDescription = lost;
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
