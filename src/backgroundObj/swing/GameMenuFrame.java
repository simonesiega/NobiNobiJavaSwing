package backgroundObj.swing;

import nobinobi.ChallengeScene;
import nobinobi.Character;
import nobinobi.Introduction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Random;

public class GameMenuFrame extends JFrame implements ActionListener , WindowListener {
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
    private JLabel diceResultLabel;

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

        rollDiceButton = createButton("Roll Dice");
        rollDiceButton.addActionListener(this);
        buttonPanel.add(rollDiceButton);

        add(buttonPanel, BorderLayout.SOUTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        diceResultLabel = new JLabel(" ");
        diceResultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(diceResultLabel, BorderLayout.NORTH);

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
        if (e.getSource() == rollDiceButton) {
            handleRollDiceButton();
        }
        else if(e.getSource() == nextButton){
            handleNextButton();
        }
    }

    private void handleNextButton() {
        // Implementa logica del pulsante Next
        ChallengeScene cs = challengeScenes.get(random.nextInt(challengeScenes.size()));
        RoundFrame roundFrame = new RoundFrame(Player , cs);
        roundFrame.setVisible(true);
        roundFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        round ++;
        if(round == 10){
            nextButton.removeActionListener(this);
        }

    }

    private void handleRollDiceButton() {
        int diceResult = rollDice();
        displayText("Dice Result: " + diceResult);
    }

    private int rollDice() {
        return random.nextInt(6) + 1;
    }

    private void play(){

        Introduction intro = Introductions.get(random.nextInt(Introductions.size()));
        textArea.append(intro.getTitle() + "\n");
        textArea.append(intro.getDescription());



    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}