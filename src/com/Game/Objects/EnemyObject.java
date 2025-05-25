package com.Game.Objects;

public abstract class EnemyObject implements Entity , Drawable{
    protected Hitbox hitbox = null;
    public boolean shouldDestroy = false;
    public Hitbox getHitbox(){
        return hitbox;
    }
}
