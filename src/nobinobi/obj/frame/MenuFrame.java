package nobinobi.obj.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class MenuFrame extends JFrame implements WindowListener{
    private JPanel pnlButtons;
    private JButton abilityF;
    private JButton introductionF;
    private JButton characterF;

    private final Font f = new Font("Arial", Font.PLAIN, 18);
    private final Font fb = new Font("Arial", Font.BOLD, 18);

    public MenuFrame() {
        super("Menu Game");
        setSize(800, 600);
        addWindowListener(this);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        setLayout(layout);

        c.weightx = 0.7;
        add(createButtonsPanel(), c);
    }

    private JPanel createButtonsPanel() {
        JPanel pnl = new JPanel();

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        pnl.setLayout(layout);

        c.gridy++;
        c.weighty = 9;

        pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1, 0));
        abilityF = new JButton("Ability Frame");
        abilityF.setFont(fb);
        abilityF.addActionListener(_ -> {
            AbilityFrame aFrame = new AbilityFrame();
            aFrame.setVisible(true);
        });
        pnlButtons.add(abilityF);

        pnlButtons.setLayout(new GridLayout(1, 0));
        characterF = new JButton("Character Frame");
        characterF.setFont(fb);
        characterF.addActionListener(_ -> {
            CharacterFrame cFrame = new CharacterFrame();
            // cFrame.setVisible(true);
        });
        pnlButtons.add(characterF);

        introductionF = new JButton("Introduction Frame");
        introductionF.setFont(fb);
        introductionF.addActionListener(_ -> {
            IntroductionFrame iFrame = new IntroductionFrame();
            iFrame.setVisible(true);
        });
        pnlButtons.add(introductionF);
        c.gridy++;
        c.weighty = 0.5;
        pnl.add(pnlButtons, c);

        return pnl;
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

