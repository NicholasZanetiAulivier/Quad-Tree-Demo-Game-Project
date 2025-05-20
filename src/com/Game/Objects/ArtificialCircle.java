package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.Vector;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public class ArtificialCircle implements Entity, Drawable{
    public Vector2 position; //Top left corner of circle
    public float rad;
    public Vector2 velocity;

    public ArtificialCircle(int x , int y , float vX , float vY,float rad){
        this.position = new Vector2(x,y);
        this.rad = rad;
        this.velocity = new Vector2(vX, vY);
    }

    public void update(float dt){
        float x = position.getX();
        float y = position.getY();
        
        if(x < 0 || x > Global.realWidth-2*rad) {
            vX = -vX;
            System.out.println("Bounced at x: "+x);
        };
        if(y < 0 || y > Global.realHeight-2*rad) {
            vY = -vY;
            System.out.println("Bounced at y: "+y);
        }
        
        position = Vector2.add(position , new Vector2(dt*vX, dt*vY));
    }

    public void draw(Graphics g , ImageObserver o){
        g.drawOval((int)position.getX() , (int)position.getY(),(int)rad*2,(int)rad*2);
    }

    public String toString(){
        return this.position.getX()+","+this.position.getY()+","+this.vY+","+this.vX+","+this.rad;
    }
}
