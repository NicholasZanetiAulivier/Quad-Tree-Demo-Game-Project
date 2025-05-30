package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;


public class Item_10 extends Item{
    private static BufferedImage sprite ;
    
    private static final int SPRITE_WIDTH = 14;
    private static final int SPRITE_HEIGHT = 14;
    
    private static final float HITBOX_X_OFFSET = 0;
    private static final float HITBOX_Y_OFFSET = 0;
    private static final int HITBOX_WIDTH = 14;
    private static final int HITBOX_HEIGHT = 14;

    public Item_10(float x , float y){
        super(x,y , 10);
        hitbox = new HitboxRectangular(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET, HITBOX_WIDTH, HITBOX_HEIGHT);
    }

    public Item_10(){
        this(0,0);
    }

    public static void loadSprite() throws Exception{
        sprite = ImageIO.read(Item.class.getResource("rsc/Item/10.png"));
    }

    public static void unload(){
        sprite.flush();
        sprite = null;
    }

    public void draw(Graphics g , ImageObserver o){
        g.drawImage(sprite, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,o);
    }
}
