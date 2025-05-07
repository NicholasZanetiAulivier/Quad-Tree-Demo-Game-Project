package com.screen;
import javax.swing.*;

/*
 * Basic Window to draw all things
 */

public class MainWindow extends JFrame {
    public MainWindow(String name){
        setTitle(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JButton butt = new JButton("Test Button");
        this.getContentPane().add(butt);
        pack();
        setVisible(true);
    }

    public MainWindow(){
        this("Unnamed Window");
    }
}
