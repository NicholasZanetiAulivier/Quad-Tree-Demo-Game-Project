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

    private int width;
    private int height;

    private DrawFunc painter = (a,b)->{};

    public GameScreen(int w , int h){
        super(true);
        setBackground(new Color(0xFFFFFF));
        width = w;
        height = h;
        Global.CANVAS = this;
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
        
        painter.draw(g , Global.currentScene);
    }


    /*
     * Literal magic code 
     * 
     * HackFixed by doing this.width-14 and this.height-36 in this.setBounds
     * This is to fix the stupid oversized JPanel that ruins the left-right and top-botton black bars when window is resized
     * This afaik shouldn't affect gameplay
     */
    public void fillBounds(int width , int height){
        if((double)width/height < Global.ASPECT_RATIO){
            Global.DRAW_SCALE = (double)width/Global.originalWidth;
            this.width = width;
            this.height = (int)(this.width / Global.ASPECT_RATIO);
            this.setBounds(0,(int)((height-this.height)/2.) , this.width-14 , this.height-36);
        } else if ((double)width/height > Global.ASPECT_RATIO){
            Global.DRAW_SCALE = (double)height/Global.originalHeight;
            this.height = height;
            this.width = (int)(this.height * Global.ASPECT_RATIO );
            this.setBounds((int)((width - this.width)/2.),0,this.width-14 , this.height-36);
        } else {
            Global.DRAW_SCALE = (double)width/Global.originalWidth;
            this.width = width;
            this.height = height;
            this.setBounds(0,0,this.width-14 , this.height-36);
        }
    }
}
