package com.Game.Objects;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;

import javax.imageio.ImageIO;

public class BasicSprite implements Drawable{
    private static BufferedImage sprite;
    public int xPos;
    public int yPos;

    public BasicSprite(int x , int y){
        xPos = x ;
        yPos = y;
    }

    public BasicSprite(){
        xPos = 0; yPos = 0;
    }

    public static void loadSprite(String imagePath) throws IOException{
        BasicSprite.sprite = ImageIO.read((new File(imagePath)).toURI().toURL());
    }

    public static void freeSprite(){
        BasicSprite.sprite = null;
    }

    public void draw(Graphics g , ImageObserver observer){
        g.drawImage(BasicSprite.sprite ,xPos , yPos ,observer);
    }
    
}
