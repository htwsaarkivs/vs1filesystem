package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingController implements ActionListener {

    private JTextArea textFeld;

    public SwingController(JTextArea textFeld)
    {
        this.textFeld=textFeld;
    }

    public void actionPerformed (ActionEvent ae) {
        JButton aeSource = (JButton) ae.getSource();
        if (aeSource.getActionCommand().equals("add")) textFeld.setText("ADD");
        if (aeSource.getActionCommand().equals("sub")) textFeld.setText("SUB");
    }
}