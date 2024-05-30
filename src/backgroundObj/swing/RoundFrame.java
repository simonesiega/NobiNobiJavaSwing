package backgroundObj.swing;

import backgroundObj.Dice;
import nobinobi.Card;
import nobinobi.ChallengeScene;
import nobinobi.Character;
import nobinobi.Condition;
import nobinobi.editable.AbilityEditable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class RoundFrame extends JFrame implements ActionListener, WindowListener {

    private final Dice dice = new Dice();
    private JTextArea textAreaLeft;

    private JButton rollDiceButton;
    private JButton technicChallengeButton;
    private JButton strengthChallengeButton;
    private JButton proceedToCheckBonusButton;
    private JButton proceedToNextPartButton;

    private JTextField nameField;
    private JLabel imageLabel;
    private JLabel[] abilityFields;
    private JTextArea textAreaRight;

    private int sceltaChallenge;

    private int limit;
    private int dadi = 0;
    private int t = 0;
    private int nRound;

    private boolean isTechnique = false;

    private final Character character;
    private final ChallengeScene prova;

    private ReaderFile rf = new ReaderFile();

    private JTextField strengthField;
    private JTextField techniqueField;

    public RoundFrame(Character c, ChallengeScene p, int nRound) {
        nRound = nRound;
        setTitle("Round Frame");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        character = c;
        prova = p;

        initializeComponents();
        layoutComponents();

        setImageFromPath(c.getImage());
        setNameField(c.getName());
        setCard(c);

        setStory();
    }

    private void initializeComponents() {
        textAreaLeft = createTextArea();

        rollDiceButton = createButton("Roll Dice");
        rollDiceButton.addActionListener(e -> {
            for (int i = 0; i < 2; i++) {
                dadi += dice.roll(6);
            }
            textAreaLeft.append("\nLancio del dado: " + dadi + '\n');
            t = dadi;

            if (isTechnique) t += character.getTechnique();
            else t += character.getStrength();

            textAreaLeft.append("Lancio del dado sommato al potere del character: " + t + '\n');

            rollDiceButton.setEnabled(false);
            proceedToCheckBonusButton.setEnabled(true);
        });

        technicChallengeButton = createButton("Technic Challenge");
        technicChallengeButton.setEnabled(false);
        technicChallengeButton.addActionListener(e -> {
            textAreaLeft.append("Hai scelto la prova tecnica" + '\n');
            isTechnique = true;
            limit = prova.getTechnique();
            textAreaLeft.append("Devi battere il punteggio " + limit + " \n");
            sceltaChallenge = 1;
            technicChallengeButton.setEnabled(false);
            strengthChallengeButton.setEnabled(false);
            rollDiceButton.setEnabled(true);
            textAreaLeft.append("Lancia i Dadi per superare la prova " + "\n");
        });

        strengthChallengeButton = createButton("Strength Challenge");
        strengthChallengeButton.setEnabled(false);
        strengthChallengeButton.addActionListener(e -> {
            isTechnique = false;
            textAreaLeft.append("Hai scelto la prova forza" + '\n');
            limit = prova.getStrength();
            textAreaLeft.append("Devi battere il punteggio " + limit + " \n");
            sceltaChallenge = 2;
            technicChallengeButton.setEnabled(false);
            strengthChallengeButton.setEnabled(false);
            rollDiceButton.setEnabled(true);
            textAreaLeft.append("Lancia i Dadi per superare la prova " + "\n");
        });

        proceedToCheckBonusButton = createButton("Check Bonus");
        proceedToCheckBonusButton.setEnabled(false);
        proceedToCheckBonusButton.addActionListener(e -> {
            textAreaLeft.append("\n");
            if (prova.checkBonus(character, 0)) {
                if (!isTechnique) {
                    int b = prova.bonusStrength(character, 0);
                    textAreaLeft.append("Hai fatto un tiro di " + dadi + " + bonus " + b + " con totale " + (t += b) + "\n");
                } else {
                    int b = prova.bonusStrength(character, 0);
                    textAreaLeft.append("Hai fatto un tiro di " + dadi + " + bonus " + b + " con totale " + (t += b) + "\n");
                }
            } else {
                textAreaLeft.append("Hai fatto un tiro di " + dadi + " senza bonus" + "\n");
            }
            proceedToNextPartButton.setEnabled(true);
            proceedToCheckBonusButton.setEnabled(false);
        });

        proceedToNextPartButton = createButton("Next Part");
        proceedToNextPartButton.setEnabled(false);
        proceedToNextPartButton.addActionListener(e -> {
            proceedToNextPart();
            proceedToNextPartButton.setEnabled(false);

            try{
                PrintWriter writer = new PrintWriter(new FileOutputStream("src/saves/gameplay/round/round" + nRound + "csv"));
                writer.write(textAreaLeft.getText());
                writer.close();
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }
        });

        nameField = new JTextField(20);

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        abilityFields = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            abilityFields[i] = new JLabel(character.getAbility(i).toString());
        }

        strengthField = new JTextField(20);
        strengthField.setText(String.valueOf(character.getStrength()));
        strengthField.setEditable(false);

        techniqueField = new JTextField(20);
        techniqueField.setText(String.valueOf(character.getTechnique()));
        techniqueField.setEditable(false);

        textAreaRight = createTextArea();
    }

    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setText("");
        textArea.setEditable(false);
        return textArea;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 50)); // Imposta l'altezza minima del pulsante
        return button;
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        addComponent(new JScrollPane(textAreaLeft), gbc, 0, 0, 2, 1, GridBagConstraints.BOTH, 0.5, 1.0);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        buttonPanel.add(rollDiceButton);
        buttonPanel.add(technicChallengeButton);
        buttonPanel.add(strengthChallengeButton);
        buttonPanel.add(proceedToCheckBonusButton);
        buttonPanel.add(proceedToNextPartButton);

        addComponent(buttonPanel, gbc, 0, 1, 2, 1, GridBagConstraints.HORIZONTAL, 0.0, 0.0);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(5, 5, 5, 5);

        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(nameField, rightGbc);

        rightGbc.gridy = 1;
        rightGbc.fill = GridBagConstraints.NONE;
        rightPanel.add(imageLabel, rightGbc);

        // Aggiunta di un pannello per forza e tecnica affiancate
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        statsPanel.add(new JLabel("Forza:"));
        statsPanel.add(strengthField);
        statsPanel.add(new JLabel("Tecnica:"));
        statsPanel.add(techniqueField);

        rightGbc.gridy = 2;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(statsPanel, rightGbc);

        // Aggiunta etichetta Abilità sopra le abilità
        rightGbc.gridy = 3;
        rightPanel.add(new JLabel("Abilità:"), rightGbc);

        rightGbc.gridy = 4;
        JPanel abilitiesPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        for (int i = 0; i < 6; i++) {
            abilitiesPanel.add(new JScrollPane(abilityFields[i]));
        }
        rightPanel.add(abilitiesPanel, rightGbc);

        rightGbc.gridy = 5;
        rightGbc.weighty = 1.0;
        rightGbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPaneRight = new JScrollPane(textAreaRight);
        rightPanel.add(scrollPaneRight, rightGbc);

        addComponent(rightPanel, gbc, 2, 0, 1, 3, GridBagConstraints.BOTH, 0.5, 1.0);
    }

    private void addComponent(Component component, GridBagConstraints gbc, int gridx, int gridy,
                              int gridwidth, int gridheight, int fill, double weightx, double weighty) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        add(component, gbc);
    }

    public void setNameField(String name) {
        nameField.setText(name);
    }

    public void setImageFromPath(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.out.println("Image file does not exist at: " + imagePath);
                JOptionPane.showMessageDialog(this, "Immagine non trovata: " + imagePath);
                imageFile = new File("src/img/cimg/player1.jpg");
                return;
            }
            ImageIcon icon = new ImageIcon(ImageIO.read(imageFile));
            imageLabel.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore nel caricamento dell'immagine: " + e.getMessage());
        }
    }

    private void setAbilityField(int index, String text) {
        if (index >= 0 && index < abilityFields.length) {
            abilityFields[index].setText(text);
        }
    }

    private void setCard(Character c) {
        for (int i = 0; i < c.getCardCount(); i++) {
            textAreaRight.append(c.getCard(i).toString() + '\n');
        }
    }

    private void setTextAreaRight(String text) {
        textAreaRight.setText(text);
    }

    private void setStory() {
        textAreaLeft.append("TITOLO: " + prova.getName());
        textAreaLeft.append("\n\n");
        textAreaLeft.append("DESCRIZIONE: " + prova.getDescription());
        textAreaLeft.append("\n\n");

        if (prova.getTechnique() == 0) {
            limit = prova.getStrength();
            textAreaLeft.append("Devi superare forza: " + limit + "\n\n");
            textAreaLeft.append("Lancia i Dadi per superare la prova " + "\n");
        } else if (prova.getStrength() == 0) {
            isTechnique = true;
            limit = prova.getTechnique();
            textAreaLeft.append("Devi superare tecnica: " + limit + "\n\n");
            textAreaLeft.append("Lancia i Dadi per superare la prova " + "\n");
        } else {
            rollDiceButton.setEnabled(false); // Assicura che il pulsante "Roll Dice" sia disabilitato finché non viene effettuata una scelta
            textAreaLeft.append("Scegli se battere tecnica o forza\n");
            textAreaLeft.append("Tecnica: " + prova.getTechnique() + "\n");
            textAreaLeft.append("Forza: " + prova.getStrength() + "\n");
            technicChallengeButton.setEnabled(true);
            strengthChallengeButton.setEnabled(true);
        }

        textAreaLeft.append("\n");
    }

    private void proceedToNextPart() {
        if (dadi > limit) {
            textAreaLeft.append("\nHai vinto la prova:\n" + prova.getWinDescription() + "\n");
            character.addCard(rf.getLightCards().get(dice.roll(rf.getLightCards().size() - 1)));
            textAreaLeft.append("Hai guadagnato la seguente carta: " + character.getCard(character.getCardCount() - 1) + '\n');
            textAreaLeft.append(character.getCard(character.getCardCount() - 1).getDescription());
        } else {
            textAreaLeft.append("\nHai perso la prova: " + prova.getLostDescription() + "\n");
            character.addCard(rf.getDarkCards().get(dice.roll(rf.getDarkCards().size() - 1)));
            textAreaLeft.append("Hai guadagnato la seguente carta: " + character.getCard(character.getCardCount() - 1) + "\n");
            textAreaLeft.append(character.getCard(character.getCardCount() - 1).getDescription());
        }
    }

    public static void main(String[] args) {
        Character a = new Character("titolo#M#src/img/cimg/player1.png#descrizione#1#2#dada@dada@2097151@434@4324@@dadffaf@fafaf@1560063@432342@4323432@@dada@fafa4@13840@3324@42342@@dada@ffaga@2097151@4324@424@@da@dada@2097151@434@43@@dad@adad@2097151@343@432");
        a.addCard(new Card("prova", new Condition(12), 0, 2));
        a.addCard(new Card("dad", new Condition(12), 1, 2));
        a.addCard(new Card("5454", new Condition(12), 1, 2));
        ChallengeScene b = new ChallengeScene("dad#adad#1#1#dada#dada#2097151#2097151");
        RoundFrame r = new RoundFrame(a, b, 1);
        r.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
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
