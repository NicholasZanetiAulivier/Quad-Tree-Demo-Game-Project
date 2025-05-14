package com.Game.Window;

import javax.swing.JFrame;

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
        setLayout(null);
        setPreferredSize(new Dimension(width,height));
        this.canvas = new GameScreen(width,height);
        this.canvas.setBounds(0,0,width,height);
        add(this.canvas);
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
