package nobinobi;

import nobinobi.obj.frame.AbilityFrame;
import nobinobi.obj.frame.CharacterFrame;
import nobinobi.obj.frame.IntroductionFrame;

public class Main{
    public static void main(String[] args)  {

        CharacterFrame b = new CharacterFrame();
        b.setVisible(true);

        IntroductionFrame a = new IntroductionFrame();
        a.setVisible(true);


        AbilityFrame prova = new AbilityFrame();
        prova.setVisible(true);
    }
}