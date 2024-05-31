package backgroundObj;

import nobinobi.editable.frame.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce il set up del gioco
 */
public class InitGame {
    private final List<FrameData> frames;
    private int currentIndex;
    private boolean finish;

    /**
     * Aggiunge ciascun frame ad un arraylist di frame
     */
    public InitGame() {
        frames = new ArrayList<>();

        // Aggiungi i frame con i relativi parametri
        // Da Modificare in base ai valori minimi
        frames.add(new FrameData(new IntroductionFrame(), "src/saves/dates/introductions.csv", 1));
        frames.add(new FrameData(new AbilityFrame(), "src/saves/dates/abilities.csv", 6));
        frames.add(new FrameData(new CharacterFrame(), "src/saves/dates/characters.csv", 1));
        frames.add(new FrameData(new ChallengeSceneFrame(), "src/saves/dates/challengescene.csv", 1));
        frames.add(new FrameData(new CardFrame(), "src/saves/dates/cards.csv", 3));
        frames.add(new FrameData(new EpilogueFrame(), "src/saves/dates/epilogue.csv", 3));

        currentIndex = 0;
        finish = false;
    }

    /**
     * Init set up
     */
    public void initGame() {
        showNextFrame();
    }

    /**
     * Funzione ricorsiva che apre ognuno dei singoli frame
     */
    private void showNextFrame() {
        // Se continua la ricorsione
        if (currentIndex < frames.size()) {
            FrameData frameData = frames.get(currentIndex);
            Frame frame = frameData.frame();
            if (frame instanceof JFrame jFrame) {
                jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                jFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        try {
                            boolean acceptClose = canClose(frameData.filename(), frameData.minLines());
                            if (acceptClose) {
                                jFrame.dispose();
                                currentIndex++;
                                showNextFrame();
                            } else {
                                System.out.println("Devi inserire tutti i parametri");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("Errore durante la chiusura del frame: " + ex.getMessage());
                        }
                    }
                });
                jFrame.setVisible(true);
            }
        } else {
            System.out.println("Tutti i frame sono stati completati.");
            finish = true;
        }
    }

    /**
     * Controlla se sono soddisfatti i requisiti per chiudere il frame
     * @param filename nomne del file da cui leggere
     * @param min minimo righe di dati da inserire per poter chiudere
     * @return true se puó chiudere, altrimenti false
     */
    private boolean canClose(String filename, int min) {
        int c = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            while (br.readLine() != null) {
                c++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File non trovato: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Errore di I/O durante la lettura del file: " + filename);
        }
        return c >= min;
    }

    /*
        // Classe per contenere le informazioni sui frame
        private static class FrameData {
            private final Frame frame;
            private final String filename;
            private final int minLines;

            public FrameData(Frame frame, String filename, int minLines) {
                this.frame = frame;
                this.filename = filename;
                this.minLines = minLines;
            }

            public Frame getFrame() {
                return frame;
            }

            public String getFilename() {
                return filename;
            }

            public int getMinLines() {
                return minLines;
            }
        }
    */

    //Metodi impliciti getter
    /**
     * Record class che tiene traccia dei Frame
     * @param frame Frame
     * @param filename nome del file corrispondente
     * @param minLines requisito linee
     */
    private record FrameData(Frame frame, String filename, int minLines) {}
}
