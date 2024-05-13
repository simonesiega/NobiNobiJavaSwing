package nobinobi.obj.frame;

import nobinobi.obj.*;
import nobinobi.obj.Character;
import nobinobi.obj.editable.*;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Objects;
import java.util.Vector;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class CharacterFrame extends JFrame implements WindowListener{
    private JTextField txtName;
    private JTextArea txtDescrizione;
    JCheckBox chkM = new JCheckBox("M");
    JCheckBox chkF = new JCheckBox("F");
    private JTextField txtImmagine;
    private JTextField txtForza;
    private JTextField txtTecnica;
    private JPanel pnlButtons;
    private JButton btnDelete;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnUpdate;

    private JList<CharacterEditable> lstScene;

    private final Font f = new Font("Arial", Font.PLAIN, 18);
    private final Font fb = new Font("Arial", Font.BOLD, 18);


    //Array gestione ability
    private final Vector<Ability> abilityOpt = new Vector<>();
    private final Vector<JCheckBox> abilitiesCheck = new Vector<>();

    JLabel rNameLabel = new JLabel("");
    JTextArea rDesLabel = new JTextArea("");


    private final Vector<CharacterEditable> scenes = new Vector<>();
    private CharacterEditable currentScene = new CharacterEditable();
    private boolean isNew = true;

    public CharacterFrame() {

        super("Character Editor");
        setSize(1200, 800);
        addWindowListener(this);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        setLayout(layout);

        loadScenes();

        c.weightx = 0.3;
        add(createListPanel(), c);

        c.weightx = 0.7;
        add(createDetailPanel(), c);

        c.weightx = 0.7;
        add(createAbilityPanel(), c);

        refreshList();
        refreshDetail();
    }

    private JPanel createListPanel(){
        JPanel pnl = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        pnl.setLayout(layout);

        c.gridy = 0;
        c.weighty = 0.5;
        JLabel lblList = new JLabel("Elenco Character");
        pnl.add(lblList, c);

        c.gridy++;
        c.weighty = 9;
        lstScene = new JList<>();
        lstScene.addListSelectionListener(e -> {
            if (lstScene.getSelectedValue() != null){
                currentScene = lstScene.getSelectedValue();
                isNew = false;
                refreshDetail();
            }
        });
        pnl.add(lstScene, c);

        pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, 0));
        btnDelete = new JButton("Elimina");
        btnDelete.setFont(fb);
        btnDelete.addActionListener(e -> {
            scenes.remove(currentScene);
            if(scenes.isEmpty()){
                currentScene = new CharacterEditable();
            }else{
                currentScene = scenes.getLast();
            }
            refreshList();
            refreshDetail();
        });
        pnlButtons.add(btnDelete);
        btnSave = new JButton("Salva");
        btnSave.setFont(fb);
        btnSave.addActionListener(e -> {
            try{
                PrintWriter writer = new PrintWriter(new FileOutputStream("nobinobi/obj/saves/characters.csv"));
                for (CharacterEditable ie : scenes) {
                    ie.saveToFile(writer);
                }
                writer.close();
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }
        });
        pnlButtons.add(btnSave);
        c.gridy++;
        c.weighty = 0.5;
        pnl.add(pnlButtons, c);

        return pnl;
    }


    private JPanel createDetailPanel(){
        JPanel pnl = new JPanel();

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        pnl.setLayout(layout);

        JLabel lbl;

        c.gridy = 0;
        c.weighty = 1;
        lbl = new JLabel("Titolo");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtName = new JTextField();
        txtName.setFont(f);
        c.gridy++;
        c.weighty = 1;
        pnl.add(txtName, c);
        c.gridy++;
        c.weighty = 1;

        lbl = new JLabel("Descrizione");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtDescrizione = new JTextArea();
        txtDescrizione.setFont(f);
        c.gridy++;
        c.weighty = 2;
        pnl.add(new JScrollPane(txtDescrizione), c);
        c.gridy++;
        c.weighty = 1;

        chkM.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                chkF.setSelected(false);
            }
        });
        chkF.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                chkM.setSelected(false);
            }
        });


        JPanel checkboxPanelFM = new JPanel(new GridLayout(1, 2)); // 2 righe, 1 colonna
        checkboxPanelFM.add(chkM);
        checkboxPanelFM.add(chkF);

        c.gridy++;
        c.weighty = 1;
        pnl.add(checkboxPanelFM, c);
        c.gridy++;
        c.weighty = 1;

        lbl = new JLabel("Forza");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtForza = createIntegerField();
        txtForza.setFont(f);
        c.gridy++;
        c.weighty = 2;
        pnl.add(txtForza, c);
        c.gridy++;
        c.weighty = 1;

        lbl = new JLabel("Tecnica");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtTecnica = createIntegerField();
        txtTecnica.setFont(f);
        c.gridy++;
        c.weighty = 2;
        pnl.add(txtTecnica, c);
        c.gridy++;
        c.weighty = 1;

        JPanel checkboxPanel = new JPanel(new GridLayout(0, 4)); // 4 checkbox per riga
        for (Ability a : abilityOpt) {
            JCheckBox checkBox = new JCheckBox(a.getName());
            abilitiesCheck.add(checkBox);
            checkBox.setFont(f);
            checkboxPanel.add(checkBox);
            checkBox.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    refreshAbilityDescription(checkBox.getText());
                }
            });
        }

        c.gridy++;
        c.weighty = 1;
        pnl.add(checkboxPanel, c);
        c.gridy++;

        lbl = new JLabel("Immagine");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtImmagine = new JTextField();
        txtImmagine.setFont(f);
        c.gridy++;
        c.weighty = 2;
        pnl.add(txtImmagine, c);
        c.gridy++;
        c.weighty = 1;


        pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, 5));
        btnNew = new JButton("Nuovo");
        btnNew.setFont(fb);
        btnNew.addActionListener(e -> {
            currentScene = new CharacterEditable();
            isNew = true;
            refreshDetail();
        });
        pnlButtons.add(btnNew);
        btnUpdate = new JButton("Aggiorna");
        btnUpdate.setFont(fb);
        btnUpdate.addActionListener(e -> {
            if(txtName.getText().isBlank()){
                currentScene.setName("--");
            }else {
                currentScene.setName(txtName.getText());
            }
            currentScene.setDescription(txtDescrizione.getText());
            currentScene.setStrength(Integer.parseInt(txtForza.getText()));
            currentScene.setTechnique(Integer.parseInt(txtTecnica.getText()));
            currentScene.setImage(txtImmagine.getText());
            if(chkM.isSelected()){
                currentScene.setGenre('M');
            }else{
                currentScene.setGenre('F');
            }
            Ability[] abi = new Ability[6];
            int co = 0;
            for(int i = 0; i < abilitiesCheck.size(); i++){
                if(abilitiesCheck.get(i).isSelected()){
                    abi[co] = abilityOpt.get(i);
                    co++;
                }
                if (co == 6){
                    break;
                }
            }
            currentScene.setAbilities(abi);
            scenes.add(currentScene);
            refreshList();
        });
        pnlButtons.add(btnUpdate);
        c.gridy++;
        c.weighty = 1;
        pnl.add(pnlButtons, c);

        return pnl;
    }

    private JPanel createAbilityPanel() {
        JPanel pnl = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        pnl.setLayout(layout);
        c.gridy = 0;
        c.weighty = 0.1;
        JLabel nameLabel = new JLabel("Nome");
        nameLabel.setFont(fb);
        pnl.add(nameLabel, c);
        c.gridy++;
        c.weighty = 0.1;
        pnl.add(rNameLabel, c);
        c.gridy++;
        c.weighty = 0.1;
        JLabel desLabel = new JLabel("Descrizione");
        desLabel.setFont(fb);
        pnl.add(desLabel, c);
        c.gridy++;
        c.weighty = 1;
        pnl.add(rDesLabel, c);
        rDesLabel.setEditable(false);
        c.weighty = 10;
        return pnl;
    }


    private JTextField createIntegerField() {
        JTextField textField = new JTextField();
        ((PlainDocument) textField.getDocument()).setDocumentFilter(new IntegerFilter());
        return textField;
    }


    /**
     * Aggiorna i dati nello schermo con quelli della scena
     * corrente
     */
    public void refreshDetail(){
        txtName.setText(currentScene.getName());
        txtDescrizione.setText(currentScene.getDescription());
        txtForza.setText(Integer.toString(currentScene.getStrength()));
        txtTecnica.setText(Integer.toString(currentScene.getTechnique()));
        if(currentScene.getGenre() == 'M'){
            chkM.setSelected(true);
            chkF.setSelected(false);
        }else{
            chkF.setSelected(true);
            chkM.setSelected(false);
        }
        for(JCheckBox c : abilitiesCheck) {
            c.setSelected(true);
            for(int i = 0; i < currentScene.getAbilityCount(); i++) {
                if (Objects.equals(c.getText(), currentScene.getAbility(i).getName())) {
                    c.setSelected(true);
                }
            }
        }
    }

    public void refreshList(){
        lstScene.setListData(scenes);
    }

    public void refreshAbilityDescription(String n){
        Ability ab = new Ability();
        for(Ability a : abilityOpt) {
            if(a.getName().equals(n)){
                ab = a;
                break;
            }
        }
        rNameLabel.setText(ab.getName());
        rDesLabel.setText(ab.getDescription());
    }

    private void loadScenes() {
        File file = new File("nobinobi/obj/saves/characters.csv");
        createFileIfNotExists(file);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            scenes.clear();
            while ((line = reader.readLine()) != null && !line.isEmpty())
                /*
                 * Condizione != "" altrimenti primo giro array nel costruttore di Scene ha dimensione 1
                 * ritorna quindi errore su value[1]
                 */
            {
                scenes.add(new CharacterEditable(line));
            }
            reader.close();

        } catch (IOException ioe) {
            System.out.println("Errore durante la lettura del file:");
            System.out.println(ioe.getMessage());
        }

        File file1 = new File("nobinobi/obj/saves/abilities.csv");
        createFileIfNotExists(file);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file1));
            String line;
            abilityOpt.clear();
            while ((line = reader.readLine()) != null && !line.isEmpty())
                /*
                 * Condizione != "" altrimenti primo giro array nel costruttore di Scene ha dimensione 1
                 * ritorna quindi errore su value[1]
                 */
            {
                abilityOpt.add(new Ability(line));
            }
            reader.close();

        } catch (IOException ioe) {
            System.out.println("Errore durante la lettura del file:");
            System.out.println(ioe.getMessage());
        }
    }

    private void createFileIfNotExists(File file) {
        try {
            if (!file.exists()) {
                /*
                if (file.createNewFile()) {
                    System.out.println("File 'scenes.csv' creato con successo.");
                } else {
                    System.out.println("Impossibile creare il file 'scenes.csv'.");
                    System.exit(-1);
                }
                */
                if (!file.createNewFile()) {
                    System.out.println("Impossibile creare il file 'scenes.csv'.");
                    System.exit(-1);
                }
            }
        } catch (IOException e) {
            System.out.println("Errore durante la creazione del file:");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream("nobinobi/obj/saves/characters.csv"));
            for (CharacterEditable ie : scenes) {
                ie.saveToFile(writer);
            }
            writer.close();
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
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
}

