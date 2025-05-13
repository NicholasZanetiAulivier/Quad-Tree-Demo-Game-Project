package com.Game.Window;

import com.Game.Engine.DrawFunc;
import com.Game.Engine.Global;
import com.Game.Engine.Scene;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;


/*
 * GameScreen acts as a canvas where entities are drawn onto the screen
 */
public class GameScreen extends JPanel{

    private static DrawFunc painter = new DrawFunc() {
        @Override
        public void draw(Graphics g , Scene scene){
            
        }
    }; 

    public GameScreen(){
        super(true);
        setBackground(new Color(0xC5C5C5));

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
        
        painter.draw(g , Global.currentScene);
    }
}
