package nobinobi.editable.frame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;
import javax.swing.*;
import nobinobi.Condition;
import nobinobi.editable.*;

public class IntroductionFrame extends JFrame implements WindowListener {
    private JTextField txtTitolo;
    private JTextArea txtDescrizione;
    private JTextField txtImmagine;
    private JPanel pnlButtons;
    private JButton btnDelete;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnUpdate;

    private final Vector<JCheckBox> endconditionsPanel = new Vector<>();

    private JList<IntroductionEditable> lstScene;
    private JPanel cardPanel; // Pannello che contiene i pannelli delle checkbox
    private CardLayout cardLayout; // Layout per il cambio di pannelli
    private final Font f = new Font("Arial", Font.PLAIN, 18);
    private final Font fb = new Font("Arial", Font.BOLD, 18);

    private final Vector<IntroductionEditable> scenes = new Vector<>();
    private IntroductionEditable currentScene = new IntroductionEditable();
    private boolean isNew = true;

    private final String pathSave = "src/saves/dates/introductions.csv";

    public IntroductionFrame() {
        super("Scene Editor");
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

    private JPanel createListPanel(){
        JPanel pnl = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        pnl.setLayout(layout);

        c.gridy = 0;
        c.weighty = 0.5;
        JLabel lblList = new JLabel("Elenco scene");
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
                currentScene = new IntroductionEditable();
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
            try {
                PrintWriter writer = new PrintWriter(new FileOutputStream(pathSave));
                for (IntroductionEditable ie : scenes) {
                    ie.saveToFile(writer);
                }
                writer.close();
            } catch(IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        });
        pnlButtons.add(btnSave);
        c.gridy++;
        c.weighty = 0.5;
        pnl.add(pnlButtons, c);

        return pnl;
    }

    private JPanel createDetailPanel() {
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
        txtTitolo = new JTextField();
        txtTitolo.setFont(f);
        c.gridy++;
        c.weighty = 1;
        pnl.add(txtTitolo, c);
        c.gridy++;
        c.weighty = 1;
        lbl = new JLabel("Descrizione");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtDescrizione = new JTextArea();
        txtDescrizione.setFont(f);
        c.gridy++;
        c.weighty = 10;
        pnl.add(new JScrollPane(txtDescrizione), c);
        c.gridy++;
        c.weighty = 1;
        lbl = new JLabel("Immagine");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtImmagine = new JTextField();
        txtImmagine.setFont(f);
        c.gridy++;
        c.weighty = 2;
        pnl.add(txtImmagine, c);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(createCheckboxPanelEndCondition(endconditionsPanel), "panel2");
        c.gridy++;
        c.weighty = 1;
        pnl.add(cardPanel, c);

        JPanel controlPanel = new JPanel(new GridLayout(1, 2)); // 2 Cosí si puó aggiungere immediatamente il bottone deseleziona tutto

        JCheckBox selectAllCheckBox = new JCheckBox("Seleziona tutto");
        selectAllCheckBox.setFont(f);
        selectAllCheckBox.addActionListener(e -> {
            for (JCheckBox checkBox : endconditionsPanel) {
                checkBox.setSelected(selectAllCheckBox.isSelected());
            }
        });
        controlPanel.add(selectAllCheckBox);
        c.gridy++;
        c.weighty = 1;
        pnl.add(controlPanel, c);

        pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, 5));
        btnNew = new JButton("Nuovo");
        btnNew.setFont(fb);
        btnNew.addActionListener(e -> {
            currentScene = new IntroductionEditable();
            isNew = true;
            refreshDetail();
        });
        pnlButtons.add(btnNew);
        btnUpdate = new JButton("Aggiorna");
        btnUpdate.setFont(fb);
        btnUpdate.addActionListener(e -> {
            if (txtTitolo.getText().isEmpty()){
                currentScene.setTitle("--");
            } else {
                currentScene.setTitle(txtTitolo.getText());
            }
            currentScene.setDescription(txtDescrizione.getText());
            currentScene.setImage(txtImmagine.getText());

            int flag2 = 0;
            for (int i = 0; i < endconditionsPanel.size(); i++) {
                if (endconditionsPanel.get(i).isSelected()) {
                    flag2 += Math.pow(2, i);
                }
            }
            Condition endcondition = new Condition(flag2);
            currentScene.setEndCondition(endcondition);

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
        refreshList();
        return pnl;
    }

    /**
     * Aggiorna i dati nello schermo con quelli della scena
     * corrente
     */
    public void refreshDetail(){
        txtTitolo.setText(currentScene.getTitle());
        txtDescrizione.setText(currentScene.getDescription());
        txtImmagine.setText(currentScene.getImage());

        for (JCheckBox a : endconditionsPanel) {
            a.setSelected(false);
        }

        // Aggiungi codice per impostare lo stato delle checkbox in base a currentScene.getEndCondition()
        Condition endCondition = currentScene.getEndCondition();
        if (endCondition != null) {
            int flag2 = endCondition.getCondition();
            for (int i = 0; i < endconditionsPanel.size(); i++) {
                if ((flag2 & (1 << i)) != 0) {
                    endconditionsPanel.get(i).setSelected(true);
                }
            }
        }
    }

    public void refreshList(){
        lstScene.setListData(scenes);
    }

    private void loadScenes() {
        File file = new File(pathSave);
        createFileIfNotExists(file);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            scenes.clear();
            while ((line = reader.readLine()) != null && !line.isEmpty())
            {
                scenes.add(new IntroductionEditable(line));
            }
            reader.close();
            refreshList();
        } catch (IOException ioe) {
            System.out.println("Errore durante la lettura del file:");
            System.out.println(ioe.getMessage());
        }
    }

    private static void createFileIfNotExists(File file) {
        try {
            if (!file.exists()) {
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

    private JPanel createCheckboxPanelEndCondition(Vector<JCheckBox> conditions) {
        JPanel checkboxPanel = new JPanel(new GridLayout(0, 4)); // 4 checkbox per riga
        String[] tecnicaLabels = {"Tutte/una", "Mercato", "Porto", "Città",
                "Villaggio", "Castello", "Chiesa", "Foresta",
                "Prateria", "Montagna", "Deserto", "Collina",
                "Mare", "Lago", "Fiume", "Luna",
                "Cielo", "PNGSingolo", "PNGGruppo", "PNGFemmina",
                "PNGMaschio"}; // Array di etichette per le checkbox
        for (String label : tecnicaLabels) {
            JCheckBox checkBox = new JCheckBox(label);
            conditions.add(checkBox);
            checkBox.setFont(f);
            checkBox.setForeground(Color.RED);
            checkboxPanel.add(checkBox);
        }
        return checkboxPanel;
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    /**
     * Quando la pagina viene chiusa salva automaticamente tutte le introduzioni
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream(pathSave));
            for (IntroductionEditable ie : scenes) {
                ie.saveToFile(writer);
            }
            writer.close();
        } catch(IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
}
