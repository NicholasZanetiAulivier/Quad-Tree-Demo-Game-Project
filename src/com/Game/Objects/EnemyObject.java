package com.Game.Objects;

import java.awt.geom.Rectangle2D;

public abstract class EnemyObject implements CollisionObject, Entity , Drawable{
    protected Hitbox hitbox = null;
    protected boolean colliding = false;
    public boolean shouldDestroy = false;
    public int HP;

    public Hitbox getHitbox(){
        return hitbox;
    }

    @Override
    public void checkCollision(CollisionObject c){
        short type = c.getType();
        boolean collides = false;
        switch(type){
            case CollisionObject.RECTANGLE : {
                collides = checkCollision(c.getBounds());
                break;
            }
            case CollisionObject.CIRCLE : {
                break;
            }
        }
        if(collides){
            colliding = true;
            isColliding(c);
            c.isColliding(this);
        }
    }

    public boolean checkCollision(Rectangle2D r){
        if (r.contains(this.getBounds()) || r.intersects(this.getBounds())) return true;
        else return false;
    }
}
