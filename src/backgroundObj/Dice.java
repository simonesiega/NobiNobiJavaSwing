package backgroundObj;

import java.util.Random;

public class Dice {

    public Dice(){

    }

    public int roll(int max){
        Random rand = new Random();
        return rand.nextInt(max);
    }
}
