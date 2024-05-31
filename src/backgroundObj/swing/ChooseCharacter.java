package backgroundObj.swing;

// Classi del progetto
import nobinobi.Character;

// Java swing lib
import javax.swing.*;

// Java user interface
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// Input Output
import java.io.File;
import java.io.IOException;

// Img
import javax.imageio.ImageIO;

import java.util.ArrayList;

/**
 * Classe che gestisce la scelta del character
 * si compone di due pannelli
 * A sinistra pannello con tutti i character
 * A destra informazioni character corrente
 */
public class ChooseCharacter extends JFrame implements WindowListener{
    /**
     * Fields per le info del character
     */
    private JTextField nameField;
    private JLabel imageLabel;

    private JTextArea abilityArea;
    private JTextArea descriptionArea;

    private JTextField techniqueField;
    private JTextField strengthField;

    private final int nRound;

    /**
     * Variabili per la Jlist
     */
    private final ArrayList<Character> characters;
    private JList<Character> characterList;
    //Character selezionato
    private Character player;

    /**
     * Costruttore della classe
     * @param round Numero di round della partita
     */
    public ChooseCharacter(int round) {
        setTitle("Choose Character");
        nRound = round;
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        ReaderFile RF = new ReaderFile();
        characters = RF.getCharacters();

        initializeComponents();
        layoutComponents();

        if (!characters.isEmpty()) {
            characterList.setSelectedIndex(0);
        }
    }

    /**
     *Inizializzazione dei campi e dei panel
     */
    private void initializeComponents() {
        // Modello e lista dei personaggi
        DefaultListModel<Character> characterListModel = new DefaultListModel<>();
        for (Character c : characters) {
            characterListModel.addElement(c);
        }
        characterList = new JList<>(characterListModel);
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        characterList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Character selectedCharacter = characterList.getSelectedValue();
                if (selectedCharacter != null) {
                    updateCharacterDetails(selectedCharacter);
                }
            }
        });

        // Campo di testo per il nome
        nameField = new JTextField(20);
        nameField.setEditable(false);
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Etichetta per l'immagine
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Area di testo per le abilità
        abilityArea = new JTextArea(6, 20);
        abilityArea.setLineWrap(true);
        abilityArea.setWrapStyleWord(true);
        abilityArea.setEditable(false);
        abilityArea.setBackground(Color.WHITE);
        abilityArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Area di testo per la descrizione
        descriptionArea = new JTextArea(10, 20); // Imposta l'altezza in modo che corrisponda all'altezza dell'immagine
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        descriptionArea.setPreferredSize(new Dimension(200, 200)); // Stessa altezza dell'immagine

        // Campi di testo per tecnica e forza
        techniqueField = new JTextField(10);
        techniqueField.setEditable(false);
        techniqueField.setBackground(Color.WHITE);
        techniqueField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        strengthField = new JTextField(10);
        strengthField.setEditable(false);
        strengthField.setBackground(Color.WHITE);
        strengthField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Layout dei panel con i componenti
     */
    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Aggiungi la lista dei personaggi
        addComponent(new JScrollPane(characterList), gbc, 0, 0, 1, 3, GridBagConstraints.BOTH, 0.5, 1.0);

        // Pannello destro
        JPanel rightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(5, 5, 5, 5);

        // Aggiungi campo di testo per il nome
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        nameField.setHorizontalAlignment(JTextField.CENTER);
        rightPanel.add(nameField, rightGbc);

        // Aggiungi area di testo per la descrizione e l'immagine
        rightGbc.gridy = 1;
        rightGbc.gridwidth = 1;
        rightGbc.fill = GridBagConstraints.BOTH;
        rightGbc.weightx = 0.5;
        rightGbc.weighty = 0.5;
        rightPanel.add(new JScrollPane(descriptionArea), rightGbc);

        rightGbc.gridx = 1;
        rightGbc.fill = GridBagConstraints.NONE;
        rightGbc.weightx = 0;
        rightGbc.weighty = 0;
        rightPanel.add(imageLabel, rightGbc);

        // Aggiungi campi per tecnica e forza
        rightGbc.gridx = 0;
        rightGbc.gridy = 2;
        rightGbc.gridwidth = 2;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        statsPanel.add(new JLabel("Strength:"));
        statsPanel.add(strengthField);
        statsPanel.add(new JLabel("Technique:"));
        statsPanel.add(techniqueField);
        rightPanel.add(statsPanel, rightGbc);

        // Aggiungi textarea per le abilità
        rightGbc.gridy = 3;
        rightGbc.fill = GridBagConstraints.BOTH;
        rightGbc.weighty = 0.5;
        rightPanel.add(new JScrollPane(abilityArea), rightGbc);

        // Aggiungi pulsante "Scegli"
        rightGbc.gridy = 4;
        rightGbc.fill = GridBagConstraints.NONE;
        JButton chooseButton = new JButton("Scegli");
        chooseButton.addActionListener(e -> {

            player = characterList.getSelectedValue();
            JOptionPane.showMessageDialog(this, "Hai scelto " + player.getName());

            // Creazione menu swing
            backgroundObj.swing.GameMenuFrame gmf = new backgroundObj.swing.GameMenuFrame(nRound , this.player);//SELEZIONE DEL NUMERO DI ROUND
            gmf.setVisible(true);
            this.dispose();
        });
        rightPanel.add(chooseButton, rightGbc);

        // Aggiungi il pannello destro al frame principale
        addComponent(rightPanel, gbc, 1, 0, 1, 3, GridBagConstraints.BOTH, 0.5, 1.0);
    }

    /**
     * Metodo per aggiungere componenti al layout con GridBagConstraints
     * @param component
     * @param gbc
     * @param gridx
     * @param gridy
     * @param gridwidth
     * @param gridheight
     * @param fill
     * @param weightx
     * @param weighty
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
     * Aggiorna i dettagli del personaggio selezionato
     * @param c il carattere da visualizzare
     * preleva OGNI parametro del c
     */
    private void updateCharacterDetails(Character c) {
        setNameField(c.getName());
        setImageFromPath(c.getImage());
        setDescriptionArea(c.getDescription());
        setTechniqueField(Integer.toString(c.getTechnique()));
        setStrengthField(Integer.toString(c.getStrength()));
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < c.getAbilityCount(); i++) {
            text.append(c.getAbility(i)).append("\n");
        }
        setAbilityArea(text.toString());
    }

    /**
     * Set name nell etichetta
     * @param name nome da settare
     */
    public void setNameField(String name) {
        nameField.setText(name);
    }

    /**
     * Setta immagine del c a partire dal path
     * @param imagePath path relativo dell immagine
     */
    public void setImageFromPath(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                imageFile = new File("src/img/cimg/error.jpg");
                if (player != null) {
                    player.setImage("src/img/cimg/error.jpg");
                }
            }
            ImageIcon originalIcon = new ImageIcon(ImageIO.read(imageFile));
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            imageLabel.setIcon(scaledIcon);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore nel caricamento dell'immagine: " + e.getMessage());
        }
    }

    private void setAbilityArea(String text) {
        abilityArea.setText(text);
    }

    public void setDescriptionArea(String description) {
        descriptionArea.setText(description);
    }

    public void setTechniqueField(String technique) {
        techniqueField.setText(technique);
    }

    public void setStrengthField(String strength) {
        strengthField.setText(strength);
    }

    public Character getPlayer() {
        return player;
    }

    @Override
    public void windowOpened(WindowEvent e) { }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(-1);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(-1);
    }

    @Override
    public void windowIconified(WindowEvent e) { }

    @Override
    public void windowDeiconified(WindowEvent e) { }

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}
