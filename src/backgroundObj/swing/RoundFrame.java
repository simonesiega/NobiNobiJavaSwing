package backgroundObj.swing;

// Classi del progetto
import backgroundObj.Dice;
import nobinobi.ChallengeScene;
import nobinobi.Character;

// Java swing lib
import javax.swing.*;

// Java user interface
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Input Output
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

// Img
import javax.imageio.ImageIO;

/**
 * Classe che gestisce ogni round del gioco
 * Si compone di due pannelli
 * Pannello sinistro area di testo controllata con dei bottoni
 * Pannello destro informazioni sul personaggio
 */
public class RoundFrame extends JFrame implements ActionListener {

    // Dado
    private final Dice dice = new Dice();
    private int dadi = 0;

    // Area di testo sinistra
    private JTextArea textAreaLeft;

    /**
     * Bottoni del pannello di sinistra
     */
    private JButton rollDiceButton;
    private JButton technicChallengeButton;
    private JButton strengthChallengeButton;
    private JButton proceedToCheckBonusButton;
    private JButton proceedToNextPartButton;

    /**
     * Etichette personaggio di destra
     */
    private JTextField nameField;
    private JLabel imageLabel;
    private JLabel[] abilityFields;
    private JTextArea textAreaRight;
    private JTextField strengthField;
    private JTextField techniqueField;

    // Soglie prova
    private int limit;
    private int t = 0;

    // Numero round corrente
    private final int nRound;

    /**
     * Che tipo di prova
     * true - tecnica
     * false - forza
     */
    private boolean isTechnique = false;

    /**
     * Obj che agiscono nel round
     * c
     * prova
     */
    private final Character character;
    private final ChallengeScene prova;

    // Lettura dai file save
    private final ReaderFile rf = new ReaderFile();

    /**
     * Costruttore
     *
     * @param c      character
     * @param p      prova
     * @param nRound numero round corrente
     */
    public RoundFrame(Character c, ChallengeScene p, int nRound) {
        this.nRound = nRound;

        //Init creazione schermata
        setTitle("Round Frame");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        character = c;
        prova = p;

        // Init componenti nella schermata
        initializeComponents();
        layoutComponents();

        // Set etichette destra
        setImageFromPath(c.getImage());
        setNameField();
        setCard();

        // Gestisce la storia
        setStory();
    }

    /**
     * Init componenti della schermata
     * Lavora con
     * c
     * p
     */
    private void initializeComponents() {
        // Creazione pannelli
        textAreaLeft = createTextArea();
        textAreaRight = createTextArea();

        // Bottone lancio dado
        rollDiceButton = createButton("Roll Dice");
        rollDiceButton.addActionListener(e -> {
            for (int i = 0; i < 2; i++) {
                dadi += dice.roll(6);
            }

            textAreaLeft.append("\nLancio del dado: " + dadi + '\n');
            t = dadi;

            // Somma il punteggio del dado
            if (isTechnique) t += character.getTechnique();
            else t += character.getStrength();

            textAreaLeft.append("Lancio del dado sommato al potere del character: " + t + '\n');

            // Disabilita il tasto e procede
            rollDiceButton.setEnabled(false);
            proceedToCheckBonusButton.setEnabled(true);
        });

        // Nei successivi due bottoni entra SOLO se forza e tecnica != 0 in prova
        // Bottone prova tecnica
        technicChallengeButton = createButton("Technic Challenge");
        technicChallengeButton.setEnabled(false);
        technicChallengeButton.addActionListener(e -> {
            isTechnique = true;
            limit = prova.getTechnique();

            textAreaLeft.append("Hai scelto la prova tecnica" + '\n');
            textAreaLeft.append("Devi battere il punteggio " + limit + " \n");
            textAreaLeft.append("Lancia i Dadi per superare la prova " + "\n");

            // Disabilita i due bottoni
            technicChallengeButton.setEnabled(false);
            strengthChallengeButton.setEnabled(false);
            rollDiceButton.setEnabled(true);
        });

        strengthChallengeButton = createButton("Strength Challenge");
        strengthChallengeButton.setEnabled(false);
        strengthChallengeButton.addActionListener(e -> {
            isTechnique = false;
            limit = prova.getStrength();

            textAreaLeft.append("Hai scelto la prova forza" + '\n');
            textAreaLeft.append("Devi battere il punteggio " + limit + " \n");
            textAreaLeft.append("Lancia i Dadi per superare la prova " + "\n");

            // Disabilita i due bottoni
            technicChallengeButton.setEnabled(false);
            strengthChallengeButton.setEnabled(false);
            rollDiceButton.setEnabled(true);

        });

        proceedToCheckBonusButton = createButton("Check Bonus");
        proceedToCheckBonusButton.setEnabled(false);
        proceedToCheckBonusButton.addActionListener(e -> {
            textAreaLeft.append("\n");
            if (prova.checkBonus(character, 0)) {
                int b;
                if (isTechnique) {
                    b = prova.bonusTechnic(character, 0);
                } else {
                    b = prova.bonusStrength(character, 0);
                }
                textAreaLeft.append("Hai fatto un tiro di " + dadi + " + bonus " + b + " con totale " + (t += b) + "\n");
            } else {
                textAreaLeft.append("Hai fatto un tiro di " + dadi + " senza bonus" + "\n");
            }
            proceedToNextPartButton.setEnabled(true);
            proceedToCheckBonusButton.setEnabled(false);
        });

        // Bottone prossimo round
        proceedToNextPartButton = createButton("Next Part");
        proceedToNextPartButton.setEnabled(false);
        proceedToNextPartButton.addActionListener(e -> {
            proceedToNextPartButton.setEnabled(false);
            proceedToNextPart();
            // Salvataggio round
            // writeRound nel file round + nRound
            try {
                PrintWriter writerRound = new PrintWriter(new FileOutputStream("src/saves/gameplay/round/round" + nRound + ".csv"));
                writerRound.write(textAreaLeft.getText());
                writerRound.close();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        });

        nameField = new JTextField(20);

        // Label img
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Ability c
        abilityFields = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            abilityFields[i] = new JLabel(character.getAbility(i).toString());
        }

        // Label forza e tecnica
        strengthField = new JTextField(20);
        strengthField.setText(String.valueOf(character.getStrength()));
        strengthField.setEditable(false);

        techniqueField = new JTextField(20);
        techniqueField.setText(String.valueOf(character.getTechnique()));
        techniqueField.setEditable(false);
    }

    /**
     * Crea un pannello
     * NB il pannello non é modificabile
     *
     * @return il pannello costruito
     * No dinamico
     */
    private JTextArea createTextArea() {
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setText("");
        textArea.setEditable(false);
        return textArea;
    }

    /**
     * Crea un bottone a partire dal nome
     * Usato per i bottoni del pannello sinistro
     *
     * @param text nome del bottone
     * @return il bottone costruito
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 50)); // Imposta l'altezza minima del pulsante
        return button;
    }

    /**
     * Unisce i pannelli
     */
    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        addComponent(new JScrollPane(textAreaLeft), gbc, 0, 0, 2, 1, GridBagConstraints.BOTH, 0.5, 1.0);

        // Pannello bottoni
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

        // Pannello per forza e tecnica affiancate
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        statsPanel.add(new JLabel("Forza:"));
        statsPanel.add(strengthField);
        statsPanel.add(new JLabel("Tecnica:"));
        statsPanel.add(techniqueField);

        rightGbc.gridy = 2;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        rightPanel.add(statsPanel, rightGbc);

        // Etichetta Abilità sopra le abilità
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
        rightPanel.add(textAreaRight, rightGbc);

        addComponent(rightPanel, gbc, 2, 0, 1, 3, GridBagConstraints.BOTH, 0.5, 1.0);
    }

    /**
     * Metodo add component in un layout
     */
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

    /**
     * Imposta nome del c
     */
    public void setNameField() {
        nameField.setText(character.getName());
    }

    /**
     * Imposta immagine a partire dal path
     * Immagini localizzate in src/img
     *
     * @param imagePath percorso img
     *                  throws IOException
     *                  Piccolo debug
     */

    public void setImageFromPath(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.out.println("Image file does not exist at: " + imagePath);
                JOptionPane.showMessageDialog(this, "Immagine non trovata: " + imagePath);
                return;
            }

            // Legge l'immagine e la ridimensiona a 300 x 300 pixel
            BufferedImage originalImage = ImageIO.read(imageFile);
            Image resizedImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(resizedImage);

            imageLabel.setIcon(icon);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore nel caricamento dell'immagine: " + e.getMessage());
        }
    }


    /**
     * Imposta le carte del c
     */
    private void setCard() {
        for (int i = 0; i < character.getCardCount(); i++) {
            textAreaRight.append(character.getCard(i).toString() + '\n');
        }
    }

    /**
     * Gestisce il round vero e proprio
     */
    private void setStory() {
        // Stampe iniziali
        textAreaLeft.append("Round numero: " + nRound + " \n\n");
        textAreaLeft.append("TITOLO: " + prova.getName());
        textAreaLeft.append("\n\n");
        textAreaLeft.append("DESCRIZIONE: " + prova.getDescription());
        textAreaLeft.append("\n\n");

        // Controllo per vedere il tipo di prova scelta
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

            // Abilitá i due bottoni solo in questo caso
            technicChallengeButton.setEnabled(true);
            strengthChallengeButton.setEnabled(true);
        }

        textAreaLeft.append("\n");
    }

    /**
     * Controllo finale del round
     * Pesca una carta randomica e la aggiunge al c
     */
    private void proceedToNextPart() {
        // Caso prova vinta
        if (dadi > limit) {
            textAreaLeft.append("\nHai vinto la prova:\n" + prova.getWinDescription() + "\n");
            character.addCard(rf.getLightCards().get(dice.roll(rf.getLightCards().size() - 1)));
            textAreaLeft.append("Hai guadagnato la seguente carta: " + character.getCard(character.getCardCount() - 1) + '\n');
            textAreaLeft.append(character.getCard(character.getCardCount() - 1).getDescription());
        }
        // Caso prova persa
        else {
            textAreaLeft.append("\nHai perso la prova: " + prova.getLostDescription() + "\n");
            character.addCard(rf.getDarkCards().get(dice.roll(rf.getDarkCards().size() - 1)));
            textAreaLeft.append("Hai guadagnato la seguente carta: " + character.getCard(character.getCardCount() - 1) + "\n");
            textAreaLeft.append(character.getCard(character.getCardCount() - 1).getDescription());
        }
    }

    // NON IMPLEMENTATO
    @Override
    public void actionPerformed(ActionEvent e) {

    }

}