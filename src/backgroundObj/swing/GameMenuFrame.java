package backgroundObj.swing;

import nobinobi.ChallengeScene;
import nobinobi.Character;
import nobinobi.Epilogue;
import nobinobi.Introduction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe che gestisce il menu del gioco
 */
public class GameMenuFrame extends JFrame implements ActionListener{
    private final Random random = new Random();
    private int round = 0;
    private final ReaderFile RF = new ReaderFile();
    private final Character Player;
    private final ArrayList<Introduction> Introductions = RF.getIntroductions();
    private final ArrayList<ChallengeScene> challengeScenes = RF.getChallengeScenes();
    private final JButton nextButton;
    private final JTextArea textArea;
    private ChallengeScene cs;
    private ChallengeScene cs2;
    private Introduction intro;
    private final CreateHtmlFile saveF = new CreateHtmlFile("src/saves/gameplay/finalprint/Print.html");

    private final int nRound;

    public GameMenuFrame(int r, Character player){
        this.Player = player;

        nRound = r;

        setTitle("Swing main.Game");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        saveF.initFile();

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        nextButton = createButton("Next");
        buttonPanel.add(nextButton);
        nextButton.addActionListener(this);

        add(buttonPanel, BorderLayout.SOUTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);

        play();
    }

    /**
     * Crea il bottone next
     * @param label nome del bottone
     * @return il bottone
     */
    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (round > nRound) {
            nextButton.setEnabled(false);
            try {
                Thread.sleep(2000);   //Cambiare il tempo se si vuole subito la stampa
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            this.dispose();
            saveF.openDocument();
        }
        else handleNextButton();
    }

    /**
     * Controlla cosa deve fare il bottone next
     * cs => challengescene
     * cs2 => challengescene pari
     *
     * quando pesco la challengescene controllo la endcondition della cs precedente utilizzando il metodo Pick()
     */
    private void handleNextButton() {

        if (cs != null) {
            cs2 = cs;
        }

        // Caso epilogo
        if (round == nRound) {
            Epilogue ep = PickEpilogue(cs2);

            textArea.append("\nEPILOGO: ");
            textArea.append(ep.getName() + "\n");
            textArea.append(ep.getDescription());

            RoundFrame rf = new RoundFrame(Player, ep, round);
            rf.setVisible(true);
            rf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            saveF.saveLine("<br><h1 id='epilogue-title'>" + "EPILOGO: " + ep.getName() + "</h1>\n<p id='epilogue-description'>" + ep.getDescription() + "</p>\n");
            saveF.finishFile();
        }

        // Round normale
        else {
            RoundFrame roundFrame;
            //Se è la prima cs la pesca dalla endcondition della introduzione
            if (round == 1) {
                cs = Pick(intro);

                roundFrame = new RoundFrame(Player, cs, round);
                saveF.saveLine("<h3 class='round-title'>Round " + round + ": " + cs.getName() + "</h3>\n<p class='round-description'>" + cs.getDescription() + "</p>\n<br>");
                textArea.append("Round " + round + ": " + cs.getName() + "\n");
            }
            else {

                //se il round è pari pesco dalla endcondition di cs
                if (round % 2 == 0) {
                    cs2 = Pick(cs);
                    roundFrame = new RoundFrame(Player, cs2, round);
                    saveF.saveLine("<h3 class='round-title'>Round " + round + ": " + cs2.getName() + "</h3>\n<p class='round-description'>" + cs2.getDescription() + "</p>\n<br>");
                    textArea.append("Round " + round + ": " + cs2.getName() + "\n");
                }// se è dispari pesco dalla endocondition di cs2
                else {
                    cs = Pick(cs2);
                    roundFrame = new RoundFrame(Player, cs, round);
                    saveF.saveLine("<h3 class='round-title'>Round " + round + ": " + cs.getName() + "</h3>\n<p class='round-description'>" + cs.getDescription() + "</p>\n<br>");
                    textArea.append("Round " + round + ": " + cs.getName() + "\n");
                }
            }

            //creazione roundframe dalla cs
            roundFrame.setVisible(true);
            roundFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        }
        round++;
    }

    /**
     * Starta il game
     * Salva anche l intro
     */
    private void play() {
        intro = Introductions.get(random.nextInt(Introductions.size()));
        saveF.saveLine("<h1 id='intro-title'>" + "INTRODUZIONE: "  + intro.getTitle() + "</h1>\n<p id='intro-description'>" + intro.getDescription() + "</p>\n<br><br>");
        textArea.append("INTRODUZIONE: " + intro.getTitle() + "\n");
        textArea.append(intro.getDescription() + "\n\n");
        round++;
    }

    /**
     * Pesca la prima cs dopo LA SCELTA DELL INTRODUZIONE
     * @param i introduzione
     * @return la cs
     */
    public ChallengeScene Pick(Introduction i) {
        ArrayList<ChallengeScene> tmp = new ArrayList<>();
        for (ChallengeScene c : challengeScenes) {
            /*if ((c.getEndCondition().getCondition() & i.getEndCondition().getCondition()) == i.getEndCondition().getCondition()) {
                tmp.add(c);
            }*/
            if ((c.getEndCondition().getCondition() & i.getEndCondition().getCondition()) != 0 ) {
                tmp.add(c);
            }
        }
        if (tmp.isEmpty()) {
            tmp.add(challengeScenes.get(random.nextInt(challengeScenes.size())));
        }

        return (tmp.get(random.nextInt(tmp.size())));
    }

    /**
     * Pesca le carte del gioco in modo che siano collegate tramite le end condition
     * @param i cs
     * @return la cs
     */
    public ChallengeScene Pick(ChallengeScene i) {
        ArrayList<ChallengeScene> tmp = new ArrayList<>();
        for (ChallengeScene c : challengeScenes) {
            /*if ((c.getEndCondition().getCondition() & i.getEndCondition().getCondition()) == i.getEndCondition().getCondition()) {
                tmp.add(c);
            }*/
            if ((c.getEndCondition().getCondition() & i.getEndCondition().getCondition()) != 0 ) {
                tmp.add(c);
            }
        }
        if (tmp.isEmpty()) {
            tmp.add(challengeScenes.get(random.nextInt(challengeScenes.size())));
        }

        return (tmp.get(random.nextInt(tmp.size())));
    }

    /**
     * Pesca l'epilogo dopo L ULTIMA CS
     * @param i ultima cs
     * @return l'epilogo
     */
    public Epilogue PickEpilogue(ChallengeScene i) {
        ArrayList<Epilogue> tmp = new ArrayList<>();
        for (Epilogue c : RF.getEpilogues()) {
            if ((c.getCondition().getCondition() & i.getEndCondition().getCondition()) != 0) {
                tmp.add(c);
            }
        }
        if (tmp.isEmpty()) {
            tmp.add(RF.getEpilogues().get(random.nextInt(RF.getEpilogues().size())));
        }

        return (tmp.get(random.nextInt(tmp.size())));
    }
}
