package com.Game.Window;

import javax.swing.JPanel;
import java.awt.Color;

/*
 * GameScreen acts as a canvas where entities are drawn onto the screen
 */
public class GameScreen extends JPanel{

    public GameScreen(){
        super(true);
        setBackground(new Color(0xC5C5C5));
    }
}
