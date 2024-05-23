package backgroundObj;

import nobinobi.editable.frame.*;

import java.awt.*;
import java.io.*;

public class InitGame {
    private IntroductionFrame introductionFrame;
    private EpilogueFrame epilogueFrame;
    private CharacterFrame characterFrame;
    private ChallengeSceneFrame challengeSceneFrame;
    private CardFrame cardFrame;
    private AbilityFrame abilityFrame;

    private boolean alreadyClosed;

    public InitGame(){
        introductionFrame = new IntroductionFrame();
        epilogueFrame = new EpilogueFrame();
        characterFrame = new CharacterFrame();
        challengeSceneFrame = new ChallengeSceneFrame();
        cardFrame = new CardFrame();
        abilityFrame = new AbilityFrame();

        alreadyClosed = false;
    }

    public void initGame(){
        introductionFrame.setVisible(true);
       while (!canClose("src/introductions.csv", 3, introductionFrame)){
           if (!introductionFrame.isActive()){
               if (alreadyClosed) System.out.println("Devi compilare tutti i parametri prima di poter chiudere");
               introductionFrame.setVisible(true);
               alreadyClosed = true;
           }
       }
        System.out.println("-------------------------------------------------");
       alreadyClosed = false;
    }

    private boolean canClose(String filename, int min, Frame frame){
        int c = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
            while (br.readLine() != null){
                c++;
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (c >= min && !frame.isActive()){
            return true;
        }
        return false;
    }
}
