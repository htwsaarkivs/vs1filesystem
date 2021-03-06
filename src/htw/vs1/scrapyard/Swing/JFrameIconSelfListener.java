package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * This class is part of the package htw.vs1.scrapyard.Swing and project ver1
 * Created by Marc Otting on 22.06.2015.
 * This class provides the following function(s):
 */
public class JFrameIconSelfListener extends JFrame implements ActionListener {
    private JPanel panel;
    private Icon icon1;
    private Icon icon2;
    private JButton button1;
    private JButton button2;

    public void start() {
        buildGUI();
    }

    private void buildGUI() {
        this.setSize(250,250);
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPane();
        this.add(panel);
        this.pack();
        this.addWindowListener(new WindowListener());

    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.button1)){
            JOptionPane.showMessageDialog(null,"Button1 Hit in da butt");
        } else {
            JOptionPane.showMessageDialog(null,"Button2 Hit and a bottle of rum");
        }

    }


    private void buildPane() {
        panel = new JPanel();


        URL imgURL = getClass().getResource("\\SwingIcons\\56x56_Haken_Gruen.png");
        URL imgURL2 = getClass().getResource("\\SwingIcons\\56x56_Haken_Rot.png");

        icon1 = new ImageIcon(imgURL);
        icon2 = new ImageIcon(imgURL2);

        button1 = new JButton(icon1);
        button2 = new JButton(icon2);

        panel.add(button1);
        panel.add(button2);

        button1.addActionListener(this);
        button2.addActionListener(this);

    }
}
