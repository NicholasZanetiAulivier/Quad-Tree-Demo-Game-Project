package com.Game.Objects;

public abstract class PlayerObject implements Entity , Drawable{
    protected Hitbox hitbox = null;
    public boolean shouldDestroy = false;
    public Hitbox getHitbox(){
        return hitbox;
    }
}
