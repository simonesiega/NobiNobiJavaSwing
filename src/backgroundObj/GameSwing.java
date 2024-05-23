package backgroundObj;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameSwing extends JFrame implements ActionListener {
    private final Random random = new Random();
    private int round = 0;

    private JButton nextButton;
    private JButton rollDiceButton;
    private JButton technicianButton;
    private JButton strengthButton;
    private JTextArea textArea;
    private JLabel diceResultLabel;

    public GameSwing() {
        setTitle("Swing Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        nextButton = createButton("Next");
        nextButton.addActionListener(this);
        buttonPanel.add(nextButton);

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
        if (e.getSource() == nextButton) {
            handleNextButton();
        } else if (e.getSource() == rollDiceButton) {
            handleRollDiceButton();
        }
    }

    private void handleNextButton() {
        // Implementa logica del pulsante Next
        round++;
        displayText("Round " + round);
    }

    private void handleRollDiceButton() {
        int diceResult = rollDice();
        displayText("Dice Result: " + diceResult);
    }

    private int rollDice() {
        return random.nextInt(6) + 1;
    }
}
