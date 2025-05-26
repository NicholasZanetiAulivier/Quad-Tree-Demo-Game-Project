package com.Game.Objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

public class HitboxRectangular extends Hitbox{

    public HitboxRectangular(Rectangle2D b){
        super(b);
    }

    public HitboxRectangular(float x , float y , float width , float height){
        super(x, y, width, height);
    }

    public void draw(Graphics g , ImageObserver i){
        g.setColor(new Color(1f,.5f,.5f,.9f));
        g.fillRect(
            (int)Math.round(bounds.getX()),
            (int)Math.round(bounds.getY()),
            (int)Math.round(bounds.getWidth()),
            (int)Math.round(bounds.getHeight())
        );
    }

    
}
