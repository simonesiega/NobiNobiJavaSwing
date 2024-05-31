package backgroundObj;

import java.util.Random;

/**
 * Classe adibita al lancio di dadi
 */
public class Dice {

    public Dice(){
    }

    /**
     * Metodo di lancio del dado
     * @param max numero massimo
     * @return
     */
    public int roll(int max){
        Random rand = new Random();
        return rand.nextInt(max) + 1;
    }

    /**
     * Metodo che verifica l'uguaglianza tra due roll
     * @param max numero massimo
     * @return
     */
    public boolean equalsNumber(int max){
        return (roll(max) == roll(max));
    }
}
