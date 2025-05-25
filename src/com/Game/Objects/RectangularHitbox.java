package com.Game.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

public class RectangularHitbox extends Hitbox{

    public RectangularHitbox(Rectangle2D b){
        super(b);
    }

    public RectangularHitbox(float x , float y , float width , float height){
        super(x, y, width, height);
    }

    public short getType(){
        return CollisionObject.RECTANGLE;
    }

    public boolean checkCollision(RectangularHitbox p){
        return this.bounds.contains(p.getBounds()) || this.bounds.intersects(p.getBounds());
    }

    public boolean checkCollision(CollisionObject p){
        if(p.getType() == CollisionObject.RECTANGLE) return checkCollision((RectangularHitbox) p);
        return false;
    }

    public void draw(Graphics g , ImageObserver i){
        g.setColor(Color.BLACK);
        g.setColor(new Color(1f,.5f,.5f,.9f));
        g.fillRect(
            (int)Math.round(bounds.getX()),
            (int)Math.round(bounds.getY()),
            (int)Math.round(bounds.getWidth()),
            (int)Math.round(bounds.getHeight())
        );
    }

    
}
