package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.Game.Engine.Global;

public class ArtificialCircle implements Entity, Drawable{
    public float x;
    public float y;
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
        if(x < 0 || x > Global.originalWidth-2*rad) {
            if(x < 0) x=0;
            if(x > Global.originalWidth-2*rad)x = Global.originalWidth-2*rad;
            vX = -vX;
            System.out.println("Bounced at x: "+x);
        };
        if(y < 0 || y > Global.originalHeight-2*rad) {
            if(y < 0) y=0;
            if(y > Global.originalHeight-2*rad)y = Global.originalHeight-2*rad;
            vY = -vY;
            System.out.println("Bounced at y: "+y);
        }
        

        x = x + vX*dt;
        y = y + vY*dt;
    }

    public void draw(Graphics g , ImageObserver o){
        g.drawOval((int)x,(int)y,(int)rad,(int)rad);
    }

    public String toString(){
        return this.x+","+this.y+","+this.vY+","+this.vX+","+this.rad;
    }
}
