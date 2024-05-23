package backgroundObj;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import nobinobi.*;
import nobinobi.Character;

public class Game {
    private final ArrayList<Character> characters = new ArrayList<>();
    private final ArrayList<Introduction> introductions = new ArrayList<>();
    private final ArrayList<Epilogue> epilogues = new ArrayList<>();
    private final ArrayList<ChallengeScene> challengeScenes = new ArrayList<>();
    private final ArrayList<Card> lightCards = new ArrayList<>();
    private final ArrayList<Card> darkCards = new ArrayList<>();

    private BufferedReader rdr;
    private Scanner scn;
    private Random rdm;
    private Character player;

    private Dice dice;
    private boolean bon;

    public void play() {
        chooseCharacter(scn);
        rdm = new Random();
        dice = new Dice();
        int result = 0;
        int limit = 0;
        Introduction intro = introductions.get(rdm.nextInt(introductions.size()));

        System.out.println(intro);
        //Ciclo di gioco
        for (int i = 0; i < 10; i++) {


            ChallengeScene cs = challengeScenes.get(rdm.nextInt(challengeScenes.size()));
            System.out.println(cs.getName());
            System.out.println(cs.getDescription());
            if( cs.getTechnique() == 0 ){
                limit = cs.getStrength();
                bon = true;
                System.out.println("Devi Superare una prova di " + limit + " di forza");
            }
            else if(cs.getStrength() == 0 ) {
                limit = cs.getTechnique();
                bon  = false;
                System.out.println("Devi Superare una prova di " + limit + " di tecnica");
            }
            else{

            }
            result =  dice.roll(6) + dice.roll(6);

            //Verifica dei bonus

            if(cs.checkBonus(player, 0)){
                if (bon) {


                    System.out.println("Hai fatto un tiro di " + result + " + bonus " + player.getStrength() + " con totale " + (result += player.getStrength()));
                }
                else{

                    System.out.println("Hai fatto un tiro di " + result + " + bonus " + player.getTechnique() + " con totale " + (result += player.getTechnique()));

                }
            }
            else{
                System.out.println("Hai fatto un tiro di " + result +" senza bonus");
            }
            if(result > limit){
                //Pesca carta luce
                System.out.println(cs.getWinDescription());
                System.out.println("Hai superato la prova , pesca una carta luce");
                Card lightcard = lightCards.get(rdm.nextInt(lightCards.size()));
                player.addCard(lightcard);
            }
            else{
                //Pesca carta ombra
                System.out.println(cs.getLostDescription());
                System.out.println("Hai fallito la prova pesca un a carta ombra");
                Card darkcard = darkCards.get(rdm.nextInt(darkCards.size()));
                player.addCard(darkcard);
            }

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //pesca la carta epilogo



    }

    public void chooseCharacter(Scanner scn){
        scn = new Scanner(System.in);
        readCharacter("src/characters.csv");
        readIntroduction("src/introductions.csv");
        readCS("src/challengescene.csv");
        readCard("src/cards.csv");
        readEpilogues("src/epilogue.csv");
        int o = 0;

        for(Character Char : characters){
            System.out.println(Char.getName());
            System.out.println(Char.getDescription());
            System.out.println("Forza :" + Char.getStrength());
            System.out.println("Tecnica :"  + Char.getTechnique());
            for (int i = 0; i < Char.getAbilityCount(); i++) {
                System.out.print(Char.getAbility(i) + " ");
            }
            System.out.println();
            System.out.println("Seleziona " + o + " per scegliere " + Char.getName());
            System.out.println();
            System.out.println();


            o++;
        }
        System.out.println("Scegli chi giocare ");
        o = scn.nextInt();

        player = characters.get(o);
    }
    public void readCharacter(String FileName) {
        try {
            rdr = new BufferedReader(new FileReader(new File(FileName)));
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
            rdr = new BufferedReader(new FileReader(new File(FileName)));
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
            rdr = new BufferedReader(new FileReader(new File(FileName)));
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
            rdr = new BufferedReader(new FileReader(new File(FileName)));
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
            rdr = new BufferedReader(new FileReader(new File(FileName)));
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