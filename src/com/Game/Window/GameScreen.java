package com.Game.Window;

import com.Game.Callbacks.DrawFunc;
import com.Game.Engine.Global;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


/*
 * GameScreen acts as a canvas where entities are drawn onto the screen
 */
public class GameScreen extends JPanel{

    @SuppressWarnings("all")
    private DrawFunc painter = (a)->{};
    
    public GameScreen(){
        super(null,true);
        setBackground(new Color(1f,1f,1f,0));
    }

    public void setDrawFunction(DrawFunc f){
        this.painter = f;
    }

    /*
     * PaintComponent draws everything, should be changed if callback function is given
     * 
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //Scales Canvas to fit window properly
        ((Graphics2D)g).scale(Global.DRAW_SCALE,Global.DRAW_SCALE);
        
        painter.draw(g);
    }

    //WINDOW IS NOT RESIZEABLE, unable to fix bug where canvas size is inacurate in many different scales
}
