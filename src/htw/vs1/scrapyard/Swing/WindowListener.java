package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import java.awt.event.WindowEvent;

/**
 * This class is part of the package htw.vs1.scrapyard.Swing and project ver1
 * Created by Marc Otting on 23.06.2015.
 * This class provides the following function(s):
 */
public class WindowListener extends WindowListenerAdapter {
    /**
     * Invoked when the user attempts to close the window
     * from the window's system menu.
     *
     * @param e
     */
    @Override
    public void windowClosing(WindowEvent e) {
        super.windowClosing(e);
        JOptionPane.showMessageDialog(null, "Sicher, Alter?");
    }

    /**
     * Invoked the first time a window is made visible.
     *
     * @param e
     */
    @Override
    public void windowOpened(WindowEvent e) {
        super.windowOpened(e);
        JOptionPane.showMessageDialog(null, "Juhu, Aufmerksamkeit!");
    }
}
