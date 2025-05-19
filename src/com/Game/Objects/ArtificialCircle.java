package com.Game.Objects;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;

public class ArtificialCircle extends Ellipse2D.Float implements Entity, Drawable{
    public int x;
    public int y;
    public float rad;
    public float vX;
    public float vY;

    public ArtificialCircle(int x , int y , float vX , float vY,float rad){
        this.x = x;
        this.y = y;
        this.rad = rad;
        this.vX = vX;
        this.vY = vY;
    }

    public void update(float dt){
        x = (int)(x + vX);
        y = (int)(y + vY);
    }

    public void draw(Graphics g , ImageObserver o){
        g.drawOval(x,y,(int)rad,(int)rad);
    }
}
