package com.Game.Window;

import javax.swing.JFrame;

import com.Game.Engine.Global;

import java.awt.Color;

/*
 * Basic Window to contain the canvas
 */

public class MainWindow extends JFrame {
    private GameScreen[] canvas;
    private GameScreen debugCanvas;

    public MainWindow(int width , int height , String name , int numOfCanvases){
        //Call JFrame Constructor
        super();

        //Basic Window Creation **Must Have**
        setTitle(name);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Set Size
        setLayout(null);
        debugCanvas = new GameScreen();
        debugCanvas.setBounds(0,0,Global.originalWidth,Global.originalHeight);
        add(debugCanvas);

        this.canvas = new GameScreen[numOfCanvases];


        //Canvas foreground is always index 0, the next layer is 1, next 2, and so on until the very back which is numOfCanvases-1
        for(int i = 0 ; i < numOfCanvases ; i++){
            this.canvas[i] = new GameScreen();
            this.canvas[i].setBounds(0,0,Global.originalWidth,Global.originalHeight);
            add(this.canvas[i]);
        }

        Global.DEBUG_CANVAS = debugCanvas;
        Global.CANVAS = this.canvas;
        setSize(width,height);
        setResizable(false);
        setLocationRelativeTo(null);

        //Base background
        getContentPane().setBackground(new Color(0x000000));
        
        //Set Visible
        setVisible(true);
    }

    public MainWindow(){
        //Default args
        this(500,500,"Unnamed Window" , 1);
    }

    public GameScreen getCanvas(int i){
        return this.canvas[i];
    }

}
