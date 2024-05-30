package backgroundObj.swing;

import nobinobi.*;
import nobinobi.Character;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe adibita alla lettura da file
 */
public class ReaderFile {
    private final ArrayList<Character> characters = new ArrayList<>();
    private final ArrayList<Introduction> introductions = new ArrayList<>();
    private final ArrayList<Epilogue> epilogues = new ArrayList<>();
    private final ArrayList<ChallengeScene> challengeScenes = new ArrayList<>();
    private final ArrayList<Card> lightCards = new ArrayList<>();
    private final ArrayList<Card> darkCards = new ArrayList<>();
    private BufferedReader rdr;

    public ReaderFile(){
        readCharacter("src/saves/dates/characters.csv");
        readIntroduction("src/saves/dates/introductions.csv");
        readCS("src/saves/dates/challengescene.csv");
        readCard("src/saves/dates/cards.csv");
        readEpilogues("src/saves/dates/epilogue.csv");
    }

    public ArrayList<Character> getCharacters () {
        return characters;
    }

    public ArrayList<Introduction> getIntroductions () {
        return introductions;
    }

    public ArrayList<Epilogue> getEpilogues () {
        return epilogues;
    }

    public ArrayList<ChallengeScene> getChallengeScenes () {
        return challengeScenes;
    }

    public ArrayList<Card> getDarkCards () {
        return darkCards;
    }

    public ArrayList<Card> getLightCards () {
        return lightCards;
    }

    public BufferedReader getRdr () {
        return rdr;
    }

    public void readCharacter(String FileName) {
        try {
            rdr = new BufferedReader(new FileReader(FileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String tmp = rdr.readLine();
            while (tmp != null) {


                characters.add(new Character(tmp));
                tmp = rdr.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readIntroduction(String FileName) {
        try {
            rdr = new BufferedReader(new FileReader(FileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String tmp = rdr.readLine();
            while (tmp != null) {


                introductions.add(new Introduction(tmp));
                tmp = rdr.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readEpilogues(String FileName) {
        try {
            rdr = new BufferedReader(new FileReader(FileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String tmp = rdr.readLine();
            while (tmp != null) {


                epilogues.add(new Epilogue(tmp));
                tmp = rdr.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCS(String FileName) {
        try {
            rdr = new BufferedReader(new FileReader(FileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String tmp = rdr.readLine();
            while (tmp != null) {
                challengeScenes.add(new ChallengeScene(tmp));
                tmp = rdr.readLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void readCard(String FileName) {
        try {
            rdr = new BufferedReader(new FileReader(FileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            String tmp = rdr.readLine();
            while (tmp != null) {
                lightCards.add(new Card(tmp));
                darkCards.add(new Card(tmp));
                tmp = rdr.readLine();

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
