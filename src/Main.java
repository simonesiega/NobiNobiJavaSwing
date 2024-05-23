import backgroundObj.GameSwing;
import backgroundObj.GameTestuale;
import backgroundObj.InitGame;

public class Main {
    public static void main(String[] args) {
        InitGame init = new InitGame();
        init.initGame();

        //Finché tutti i frame non sono conclusi non va avanti
        while (!init.getFinish()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
        GameTestuale test = new GameTestuale();
        test.play();
        */

        GameSwing swing = new GameSwing();
        swing.setVisible(true);
    }
}