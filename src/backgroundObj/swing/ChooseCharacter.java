package backgroundObj.swing;

import nobinobi.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class ChooseCharacter extends JFrame implements WindowListener {

    private JTextField nameField;
    private JLabel imageLabel;
    private JTextArea abilityArea; // Cambiato a JTextArea per le abilità
    private JTextArea descriptionArea;
    private JTextField techniqueField;
    private JTextField strengthField;
    private ArrayList<Character> characters;
    private JList<Character> characterList;
    private DefaultListModel<Character> characterListModel;
    private Character player; // Variabile player pubblica

    public ChooseCharacter() {
        setTitle("Choose Character");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        ReaderFile RF = new ReaderFile();
        characters = RF.getCharacters();

        initializeComponents(characters);
        layoutComponents();

        // Seleziona il primo personaggio di default se la lista non è vuota
        if (!characters.isEmpty()) {
            characterList.setSelectedIndex(0);
        }
    }

    private void initializeComponents(ArrayList<Character> characters) {
        // Modello e lista dei personaggi
        characterListModel = new DefaultListModel<>();
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

        // Area per l'immagine
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(300, 300));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Area di testo per le abilità
        abilityArea = new JTextArea(6, 20); // Cambiato a JTextArea
        abilityArea.setLineWrap(true);
        abilityArea.setWrapStyleWord(true);
        abilityArea.setEditable(false);
        abilityArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Area di testo per la descrizione
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Campi di testo per tecnica e forza
        techniqueField = new JTextField(10);
        strengthField = new JTextField(10);
    }

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
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        nameField.setHorizontalAlignment(JTextField.CENTER);
        rightPanel.add(nameField, rightGbc);

        // Aggiungi area per l'immagine
        rightGbc.gridy = 1;
        rightGbc.fill = GridBagConstraints.NONE;
        rightPanel.add(imageLabel, rightGbc);

        // Aggiungi area di testo per la descrizione
        rightGbc.gridy = 2;
        rightGbc.fill = GridBagConstraints.BOTH;
        rightGbc.weighty = 0.5;
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        rightPanel.add(descriptionScrollPane, rightGbc);

        // Aggiungi campi per tecnica e forza
        rightGbc.gridy = 3;
        rightGbc.fill = GridBagConstraints.HORIZONTAL;
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        statsPanel.add(new JLabel("Strength:"));
        statsPanel.add(strengthField);
        statsPanel.add(new JLabel("Technique:"));
        statsPanel.add(techniqueField);

        rightPanel.add(statsPanel, rightGbc);

        // Aggiungi textarea per le abilità
        rightGbc.gridy = 4;
        rightGbc.fill = GridBagConstraints.BOTH;
        JScrollPane abilityScrollPane = new JScrollPane(abilityArea);
        rightPanel.add(abilityScrollPane, rightGbc);

        // Aggiungi pulsante "Scegli"
        rightGbc.gridy = 5;
        rightGbc.fill = GridBagConstraints.NONE;
        JButton chooseButton = new JButton("Scegli");
        chooseButton.addActionListener(e -> {
            player = characterList.getSelectedValue(); // Imposta il personaggio selezionato
            JOptionPane.showMessageDialog(this, "Hai scelto " + player.getName());

            backgroundObj.swing.GameMenuFrame gmf = new backgroundObj.swing.GameMenuFrame(this.player);
            gmf.setVisible(true);
            this.dispose();
        });
        rightPanel.add(chooseButton, rightGbc);

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

    private void updateCharacterDetails(Character c) {
        setNameField(c.getName());
        setImageFromPath(c.getImage());
        setDescriptionArea(c.getDescription());
        setTechniqueField(Integer.toString(c.getTechnique()));
        setStrengthField(Integer.toString(c.getStrength()));
        String text = "";
        for (int i = 0; i < c.getAbilityCount(); i++) {
            text += c.getAbility(i) + "\n";
        }
        setAbilityArea(text);
    }

    // Metodi per impostare i valori
    public void setNameField(String name) {
        nameField.setText(name);
    }

    public void setImageFromPath(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                imageFile = new File("src/img/cimg/player1.jpg");
                if(player != null){
                    player.setImage("src/img/cimg/player1.jpg");
                }

            }
            ImageIcon icon = new ImageIcon(ImageIO.read(imageFile));

            imageLabel.setIcon(icon);

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
    public Character getPlayer(){
        return player;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(-1);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(-1);
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

    public static void main(String[] args) {
        ChooseCharacter c = new ChooseCharacter();
        c.setVisible(true);
    }
}
