package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class is part of the package htw.vs1.scrapyard.Swing and project ver1
 * Created by Marc Otting on 23.06.2015.
 * This class provides the following function(s):
 */
public class JFrameIconExternAListener implements ActionListener {
    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == "button1"){
            JOptionPane.showMessageDialog(null,"Button1 hit");
        }else {
            JOptionPane.showMessageDialog(null,"Button2 hit");
        }

    }
}
