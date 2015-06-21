package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import java.awt.*;

public class SwingGUIPrinzip extends JFrame {

  private int zahl = 0;

  private JTextArea   ta;
  private JButton    ab;
  private JButton    sb;
  private SwingController ctrl;

  public SwingGUIPrinzip() {
    ta = new JTextArea("0");
    ab = new JButton("Add");
    sb = new JButton("Sub");
    //Layout
    Container c = getContentPane();


    if (true) {
      add(ta, BorderLayout.CENTER);
      add(ab, BorderLayout.WEST);
      add(sb, BorderLayout.EAST);
    } else {
      c.add(ta, BorderLayout.CENTER);
      c.add(ab, BorderLayout.WEST);
      c.add(sb, BorderLayout.EAST);
    }
            //Ereignisbehandlung
    ab.setActionCommand("add");
    sb.setActionCommand("sub");
    ctrl = new SwingController(ta);
    ab.addActionListener(ctrl);
    sb.addActionListener(ctrl);

    pack();
  }

  public static void main(String[] args) {
    SwingGUIPrinzip sgp= new SwingGUIPrinzip();
    sgp.setTitle("Swing GUIPrinzip");
    sgp.setVisible(true);
  }
}