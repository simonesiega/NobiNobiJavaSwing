package backgroundObj;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import nobinobi.*;
import nobinobi.Character;

public class GameTestuale {
    private final ArrayList<Character> characters = new ArrayList<>();
    private final ArrayList<Introduction> introductions = new ArrayList<>();
    private final ArrayList<Epilogue> epilogues = new ArrayList<>();
    private final ArrayList<ChallengeScene> challengeScenes = new ArrayList<>();
    private final ArrayList<Card> lightCards = new ArrayList<>();
    private final ArrayList<Card> darkCards = new ArrayList<>();
    private BufferedReader rdr;
    private Random rdm;
    private Character player;

    private Dice dice;
    // Variabili ausiliarie per il Tiro di Dadi
    int result = 0;
    int limit = 0;

    public void play() {
        chooseCharacter();
        rdm = new Random();
        dice = new Dice();


        //Introduzione
        Introduction intro = introductions.get(rdm.nextInt(introductions.size()));
        System.out.println(intro);


        //Ciclo di gioco
        GameLoop(10);


        //pesca la carta epilogo



    }

    public void GameLoop(int Round){
        Scanner scn = new Scanner(System.in);
        boolean bon;
        for (int i = 0; i < Round; i++) {
            ChallengeScene cs = challengeScenes.get(rdm.nextInt(challengeScenes.size()));
            System.out.println(cs.getName());
            System.out.println(cs.getDescription());
            System.out.println("Premi qualsiasi tasto per continuare");
            scn.next();


            //Verifica se è una prova di forza o di tecnica o  fa scegliere al player
            if( cs.getTechnique() == 0 ){
                limit = cs.getStrength();
                bon = true;
                System.out.println("Devi Superare una prova di " + limit + " di forza");

            }
            else if(cs.getStrength() == 0 ) {
                limit = cs.getTechnique();
                bon = false;
                System.out.println("Devi Superare una prova di " + limit + " di tecnica");

            }
            else{
                System.out.println("Scegli tra una prova di forza " + cs.getStrength() + " oppure una di tecnica " + cs.getTechnique());
                System.out.println("Digita 1 per forza e 2 per tecnica");

                int tmp = scn.nextInt();
                if(tmp == 1){
                    limit = cs.getStrength();
                    bon = true;
                    System.out.println("Devi Superare una prova di " + limit + " di forza");
                }
                else{
                    limit = cs.getTechnique();
                    bon = false;
                    System.out.println("Devi Superare una prova di " + limit + " di tecnica");
                }
            }
            System.out.println("Premi qualsiasi tasto per continuare");
            scn.next();

            //Tiro dei Dadi
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

            //Verifica del Tiro
            if(result > limit){
                System.out.println(cs.getWinDescription());

                //Pesca Carta Luce
                System.out.println("Hai superato la prova , pesca una carta luce");
                Card lightcard = lightCards.get(rdm.nextInt(lightCards.size()));
                System.out.println(lightcard.getDescription());
                player.addCard(lightcard);
            }

            else{
                System.out.println(cs.getLostDescription());

                //Pesca Carta Ombra
                System.out.println("Hai fallito la prova pesca un a carta ombra");
                Card darkcard = darkCards.get(rdm.nextInt(darkCards.size()));
                System.out.println(darkcard.getDescription());
                player.addCard(darkcard);
            }


            System.out.println("Premi qualsiasi tasto per continuare");
            scn.next();
        }


        Epilogue epilogue = epilogues.get(rdm.nextInt(epilogues.size()));
        System.out.println("E' ARRIVATA LA PROVA FINALE");
        System.out.println();
        System.out.println(epilogue.getName());
        System.out.println(epilogue.getDescription());
        System.out.println("Premi qualsiasi tasto per continuare");
        scn.next();


        //Verifica se è una prova di forza o di tecnica o  fa scegliere al player
        if( epilogue.getTechnique() == 0 ){
            limit = epilogue.getStrength();
            bon = true;
            System.out.println("Devi Superare una prova di " + limit + " di forza");

        }
        else if(epilogue.getStrength() == 0 ) {
            limit = epilogue.getTechnique();
            bon = false;
            System.out.println("Devi Superare una prova di " + limit + " di tecnica");

        }
        else{
            System.out.println("Scegli tra una prova di forza " + epilogue.getStrength() + " oppure una di tecnica " + epilogue.getTechnique());
            System.out.println("Digita 1 per forza e 2 per tecnica");

            int tmp = scn.nextInt();
            if(tmp == 1){
                limit = epilogue.getStrength();
                bon = true;
                System.out.println("Devi Superare una prova di " + limit + " di forza");
            }
            else{
                limit = epilogue.getTechnique();
                bon = false;
                System.out.println("Devi Superare una prova di " + limit + " di tecnica");
            }
        }
        System.out.println("Premi qualsiasi tasto per continuare");
        scn.next();

        //Tiro dei Dadi
        result =  dice.roll(6) + dice.roll(6);

        //Verifica dei bonus
        if(epilogue.checkBonus(player, 0)){
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
    }

    public void chooseCharacter(){
        Scanner scn = new Scanner(System.in);
        readCharacter("src/saves/characters.csv");
        readIntroduction("src/saves/introductions.csv");
        readCS("src/saves/challengescene.csv");
        readCard("src/saves/cards.csv");
        readEpilogues("src/saves/epilogue.csv");
        /*
        readCharacter("src/characters.csv");
        readIntroduction("src/introductions.csv");
        readCS("src/challengescene.csv");
        readCard("src/cards.csv");
        readEpilogues("src/epilogue.csv");
        */
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