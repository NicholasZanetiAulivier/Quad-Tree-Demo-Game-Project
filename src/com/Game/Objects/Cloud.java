package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public class Cloud implements Entity, Drawable{
    public static Image[] clouds;
    
    public Vector2 position;
    public float scrollSpeed;
    public boolean shouldDestroy = false;
    public int whichCloud = 0;

    public Cloud(float x , float y , int id){
        position = new Vector2(x, y);
        scrollSpeed = (float)Math.random()*200+200;
        whichCloud = id;
    }

    public Cloud(float x , float y){
        position = new Vector2(x, y);
        scrollSpeed = (float)Math.random()*200+200;
        whichCloud = (int)Math.round(Math.random()*5);
    }
    
    public Cloud(float x){
        position = new Vector2(x, -100);
        scrollSpeed = (float)Math.random()*200+200;
        whichCloud = (int)Math.round(Math.random()*5);
    }
    
    public Cloud(){
        position = new Vector2((float)Math.random()*(Global.originalWidth-100) , -100);
        scrollSpeed = (float)Math.random()*200+200;
        whichCloud = (int)Math.round(Math.random()*5);
    }

    @Override
    public void update(float dt){
        if(position.y > Global.originalHeight){
            this.shouldDestroy = true;
        }

        position.y += scrollSpeed * dt;
    }

    public static void loadSprite() throws IOException{
        clouds = new Image[6];

        BufferedImage temp = ImageIO.read(Cloud.class.getResource("rsc/Clouds.png"));
        Image n;

        n = temp.getSubimage(8, 13, 67-8+1, 47-13+1);
        clouds[0] = n.getScaledInstance(n.getWidth(null)*3, n.getHeight(null)*3, BufferedImage.SCALE_FAST);
        n = temp.getSubimage(81,24,110-81+1,43-24+1);
        clouds[1] = n.getScaledInstance(n.getWidth(null)*3, n.getHeight(null)*3, BufferedImage.SCALE_FAST);
        n = temp.getSubimage(8,55,41-8+1,77-55+1);
        clouds[2] = n.getScaledInstance(n.getWidth(null)*3, n.getHeight(null)*3, BufferedImage.SCALE_FAST);
        n = temp.getSubimage(67,55,116-67+1,83-55+1);
        clouds[3] = n.getScaledInstance(n.getWidth(null)*3, n.getHeight(null)*3, BufferedImage.SCALE_FAST);
        n = temp.getSubimage(16,86,56-16+1,113-86+1);
        clouds[4] = n.getScaledInstance(n.getWidth(null)*3, n.getHeight(null)*3, BufferedImage.SCALE_FAST);
        n = temp.getSubimage(73,93,102-73+1,116-93+1);
        clouds[5] = n.getScaledInstance(n.getWidth(null)*3, n.getHeight(null)*3, BufferedImage.SCALE_FAST);

    }

    public static void unload(){
        clouds = null;
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        g.drawImage(clouds[whichCloud], Math.round(position.x), Math.round(position.y), o);
    }

    @Override
    public boolean hasCollisions(){
        return false;
    }

}
