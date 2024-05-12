package nobinobi.obj;

/**
 * Classe che implementa l'introduzione del gioco
 */
public class Introduction extends Scene {
    /**
     * Costruttore di introduction
     * @param title Titolo
     * @param description Descrizione
     * @param image Path dell immagine
     */
    public Introduction(String title, String description, String image) {
        super(title, description, image);
    }

    /**
     * Super del costruttore vuoto
     */
    public Introduction(){
        super();
    }

    /**
     * Super del costruttore che costruisce l'introduzione partendo dal file
     * @param line linea del file da leggere
     */
    public Introduction(String line)  {
        super(line);
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
