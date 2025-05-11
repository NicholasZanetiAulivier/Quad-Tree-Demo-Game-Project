package com.Game.Window;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;

/*
 * Basic Window to contain the canvas
 */

public class MainWindow extends JFrame {
    private GameScreen canvas;

    public MainWindow(int width , int height , String name){
        //Call JFrame Constructor
        super();

        //Basic Window Creation **Must Have**
        setTitle(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Set Size
        setPreferredSize(new Dimension(width,height));
        this.canvas = new GameScreen();
        add(this.canvas , BorderLayout.CENTER);
        pack();

        //Set Visible
        setVisible(true);
    }

    public MainWindow(){
        //Default args
        this(500,500,"Unnamed Window");
    }

    public GameScreen getCanvas(){
        return this.canvas;
    }
}
