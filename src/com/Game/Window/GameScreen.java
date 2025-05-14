package com.Game.Window;

import com.Game.Engine.DrawFunc;
import com.Game.Engine.Global;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;


/*
 * GameScreen acts as a canvas where entities are drawn onto the screen
 */
public class GameScreen extends JPanel{

    private int width;
    private int height;

    private static DrawFunc painter = new DrawFunc() {
        @Override
        public void draw(Graphics g){
            
        }
    }; 

    public GameScreen(int w , int h){
        super(true);
        setBackground(new Color(0xC5C5C5));
        width = w;
        height = h;
        Global.CANVAS = this;
    }

    public void setDrawFunction(DrawFunc f){
        GameScreen.painter = f;
    }

    /*
     * PaintComponent draws everything, should be changed if callback function is given
     * 
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ((Graphics2D)g).scale(Global.DRAW_SCALE,Global.DRAW_SCALE);
        
        painter.draw(g);
    }

    public void fillBounds(int width , int height){
        if((double)width/height < Global.ASPECT_RATIO){
            Global.DRAW_SCALE = (double)width/Global.originalWidth;
            this.width = width;
            this.height = (int)(this.width / Global.ASPECT_RATIO);
            this.setBounds(0,(int)((height-this.height)/2) , this.width , this.height);
        } else if ((double)width/height > Global.ASPECT_RATIO){
            Global.DRAW_SCALE = (double)height/Global.originalHeight;
            this.height = height;
            this.width = (int)(this.height * Global.ASPECT_RATIO );
            this.setBounds((int)((width - this.width)/2),0,this.width , this.height);
        } else {
            Global.DRAW_SCALE = width/Global.originalWidth;
            this.width = width;
            this.height = height;
        }

        System.out.println("Canvas Width: " + this.width + ", Canvas Height: "+ this.height + ", Scale: " + Global.DRAW_SCALE
                + ", Width: " + width + ", Height" + height
            );
    }
}
