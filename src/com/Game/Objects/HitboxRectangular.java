package com.Game.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class HitboxRectangular extends Hitbox<Rectangle>{

    public HitboxRectangular(Rectangle b){
        super(b , (Rectangle)b.clone());
    }

    public HitboxRectangular(float x , float y , float width , float height){
        super(x, y, width, height , new Rectangle((int)x,(int)y,(int)width,(int)height));
    }

    public void setPosition(float x , float y){
        super.setPosition(x, y);
        super.hitbox.x = (int)x ;
        super.hitbox.y = (int)y;
    }

    public void draw(Graphics g , ImageObserver i){
        g.setColor(new Color(1f,.5f,.5f,.9f));
        g.fillRect(
            (int)Math.round(super.hitbox.x),
            (int)Math.round(super.hitbox.y),
            (int)Math.round(super.hitbox.width),
            (int)Math.round(super.hitbox.height)
        );
    }

    
}
