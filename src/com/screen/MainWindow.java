package com.screen;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

/*
 * Basic Window to contain the canvas
 */

public class MainWindow extends JFrame {
    public MainWindow(int width , int height , String name){
        
        //Basic Window Creation **Must Have**
        setTitle(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Set Size
        setPreferredSize(new Dimension(width,height));
        add(new GameScreen() , BorderLayout.CENTER);
        pack();

        //Set Visible
        setVisible(true);
    }

    public MainWindow(){
        //Default args
        this(500,500,"Unnamed Window");
    }
}
