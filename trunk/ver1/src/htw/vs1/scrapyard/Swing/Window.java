package htw.vs1.scrapyard.Swing;

import javax.swing.*;

/**
 * This class is part of the package htw.vs1.scrapyard.Swing and project ver1
 * Created by Marc Otting on 23.06.2015.
 * This class provides the following function(s):
 */
public class Window extends JFrame{

    public void start(){
        buildWindow();
    }

    private void buildWindow() {
        this.setName("Fensterlein");
        this.setSize(250,250);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.addWindowListener(new WindowListener());
        this.setVisible(true);
    }
}
