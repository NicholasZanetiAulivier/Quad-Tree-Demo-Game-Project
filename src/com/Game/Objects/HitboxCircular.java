package com.Game.Objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.awt.Color;

public class HitboxCircular extends Hitbox{
    
    public HitboxCircular(Rectangle2D r){
        super(r);
    }
    
    public HitboxCircular(float x , float y , float rad){
        super(x,y,rad+rad,rad+rad);
    }

    public void draw(Graphics g , ImageObserver o){
        g.setColor(new Color(1f,.5f,.5f,.9f));
        ((Graphics2D)g).fillOval(
            (int)Math.round(bounds.getX()),
            (int)Math.round(bounds.getY()),
            (int)Math.round(bounds.getWidth()),
            (int)Math.round(bounds.getHeight())
        );
    }
}
