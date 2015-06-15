package htw.vs1.scrapyard.Swing;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

/**
 * This class is part of the package com.company.awt and project test
 * Created by Marc Otting on 15.06.2015.
 * This class provides the following function(s):
 */



public class SwingGUIPrinzipNoContainer extends JFrame {

    private JFrame frame;
    private JPanel panel;
    private JButton button;


    public SwingGUIPrinzipNoContainer() {
    }

    public void start() {
    buildFrame();
    }

    private JPanel buildPanel(){
        panel = new JPanel();

        Border border = panel.getBorder();
        Border margin = new LineBorder(Color.gray,4);
        panel.setBorder(new CompoundBorder(border, margin));

        button = new JButton("TestButton");
        panel.add(button,BorderLayout.EAST);
        JPanel panel2 = new JPanel();

        Border border2 = panel2.getBorder();
        Border margin2 = new LineBorder(Color.gray,4);
        panel2.setBorder(new CompoundBorder(border2, margin2));

        JButton button2 = new JButton("Button2");
        panel2.add(button2,BorderLayout.EAST);
        JButton button3 = new JButton("Button3");
        panel2.add(button3,BorderLayout.WEST);
        panel.add(panel2,BorderLayout.WEST);

        return panel;

    }

    private  void buildFrame() {
        frame = new JFrame("Window");
        frame.setSize(250,250);
        frame.add(buildPanel(),BorderLayout.CENTER);
        frame.setVisible(true);
//      frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
