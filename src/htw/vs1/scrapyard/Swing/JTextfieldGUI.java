package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is part of the package htw.vs1.scrapyard.Swing and project ver1
 * Created by Marc Otting on 23.06.2015.
 * This class provides the following function(s):
 */
public class JTextfieldGUI extends JFrame {

    private JPanel panel;
    private JTextField textfield;
    private JButton button;

    public void start(){
        buildGUI();
    }

    private void buildGUI() {
        this.setSize(250, 250);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPane();
        this.add(panel, BorderLayout.CENTER);
        this.add(textfield,BorderLayout.SOUTH);
        this.setVisible(true);
        this.pack();

    }

    private void buildPane() {
        panel = new JPanel();
        textfield = new JTextField();
        button = new JButton("Hit me!");

        textfield.setVisible(true);
        textfield.setText("Schreib was du Luder!");

        textfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String schreibsel = textfield.getText();
                JOptionPane.showMessageDialog(null,schreibsel);
            }
        });
   //   panel.add(textfield, BorderLayout.NORTH);
        panel.add(button,BorderLayout.SOUTH);


    }
}
