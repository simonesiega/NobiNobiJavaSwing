package nobinobi.obj.editable;
import nobinobi.obj.Introduction;

/**
 * Classe che implementa l'introduzione del gioco
 * NB é una classe editable
 */
public class IntroductionEditable extends Introduction{
    /**
     * Super del costruttore vuoto
     */
    public IntroductionEditable(){}

    /**
     * Super del costruttore che costruisce l'introduzione partendo dal file
     * @param line linea del file da leggere
     */
    public IntroductionEditable(String line){
        super(line);
    }

    /*
     * Setter della classe ability
     */
    public void setTitle    (String title) {
        this.title = title;
    }
    public  void setDescription(String description) {
        this.description = description;
    }
    public void setImage(String image) {
        this.image = image;
    }



}
