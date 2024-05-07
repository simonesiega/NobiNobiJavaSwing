package nobinobi.obj;

import java.io.PrintWriter;

/**
 * Classe astratta
 * Implementa tutte le scene del gioco (Introduction, epilogue...)
 */
abstract class Scene {

    // Titolo della scena
    protected String title;
    // Descrizione della scena
    protected String description;
    // Path immagine della scena
    protected String image;

    /**
     * Costruttore vuoto
     * Inizializza tutti gli attributi vuoti
     */
    public Scene() {
        this.title = "";
        this.description = "";
        this.image = "";
    }

    /**
     * Costruttore di scena (Non puó essere istanziata perché abstract)
     * @param file riga da analizzare
     */
     public Scene(String file){
         String[] value= file.split("#");
         /*
         if (value.length == 3) {
             title=value[0];
             description=value[1];
             image=value[2];
         }
         */
         title=value[0];
         description=value[1];
         image=value[2];
     }

    /**
     * Costruttore di scena con tutti e 3 i parametri
     * @param title Titolo
     * @param description Descrizione
     * @param image Path immagine
     */
    public Scene(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    // Getter
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getImage() {
        return image;
    }

    /**
     * Metodo astratto che definisce come caricare una Scena
     * @param line riga da analizzare
     * @return Scena costruita
     */
    public abstract Scene loadFromFile(String line);

    /**
     * Salva l'abilitá in un file
     * @param out oggetto PrintWriter usato per scrivere
     */
    public void saveToFile(PrintWriter out){
        // out.println(this.getTitle()+"#"+this.getDescription()+"#"+this.getImage() +"#");
        out.println(this.getTitle());
    }

    /**
     * Costruisce la stringa
     * @return stringa
     */
    public String toString(){
        // return "TITLE: "+title+"\n"+ "DESCRIPTION: "+description+"\n"+ "IMAGE: "+image+"\n";
        return title;
    }
}
