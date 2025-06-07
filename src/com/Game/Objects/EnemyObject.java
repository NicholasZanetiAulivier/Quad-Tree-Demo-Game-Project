package com.Game.Objects;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public abstract class EnemyObject implements CollisionObject, Entity , Drawable{
    protected Hitbox<?> hitbox = null;
    protected boolean colliding = false;
    public boolean shouldDestroy = false;
    public int HP;

    public Hitbox<?> getHitbox(){
        return hitbox;
    }

    @Override
    public void checkCollision(CollisionObject c){
        short type = c.getType();
        boolean collides = false;
        switch(type){
            case CollisionObject.RECTANGLE : {
                collides = checkCollision((Rectangle)c.getHitbox().getHitbox());
                break;
            }
            case CollisionObject.CIRCLE : {
                collides = checkCollision((Ellipse2D)c.getHitbox().getHitbox());
                break;
            }
        }
        if(collides){
            colliding = true;
            isColliding(c);
            c.isColliding(this);
        }
    }

    public boolean checkCollision(Shape r){
        if(r == null) return false;
        if (r.contains(this.getBounds()) || r.intersects(this.getBounds())) return true;
        else return false;
    }
}
