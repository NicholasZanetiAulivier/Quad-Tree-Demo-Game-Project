package com.Game.Objects;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.awt.Graphics;

import javax.imageio.ImageIO;

import com.Game.Engine.Global;

public class BasicObject implements Drawable , Entity{
    private static BufferedImage sprite;
    public int x;
    public int y;

    public BasicObject(int x , int y){
        this.x = x ;
        this.y = y;
    }

    public BasicObject(){
        x = 0; y = 0;
    }

    public static void loadSprite() throws IOException{
        BasicObject.sprite = ImageIO.read(BasicObject.class.getResource("rsc/sixteen.png"));
    }

    public static void freeSprite(){
        BasicObject.sprite = null;
    }

    public void draw(Graphics g , ImageObserver observer){
        g.drawImage(BasicObject.sprite ,x , y ,observer);
    }

    public void update(double dt){
        this.x = Global.MOUSE.x;
        this.y = Global.MOUSE.y;
    }
    
}
