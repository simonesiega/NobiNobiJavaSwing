package nobinobi;

import java.io.PrintWriter;

/**
 * Classe che implementa l'introduzione del gioco
 */
public class Introduction extends Scene {

    private Condition endCondition;

    /**
     * Costruttore di introduction
     * @param title Titolo
     * @param description Descrizione
     * @param image Path dell immagine
     */
    public Introduction(String title, String description, String image, Condition end) {
        super(title, description, image);
        this.endCondition = end;
    }

    /**
     * Super del costruttore vuoto
     */
    public Introduction(){
        super();
        this.endCondition = null;
    }

    /**
     * Super del costruttore che costruisce l'introduzione partendo dal file
     * @param line linea del file da leggere
     */
    public Introduction(String line)  {
        String[] value= line.split("#");
        title=value[0];
        description=value[1];
        image=value[2];
        // Condition endCondition = new Condition(Integer.parseInt(value[3]));
    }

    public Condition getEndCondition () {
        return endCondition;
    }

    @Override
    public void saveToFile(PrintWriter out){
        out.println(this.getTitle()+"#"+this.getDescription()+"#"+this.getImage() +"#" + this.getEndCondition());
    }

    /**
     * Legge una riga del file
     * Crea una nuova introduzione tramite il costruttore
     * @param line linea del file da analizzare
     */
    public Scene loadFromFile(String line){
        return new Introduction(line);
    }
}
