package backgroundObj.swing;

import backgroundObj.Dice;
import nobinobi.Card;
import nobinobi.Character;
import nobinobi.Condition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class RoundFrame extends JFrame implements ActionListener {

    private Dice dice = new Dice();
    private JTextArea textAreaLeft;
    private JButton rollDiceButton;
    private JTextField nameField;
    private JLabel imageLabel;
    private JLabel[] abilityFields;
    private JTextArea textAreaRight;

    public RoundFrame(Character c) {
        setTitle("Round Frame");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        initializeComponents(c);
        layoutComponents();

        setImageFromPath(c.getImage());
        setNameField(c.getName());
        setCard(c);
    }

    private void initializeComponents(Character c) {
        // Area di testo a sinistra
        textAreaLeft = createTextArea("", false);

        // Bottone Roll Dice
        rollDiceButton = new JButton("Roll Dice");
        rollDiceButton.addActionListener( e -> {
            int sum = 0;

            for (int i = 0; i < 2; i++) {
                sum += dice.roll(6);
            }
            textAreaLeft.append(Integer.toString(sum) + '\n');
        });

        // Campo di testo per il nome
        nameField = new JTextField(20);

        // Area per l'immagine
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Aree di testo non modificabili per le abilità
        abilityFields = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            abilityFields[i] = new JLabel(c.getAbility(i).toString());
        }

        // Grande area di testo a destra
        textAreaRight = createTextArea("", false);
    }

    private JTextArea createTextArea(String text, boolean editable) {
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setText(text);
        textArea.setEditable(editable);
        return textArea;
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Aggiungi area di testo a sinistra
        addComponent(new JScrollPane(textAreaLeft), gbc, 0, 0, 1, 2, GridBagConstraints.BOTH, 0.5, 1.0);

        // Aggiungi bottone Roll Dice
        addComponent(rollDiceButton, gbc, 0, 2, 1, 1, GridBagConstraints.NONE, 0.0, 0.0);

        // Pannello destro
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(5, 5, 5, 5);

        // Aggiungi campo di testo per il nome
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(nameField, rightGbc);

        // Aggiungi area per l'immagine
        rightGbc.gridy = 1;
        rightGbc.fill = GridBagConstraints.NONE;
        rightPanel.add(imageLabel, rightGbc);

        // Aggiungi aree di testo non modificabili per le abilità
        rightGbc.gridy = 2;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel abilitiesPanel = new JPanel(new GridLayout(6, 1, 5, 5));
        for (int i = 0; i < 6; i++) {
            abilitiesPanel.add(new JScrollPane(abilityFields[i]));
        }
        rightPanel.add(abilitiesPanel, rightGbc);

        // Aggiungi grande area di testo sotto le abilità
        rightGbc.gridy = 3;
        rightGbc.weighty = 1.0;
        rightGbc.fill = GridBagConstraints.BOTH;
        JScrollPane scrollPaneRight = new JScrollPane(textAreaRight);
        rightPanel.add(scrollPaneRight, rightGbc);

        // Aggiungi pannello destro al frame
        addComponent(rightPanel, gbc, 1, 0, 1, 3, GridBagConstraints.BOTH, 0.5, 1.0);
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

    // Metodi per impostare i valori
    public void setNameField(String name) {
        nameField.setText(name);
    }

    public void setImageFromPath(String imagePath) {
        try {
            // Debug: Stampa il percorso dell'immagine
            System.out.println("Trying to load image from path: " + imagePath);
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.out.println("Image file does not exist at: " + imagePath);
                JOptionPane.showMessageDialog(this, "Immagine non trovata: " + imagePath);
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

    private void setCard(Character c){
        for (int i = 0; i < c.getCardCount(); i++) {
            textAreaRight.append(c.getCard(i).toString() + '\n');
        }
    }

    private void setTextAreaRight(String text) {
        textAreaRight.setText(text);
    }

    public static void main (String[] args) {
        Character a = new Character("titolo#M#src/img/cimg/player1.jpg#descrizione#1#2#dada@dada@2097151@434@4324@@dadffaf@fafaf@1560063@432342@4323432@@dada@fafa4@13840@3324@42342@@dada@ffaga@2097151@4324@424@@da@dada@2097151@434@43@@dad@adad@2097151@343@432");
        a.addCard(new Card("prova", new Condition(12), 1, 2));
        a.addCard(new Card("dad", new Condition(12), 1, 2));
        a.addCard(new Card("5454", new Condition(12), 1, 2));
        RoundFrame r = new RoundFrame(a);
        r.setVisible(true);
    }

    @Override
    public void actionPerformed (ActionEvent e) {

    }
}
