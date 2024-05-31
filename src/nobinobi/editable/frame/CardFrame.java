package nobinobi.editable.frame;

import nobinobi.Condition;
import nobinobi.editable.*;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Vector;

/**
 * Frame per la creazione delle carte
 */
public class CardFrame extends JFrame implements WindowListener{
    /**
     * Fields e txtArea per la creazione
     */
    private JTextField txtName;
    private JTextArea txtDescrizione;
    private JTextField txtForza;
    private JTextField txtTecnica;
    private final Vector<JCheckBox> conditions = new Vector<>();
    /**
     * Panel e Bottoni
     */
    private JPanel pnlButtons;
    private JButton btnDelete;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnUpdate;

    /**
     * Lista delle Card
     */
    private JList<CardEditable> lstScene;

    //Font
    private final Font f = new Font("Arial", Font.PLAIN, 18);
    private final Font fb = new Font("Arial", Font.BOLD, 18);

    /**
     * Vettore Scene e variabili per il selezionamento
     */
    private final Vector<CardEditable> scenes = new Vector<>();
    private CardEditable currentScene = new CardEditable();
    private boolean isNew = true;

    //Path per la lettura e scrittura
    private final String pathSave = "src/saves/dates/cards.csv";

    public CardFrame() {
        super("Card Editor");
        setSize(800, 600);
        addWindowListener(this);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        setLayout(layout);

        c.weightx = 0.3;
        add(createListPanel(), c);

        c.weightx = 0.7;
        add(createDetailPanel(), c);

        loadScenes();
    }
    /**
     * Metodo per la creazione dell pannello della lista di Card
     * @return
     */
    private JPanel createListPanel(){
        JPanel pnl = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        pnl.setLayout(layout);

        c.gridy = 0;
        c.weighty = 0.5;
        JLabel lblList = new JLabel("Elenco Card");
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
                currentScene = new CardEditable();
            }else{
                currentScene = scenes.lastElement();
            }
            refreshList();
            refreshDetail();
        });
        pnlButtons.add(btnDelete);
        btnSave = new JButton("Salva");
        btnSave.setFont(fb);
        btnSave.addActionListener(e -> {
            try{
                PrintWriter writer = new PrintWriter(new FileOutputStream(pathSave));
                for (CardEditable ie : scenes) {
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

    /**
     * Metodo per la creazione del pannello per la creazione delle Card
     * @return
     */
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

        // Creazione del pannello per le checkbox
        JPanel checkboxPanel = new JPanel(new GridLayout(0, 4)); // 4 checkbox per riga
        String[] tecnicaLabels =    {"Tutte/Una", "Mercato", "Porto","Città",
                "Villaggio","Castello","Chiesa","Foresta",
                "Prateria","Montagna", "Deserto","Collina",
                "Mare","Lago","Fiume","Luna",
                "Cielo", "PNGSingolo","PNGGruppo","PNGFemmina",
                "PNGMaschio"}; // Array di etichette per le checkbox
        for (String label : tecnicaLabels) {
            JCheckBox checkBox = new JCheckBox(label);
            conditions.add(checkBox);
            checkBox.setFont(f);
            checkboxPanel.add(checkBox);
        }

        c.gridy++;
        c.weighty = 1;
        pnl.add(checkboxPanel, c);

        JPanel controlPanel = new JPanel(new GridLayout(1, 2)); //2 Cosí si puó aggiungere immediatamente il bottone deseleziona tutto

        JCheckBox selectAllCheckBox = new JCheckBox("Seleziona tutto");
        selectAllCheckBox.setFont(f);
        selectAllCheckBox.addActionListener(e -> {
            for (JCheckBox checkBox : conditions) {
                checkBox.setSelected(selectAllCheckBox.isSelected());
            }
        });
        controlPanel.add(selectAllCheckBox);

        /*
        JCheckBox deselectAllCheckBox = new JCheckBox("Deseleziona tutto");
        deselectAllCheckBox.setFont(f);
        deselectAllCheckBox.addActionListener(e -> {
            for (JCheckBox checkBox : conditions) {
                checkBox.setSelected(!deselectAllCheckBox.isSelected());
            }
        });
        controlPanel.add(deselectAllCheckBox);

         */

        c.gridy++;
        c.weighty = 1;
        pnl.add(controlPanel, c);

        pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, 5));
        btnNew = new JButton("Nuovo");
        btnNew.setFont(fb);
        btnNew.addActionListener(e -> {
            currentScene = new CardEditable();
            isNew = true;
            refreshDetail();
        });
        pnlButtons.add(btnNew);
        btnUpdate = new JButton("Aggiorna");
        btnUpdate.setFont(fb);
        btnUpdate.addActionListener(e -> {
            if (txtName.getText().isEmpty()){
                currentScene.setName("--");
            } else {
                currentScene.setName(txtName.getText());
            }
            currentScene.setDescription(txtDescrizione.getText());
            if (txtForza.getText().isEmpty()){
                currentScene.setStrength(0);
            } else {
                currentScene.setStrength(Integer.parseInt(txtForza.getText()));
            }
            if (txtTecnica.getText().isEmpty()){
                currentScene.setTechnique(0);
            } else {
                currentScene.setTechnique(Integer.parseInt(txtTecnica.getText()));
            }
            int flag = 0;
            for(int i = 0; i < conditions.size(); i++){
                if(conditions.get(i).isSelected()){
                    flag += (int) Math.pow(2,i);
                }
            }
            currentScene.setConditions(new Condition(flag));
            if (isNew){
                scenes.add(currentScene);
                isNew = false;
            }
            refreshList();
        });
        pnlButtons.add(btnUpdate);
        c.gridy++;
        c.weighty = 1;
        pnl.add(pnlButtons, c);

        return pnl;
    }

    /**
     * metodo per la creazione dell'integerfield
     * @return
     */
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

        for (JCheckBox a : conditions) {
            a.setSelected(false);
        }
        for(int i = 0; i < conditions.size(); i++){
            if(((currentScene.getConditions().getCondition()) & ((int) Math.pow(2,i)))>0){
                conditions.get(i).setSelected(true);
            }
        }
    }
    /**
     * Metodo per il refresh della lista quando viene aggiunta una Card
     */
    public void refreshList(){
        lstScene.setListData(scenes);
    }

    /**
     * Metodo per il caricamento delle Card nella Lista
     */
    private void loadScenes() {
        File file = new File(pathSave);
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
                scenes.add(new CardEditable(line));
            }
            reader.close();
            refreshList();
        } catch (IOException ioe) {
            System.out.println("Errore durante la lettura del file:");
            System.out.println(ioe.getMessage());
        }
    }
    /**
     * Metodo per la creazione del file se non esiste
     * @param file
     */
    private static void createFileIfNotExists(File file) {
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

    /**
     * Quando la pagina viene chiusa salva automaticamente tutte le introduzioni
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream(pathSave));
            for (CardEditable ie : scenes) {
                ie.saveToFile(writer);
            }
            writer.close();
        }
        catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}

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

