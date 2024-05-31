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

public class GameMenuFrame extends JFrame implements ActionListener {
    private final Random random = new Random();
    private int round = 0;
    private ReaderFile RF = new ReaderFile();
    private Character Player;
    private ArrayList<Introduction> Introductions = RF.getIntroductions();
    private ArrayList<ChallengeScene> challengeScenes = RF.getChallengeScenes();
    private JButton nextButton;
    private JTextArea textArea;
    private ChallengeScene cs;
    private ChallengeScene cs2;
    private Introduction intro;
    private CreateHtmlFile saveF = new CreateHtmlFile("src/saves/gameplay/finalprint/Print.html");

    public GameMenuFrame(Character player) {
        this.Player = player;

        setTitle("Swing Game");
        setSize(400, 300);
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

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        return button;
    }

    private void displayText(String text) {
        textArea.append(text + "\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        handleNextButton();
    }

    private void handleNextButton() {
        if (cs != null) {
            cs2 = cs;
        }

        if (round == 3) {
            Epilogue ep = PickEpilogue(cs2);
            textArea.append("\nEPILOGO\n");
            saveF.saveLine("<br><h2 id='epilogue-title'>" + "EPILOGO: " + ep.getName() + "</h2>\n<p id='epilogue-description'>" + ep.getDescription() + "</p>\n");
            saveF.finishFile();
            textArea.append(ep.getTitle() + "\n");
            textArea.append(ep.getDescription() + "\n");
            RoundFrame rf = new RoundFrame(Player, ep, round);
            rf.setVisible(true);
        } else {
            if (round == 1) {
                cs = Pick(intro);
            } else {
                if (round % 2 == 0) {
                    cs2 = Pick(cs);
                } else {
                    cs = Pick(cs2);
                }
            }

            saveF.saveLine("<h3 class='round-title'>Round " + round + ": " + cs.getName() + "</h3>\n<p class='round-description'>" + cs.getDescription() + "</p>\n<br>");
            textArea.append("Round " + round + ": " + cs.getName() + "\n");
            RoundFrame roundFrame = new RoundFrame(Player, cs, round);
            roundFrame.setVisible(true);
            roundFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            round++;
        }
    }

    private void play() {
        intro = Introductions.get(random.nextInt(Introductions.size()));
        saveF.saveLine("<h1 id='intro-title'>" + "INTRODUZIONE: "  + intro.getTitle() + "</h1>\n<p id='intro-description'>" + intro.getDescription() + "</p>\n<br><br>");
        textArea.append(intro.getTitle() + "\n");
        textArea.append(intro.getDescription() + "\n");
        round++;
    }

    public ChallengeScene Pick(Introduction i) {
        ArrayList<ChallengeScene> tmp = new ArrayList<>();
        for (ChallengeScene c : challengeScenes) {
            if ((c.getEndCondition().getCondition() & i.getEndCondition().getCondition()) == i.getEndCondition().getCondition()) {
                tmp.add(c);
            }
        }
        if (tmp.isEmpty()) {
            tmp.add(challengeScenes.get(random.nextInt(challengeScenes.size())));
        }

        return (tmp.get(random.nextInt(tmp.size())));
    }

    public ChallengeScene Pick(ChallengeScene i) {
        ArrayList<ChallengeScene> tmp = new ArrayList<>();
        for (ChallengeScene c : challengeScenes) {
            if ((c.getEndCondition().getCondition() & i.getEndCondition().getCondition()) == i.getEndCondition().getCondition()) {
                tmp.add(c);
            }
        }
        if (tmp.isEmpty()) {
            tmp.add(challengeScenes.get(random.nextInt(challengeScenes.size())));
        }

        return (tmp.get(random.nextInt(tmp.size())));
    }

    public Epilogue PickEpilogue(ChallengeScene i) {
        ArrayList<Epilogue> tmp = new ArrayList<>();
        for (Epilogue c : RF.getEpilogues()) {
            if ((c.getCondition().getCondition() & i.getEndCondition().getCondition()) == i.getEndCondition().getCondition()) {
                tmp.add(c);
            }
        }
        if (tmp.isEmpty()) {
            tmp.add(RF.getEpilogues().get(random.nextInt(RF.getEpilogues().size())));
        }

        return (tmp.get(random.nextInt(tmp.size())));
    }
}
