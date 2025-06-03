package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.DataType.Vector2;

public class PlayButton implements Drawable{
    public static BufferedImage sprite;
    public static BufferedImage hover;

    public static final int WIDTH = 168;
    public static final int HEIGHT = 88;

    public boolean hovering;
    public Vector2 position;

    public PlayButton(float x , float y){
        position = new Vector2(x,y);
    }

    public static void loadSprite() throws IOException{
        sprite = ImageIO.read(PlayButton.class.getResource("rsc/Buttons/PlayButton.png"));
        hover = ImageIO.read(PlayButton.class.getResource("rsc/Buttons/PlayButtonHover.png"));
    }

    public static void unload(){
        sprite = hover = null;
    }

    public void draw(Graphics g , ImageObserver o){
        if(hovering) g.drawImage(hover , (int)position.x , (int)position.y , WIDTH , HEIGHT , o);
        else g.drawImage(sprite , (int)position.x , (int)position.y , WIDTH , HEIGHT , o);
    }
}
