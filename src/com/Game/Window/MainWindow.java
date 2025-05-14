package com.Game.Window;

import javax.swing.JFrame;

import com.Game.Engine.Global;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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

        //Base background
        getContentPane().setBackground(new Color(0x000000));
        
        //Adding a callback functions(to resize canvas when needed)
        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e){
                Component c = (Component) e.getSource();
                Global.GAME_ENVIRONMENT.resize(c.getWidth() , c.getHeight());
            }

            public void componentShown(ComponentEvent e){}
            public void componentHidden(ComponentEvent e){}
            public void componentMoved(ComponentEvent e){}
        });
        
        
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
