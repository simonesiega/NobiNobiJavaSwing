package backgroundObj.swing;

import backgroundObj.swing.ReaderFile;
import nobinobi.Character;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ChooseCharacter extends JFrame {
    private JList<String> characterList;
    private DefaultListModel<String> listModel;
    private JTextField nameField, descriptionField, genderField, strengthField, techniqueField, abilitiesField;
    private JLabel imageLabel;
    private ArrayList<Character> characters;

    public ChooseCharacter(ArrayList<Character> characters) {
        super("Character Selection Frame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        this.characters = characters;

        // Pannello per la lista dei personaggi
        listModel = new DefaultListModel<>();
        for (Character character : characters) {
            listModel.addElement(character.getName());
        }
        characterList = new JList<>(listModel);
        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        characterList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = characterList.getSelectedIndex();
                showCharacterDetails(selectedIndex);
            }
        });

        JScrollPane listScrollPane = new JScrollPane(characterList);
        listScrollPane.setPreferredSize(new Dimension(200, 0));

        // Pannello per visualizzare i dettagli del personaggio
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(150, 150));

        nameField = createTextField();
        descriptionField = createTextField();
        genderField = createTextField();
        strengthField = createTextField();
        techniqueField = createTextField();
        abilitiesField = createTextField();

        detailsPanel.add(imageLabel);
        detailsPanel.add(createLabelledField("Nome:", nameField));
        detailsPanel.add(createLabelledField("Descrizione:", descriptionField));
        detailsPanel.add(createLabelledField("Genere:", genderField));
        detailsPanel.add(createLabelledField("Forza:", strengthField));
        detailsPanel.add(createLabelledField("Tecnica:", techniqueField));
        detailsPanel.add(createLabelledField("Abilità:", abilitiesField));

        // Aggiungere i pannelli al frame
        add(listScrollPane, BorderLayout.WEST);
        add(detailsPanel, BorderLayout.CENTER);

        // Mostrare i dettagli del primo personaggio per default
        if (!characters.isEmpty()) {
            characterList.setSelectedIndex(0);
            showCharacterDetails(0);
        }
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setBackground(Color.WHITE);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        return textField;
    }

    private JPanel createLabelledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(Box.createHorizontalStrut(10));
        panel.add(textField);
        return panel;
    }

    private void showCharacterDetails(int index) {
        Character character = characters.get(index);
        nameField.setText(character.getName());
        descriptionField.setText(character.getDescription());
        genderField.setText(character.getGenre() == 'M' ? "Maschio" : "Femmina");
        strengthField.setText(String.valueOf(character.getStrength()));
        techniqueField.setText(String.valueOf(character.getTechnique()));
        abilitiesField.setText(getAbilitiesText(character));

        // Caricare e impostare l'immagine del personaggio
        ImageIcon imageIcon = new ImageIcon(character.getImage());
        imageLabel.setIcon(imageIcon);
    }

    private String getAbilitiesText(Character character) {
        StringBuilder abilitiesText = new StringBuilder();
        for (int i = 0; i < character.getAbilityCount(); i++) {
            abilitiesText.append(character.getAbility(i).toString());
            if (i < character.getAbilityCount() - 1) {
                abilitiesText.append(", ");
            }
        }
        return abilitiesText.toString();
    }




    public static void main(String[] args) {
        ReaderFile RF =new ReaderFile();
        // Creare personaggi di esempio
        ChooseCharacter cs = new ChooseCharacter(RF.getCharacters());
        cs.setVisible((true));

    }
}

