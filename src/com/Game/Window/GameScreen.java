package com.Game.Window;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import com.Game.Objects.*;


/*
 * GameScreen acts as a canvas where entities are drawn onto the screen
 */
public class GameScreen extends JPanel{

    public GameScreen(){
        super(true);
        setBackground(new Color(0xC5C5C5));
    }

    /*
     * PaintComponent draws everything, should be changed if callback function is given
     * 
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Drawable obj = new BasicSprite();

        Graphics2D g2d = (Graphics2D) g;
        obj.draw(g2d , this);
    }
}
