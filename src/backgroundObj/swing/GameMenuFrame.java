package backgroundObj.swing;

import nobinobi.ChallengeScene;
import nobinobi.Character;
import nobinobi.Epilogue;
import nobinobi.Introduction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;

public class GameMenuFrame extends JFrame implements ActionListener {
    private final Random random = new Random();
    private int round = 0;
    private ReaderFile RF = new ReaderFile(); ;
    private Character Player;
    private ArrayList<Introduction> Introductions = RF.getIntroductions();
    private ArrayList<ChallengeScene> challengeScenes = RF.getChallengeScenes();
    private JButton nextButton;
    private JButton rollDiceButton;
    private JButton technicianButton;
    private JButton strengthButton;
    private JTextArea textArea;


    public GameMenuFrame(Character player) {

        this.Player = player;

        setTitle("Swing Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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

        if(round == 3){
            Epilogue ep = RF.getEpilogues().get(random.nextInt(RF.getEpilogues().size()));
            textArea.append("EPILOGO" + "\n");
            textArea.append(ep.getTitle() + "\n");
            textArea.append(ep.getDescription()+ "\n");
            //HO FATTO SI CHE EPILOGUE EREDITASSE DA CHALLENGESCENE
            RoundFrame rf = new RoundFrame(Player , ep , round);
            rf.setVisible(true);
        }else{
            ChallengeScene cs = challengeScenes.get(random.nextInt(challengeScenes.size()));
            textArea.append("Round "+ round + ": " + cs.getName() + "\n");
            RoundFrame roundFrame = new RoundFrame(Player , cs , round );
            roundFrame.setVisible(true);
            roundFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            round ++;
        }

    }
    private void play(){
        Introduction intro = Introductions.get(random.nextInt(Introductions.size()));
        textArea.append(intro.getTitle() + "\n");
        textArea.append(intro.getDescription()+ "\n");
    }


}