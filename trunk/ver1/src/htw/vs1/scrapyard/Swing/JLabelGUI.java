package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import java.awt.*;

/**
 * This class is part of the package htw.vs1.scrapyard.Swing and project ver1
 * Created by Marc Otting on 22.06.2015.
 * This class provides the following function(s):
 */
public class JLabelGUI extends JFrame {
    private JLabel label;
    private JPanel panel;
    private JPanel panel2;

    public void start(){
        buildGUI();
    }

    private void buildGUI() {
        this.setSize(250,250);
        this.setResizable(false);
      //  this.setOpacity(0.5F);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPane();
        this.add(panel,BorderLayout.NORTH);
        this.add(panel2,BorderLayout.CENTER);
        this.pack();
    }

    private void buildPane() {
        panel = new JPanel();
        panel2 = new JPanel();
        label = new JLabel("Boobie Label");
        JButton button1 = new JButton("Boob1");
        JButton button2 = new JButton("Boob2");
        panel.add(label);
        panel2.add(button1);
        panel2.add(button2);

    }
}
