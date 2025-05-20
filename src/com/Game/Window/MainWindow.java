package com.Game.Window;

import javax.swing.JFrame;

import com.Game.Engine.Global;

import java.awt.Color;

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
        this.canvas = new GameScreen();
        this.canvas.setBounds(0,0,Global.originalWidth,Global.originalHeight);
        add(this.canvas);
        setSize(width,height);
        setResizable(false);

        //Base background
        getContentPane().setBackground(new Color(0x000000));
        
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
