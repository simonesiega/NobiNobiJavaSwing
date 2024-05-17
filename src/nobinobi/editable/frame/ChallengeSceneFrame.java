package nobinobi.editable.frame;

import nobinobi.Condition;
import nobinobi.editable.ChallengeSceneEditable;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.Vector;

public class ChallengeSceneFrame extends JFrame implements WindowListener {
    private JTextField txtName;
    private JTextArea txtDescrizione;

    private JTextArea txtWinDescrizione;
    private JTextArea txtLostDescrizione;
    private JTextField txtForza;
    private JTextField txtTecnica;
    private final Vector<JCheckBox> conditionsPanel = new Vector<>();
    private final Vector<JCheckBox> endconditionsPanel = new Vector<>();
    private JPanel pnlButtons;
    private JButton btnDelete;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnUpdate;

    private JList<ChallengeSceneEditable> lstScene;

    private final Font f = new Font("Arial", Font.PLAIN, 18);
    private final Font fb = new Font("Arial", Font.BOLD, 18);

    private final Vector<ChallengeSceneEditable> scenes = new Vector<>();
    private ChallengeSceneEditable currentScene = new ChallengeSceneEditable();
    private boolean isNew = true;

    private JPanel cardPanel; // Pannello che contiene i pannelli delle checkbox
    private CardLayout cardLayout; // Layout per il cambio di pannelli

    public ChallengeSceneFrame() {
        super("ChallengeScene Editor");
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
        refreshList();
    }

    private JPanel createListPanel() {
        JPanel pnl = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        pnl.setLayout(layout);

        c.gridy = 0;
        c.weighty = 0.5;
        JLabel lblList = new JLabel("Elenco Scene");
        pnl.add(lblList, c);

        c.gridy++;
        c.weighty = 9;
        lstScene = new JList<>();
        lstScene.addListSelectionListener(e -> {
            if (lstScene.getSelectedValue() != null) {
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
            if (scenes.isEmpty()) {
                currentScene = new ChallengeSceneEditable();
            } else {
                currentScene = scenes.lastElement();
            }
            refreshList();

        });
        pnlButtons.add(btnDelete);
        btnSave = new JButton("Salva");
        btnSave.setFont(fb);
        btnSave.addActionListener(e -> {
            try {
                PrintWriter writer = new PrintWriter(new FileOutputStream("src/challengescene.csv"));
                for (ChallengeSceneEditable ie : scenes) {
                    ie.saveToFile(writer);
                }
                writer.close();
            } catch (IOException ioe) {
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

        lbl = new JLabel("Descrizione Vittoria");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtWinDescrizione = new JTextArea();
        txtWinDescrizione.setFont(f);
        c.gridy++;
        c.weighty = 2;
        pnl.add(new JScrollPane(txtWinDescrizione), c);
        c.gridy++;
        c.weighty = 1;

        lbl = new JLabel("Descrizione Perdita");
        lbl.setFont(fb);
        pnl.add(lbl, c);
        txtLostDescrizione = new JTextArea();
        txtLostDescrizione.setFont(f);
        c.gridy++;
        c.weighty = 2;
        pnl.add(new JScrollPane(txtLostDescrizione), c);
        c.gridy++;
        c.weighty = 1;

        JButton switchButton = new JButton("Vai a EndCondition");
        switchButton.setForeground(Color.RED);
        final boolean[] isEnd = {false};
        switchButton.setFont(fb);
        switchButton.addActionListener(e -> {

            if(isEnd[0]){
                isEnd[0] = false;
                switchButton.setText("Vai a EndCondition");
                switchButton.setForeground(Color.RED);
            }
            else{
                isEnd[0] = true;
                switchButton.setText("Vai a Condition");
                switchButton.setForeground(Color.BLACK);
            }
            cardLayout.next(cardPanel);
        });
        c.gridy++;
        c.weighty = 1;
        pnl.add(switchButton, c);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(createCheckboxPanelCondition(conditionsPanel), "panel1");
        cardPanel.add(createCheckboxPanelEndCondition(endconditionsPanel), "panel2");
        c.gridy++;
        c.weighty = 1;
        pnl.add(cardPanel, c);

        JPanel controlPanel = new JPanel(new GridLayout(1, 2)); // 2 Cosí si puó aggiungere immediatamente il bottone deseleziona tutto

        JCheckBox selectAllCheckBox = new JCheckBox("Seleziona tutto");
        selectAllCheckBox.setFont(f);
        selectAllCheckBox.addActionListener(e -> {
            for (JCheckBox checkBox : conditionsPanel) {
                checkBox.setSelected(selectAllCheckBox.isSelected());
            }
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
            currentScene = new ChallengeSceneEditable();
            isNew = true;
            refreshDetail();
        });
        pnlButtons.add(btnNew);
        btnUpdate = new JButton("Aggiorna");
        btnUpdate.setFont(fb);
        btnUpdate.addActionListener(e -> {
            if (txtName.getText().isEmpty()) {
                currentScene.setName("--");
            } else {
                currentScene.setName(txtName.getText());
            }
            currentScene.setDescription(txtDescrizione.getText());
            if (txtForza.getText().isEmpty()) {
                currentScene.setStrength(0);
            } else {
                currentScene.setStrength(Integer.parseInt(txtForza.getText()));
            }
            if (txtTecnica.getText().isEmpty()) {
                currentScene.setTechnique(0);
            } else {
                currentScene.setTechnique(Integer.parseInt(txtTecnica.getText()));
            }
            currentScene.setWinDescription(txtWinDescrizione.getText());
            currentScene.setLostDescription((txtLostDescrizione.getText()));

            int flag = 0;
            int flag2 = 0;
            for (int i = 0; i < conditionsPanel.size(); i++) {
                if (conditionsPanel.get(i).isSelected()) {
                    flag += Math.pow(2, i);
                }
            }
            for (int i = 0; i < endconditionsPanel.size(); i++) {
                if (endconditionsPanel.get(i).isSelected()) {
                    flag2 += Math.pow(2, i);
                }
            }
            Condition condition = new Condition(flag);
            Condition endcondition = new Condition(flag2);
            currentScene.setCondition(condition);
            currentScene.setEndCondition(endcondition);

            if (isNew) {
                scenes.add(currentScene);
            }

            refreshList();
        });
        pnlButtons.add(btnUpdate);
        c.gridy++;
        c.weighty = 1;
        pnl.add(pnlButtons, c);

        return pnl;
    }

    private JPanel createCheckboxPanelCondition(Vector<JCheckBox> conditions) {
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
            checkboxPanel.add(checkBox);
        }
        return checkboxPanel;
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

    private JTextField createIntegerField() {
        JTextField textField = new JTextField();
        ((PlainDocument) textField.getDocument()).setDocumentFilter(new IntegerFilter());
        return textField;
    }

    public void refreshDetail() {
        txtName.setText(currentScene.getName());
        txtDescrizione.setText(currentScene.getDescription());
        txtForza.setText(Integer.toString(currentScene.getStrength()));
        txtTecnica.setText(Integer.toString(currentScene.getTechnique()));
        txtWinDescrizione.setText(currentScene.getWinDescription());
        txtLostDescrizione.setText(currentScene.getLostDescription());

        for (JCheckBox a : conditionsPanel) {
            a.setSelected(false);
        }
        for (JCheckBox a : endconditionsPanel) {
            a.setSelected(false);
        }
        for (int i = 0; i < conditionsPanel.size(); i++) {
            if (((currentScene.getCondition().getCondition()) & ((int) Math.pow(2, i))) > 0) {
                conditionsPanel.get(i).setSelected(true);
            }
        }
        for (int i = 0; i < endconditionsPanel.size(); i++) {
            if (((currentScene.getEndCondition().getCondition()) & ((int) Math.pow(2, i ))) > 0) {
                endconditionsPanel.get(i).setSelected(true);
            }
        }
    }

    public void refreshList() {
        lstScene.setListData(scenes);
    }

    private void loadScenes() {
        File file = new File("src/challengescene.csv");
        createFileIfNotExists(file);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            scenes.clear();
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                scenes.add(new ChallengeSceneEditable(line));
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

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            PrintWriter writer = new PrintWriter(new FileOutputStream("src/challengescene.csv"));
            for (ChallengeSceneEditable ie : scenes) {
                ie.saveToFile(writer);
            }
            writer.close();
        } catch (IOException ioe) {
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
