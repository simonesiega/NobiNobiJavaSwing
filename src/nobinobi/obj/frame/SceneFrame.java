package nobinobi.obj.frame;

import java.awt.GridLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nobinobi.obj.editable.*;

public class SceneFrame extends JFrame implements WindowListener{
    private JTextField txtTitolo;
    private JTextArea txtDescrizione;
    private JTextField txtImmagine;
    private JPanel pnlButtons;
    private JButton btnLoad;
    private JButton btnNew;
    private JButton btnSave;
    private JButton btnUpdate;

    private JList<IntroductionEditable> lstScene;

    private Font f = new Font("Arial", Font.PLAIN, 18);
    private Font fb = new Font("Arial", Font.BOLD, 18);

    private Vector<IntroductionEditable> scenes = new Vector<IntroductionEditable>();
    private IntroductionEditable currentScene = new IntroductionEditable();
    private boolean isNew = true;

    public SceneFrame() {
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
        lstScene = new JList<IntroductionEditable>();
        lstScene.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (lstScene.getSelectedValue() != null){
                    currentScene = lstScene.getSelectedValue();
                    isNew = false;
                    refreshDetail();
                }
            }

        });
        pnl.add(lstScene, c);

        pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, 0));
        btnLoad = new JButton("Carica");
        btnLoad.setFont(fb);
        pnlButtons.add(btnLoad);
        btnSave = new JButton("Salva");
        btnSave.setFont(fb);
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    PrintWriter writer = new PrintWriter(new FileOutputStream("nobinobi/obj/saves/scenes.csv"));
                    for (IntroductionEditable ie : scenes) {
                        ie.saveToFile(writer);
                    }
                    writer.close();
                }
                catch(IOException ioe){
                    System.out.println(ioe.getMessage());
                }
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

        pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, 5));
        btnNew = new JButton("Nuovo");
        btnNew.setFont(fb);
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentScene = new IntroductionEditable();
                isNew = true;
                refreshDetail();
            }

        });
        pnlButtons.add(btnNew);
        btnUpdate = new JButton("Aggiorna");
        btnUpdate.setFont(fb);
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentScene.setTitle(txtTitolo.getText());
                currentScene.setDescription(txtDescrizione.getText());
                currentScene.setImage(txtImmagine.getText());
                if (isNew){
                    scenes.add(currentScene);
                    isNew = false;
                }
                refreshList();
            }

        });
        pnlButtons.add(btnUpdate);
        c.gridy++;
        c.weighty = 1;
        pnl.add(pnlButtons, c);

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
    }

    public void refreshList(){
        lstScene.setListData(scenes);
    }

    private void loadScenes() {
        File file = new File("nobinobi/obj/saves/scenes.csv");
        createFileIfNotExists(file);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            scenes.clear();
            while ((line = reader.readLine()) != null) {
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
}
