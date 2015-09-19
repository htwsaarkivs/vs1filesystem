package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * This class is part of the package com.company.awt and project test
 * Created by Marc Otting on 15.06.2015.
 * This class provides the following function(s):
 */


//          Info zu JFrame
//          class javax.swing.JFrame
//          extends Frame
//          implements WindowConstants, Accessible, RootPaneContainer


public class SwingGUIPrinzipNoContainer  {

    private JFrame frame;
    private JPanel panel;
    private JPanel panel2;
    private JButton button;


    public SwingGUIPrinzipNoContainer() {
    }

    public void start() {
    buildFrame();
    }

    private void buildPanel(JFrame frame){
        panel = new JPanel();

        Border border = panel.getBorder();
        Border margin = new LineBorder(Color.gray,4);
        panel.setBorder(new CompoundBorder(border, margin));

        button = new JButton("TestButton");
        panel.add(button);

        frame.add(panel,BorderLayout.CENTER);

        panel2 = new JPanel();
        Border border2 = panel2.getBorder();
        Border margin2 = new LineBorder(Color.red,4);
        panel2.setBorder(new CompoundBorder(border2, margin2));

        JButton button2 = new JButton("Button2");
        panel2.add(button2);
        JButton button3 = new JButton("Button3");
        panel2.add(button3);

        frame.add(panel2,BorderLayout.NORTH);

        frame.pack();
    }

    private  void buildFrame() {
        frame = new JFrame("Window");
        frame.setSize(250,250);
        buildPanel(frame);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
