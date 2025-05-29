package com.Game.Objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.ImageObserver;
import java.awt.Color;

public class HitboxCircular extends Hitbox<Ellipse2D.Float>{

    public HitboxCircular(Rectangle r){
        super(r , new Ellipse2D.Float(r.x,r.y,r.width,r.height));
    }
    
    public HitboxCircular(float x , float y , float rad){
        super(x,y,rad+rad,rad+rad , new Ellipse2D.Float(x,y,rad*2,rad*2));
    }

    public void setPosition(float x , float y){
        super.setPosition(x, y);
        super.hitbox.x = x ;
        super.hitbox.y = y;
    }

    public void draw(Graphics g , ImageObserver o){
        g.setColor(new Color(1f,.5f,.5f,.9f));
        ((Graphics2D)g).fillOval(
            (int)Math.round(super.hitbox.x),
            (int)Math.round(super.hitbox.y),
            (int)Math.round(super.hitbox.width),
            (int)Math.round(super.hitbox.height)
        );
    }
}
