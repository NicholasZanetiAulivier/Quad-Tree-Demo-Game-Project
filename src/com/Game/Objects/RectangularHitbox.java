package com.Game.Objects;

import java.awt.geom.Rectangle2D;

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

    
}
