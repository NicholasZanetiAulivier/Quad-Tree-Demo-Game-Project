package com.Game.Objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;
import com.Game.Scenes.ShooterGame;

public class EnemyEntityHoming extends EnemyEntityBasic{
    private static BufferedImage sprite;
    
    private static final int HITBOX_X_OFFSET = 11;
    private static final int HITBOX_Y_OFFSET = 13;
    private static final int HITBOX_RADIUS = 21;
    
    private static final int SPRITE_WIDTH = 64;
    private static final int SPRITE_HEIGHT = 64;

    private static int SPEED_CONSTANT = 400;
    
    private BufferedImage rotatedImage;
    private Vector2 velocity;

    public EnemyEntityHoming(float x , float y){
        position = new Vector2(x, y);
        velocity = new Vector2(0,100);
        HP = 5;
        hitbox = new HitboxCircular(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET,HITBOX_RADIUS);
        rotatedImage = new BufferedImage(sprite.getWidth(), sprite.getHeight(), sprite.getType());
    }

    public EnemyEntityHoming(){
        this(0,0);
    }

    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/HomingEnemy.png"));
        sprite = new BufferedImage(temp.getWidth(), temp.getHeight(),temp.getType());
        Graphics2D g = sprite.createGraphics();
        g.rotate(Math.toRadians(180) , temp.getWidth()/2 , temp.getHeight()/2);
        g.drawImage(temp , null , 0 , 0);
    }

    public static void unload(){
        sprite.flush();
        sprite = null;
    }

    @Override
    public void update(float dt){
        if(position.y > Global.realHeight) shouldDestroy = true;
        velocity = Vector2.subtract(((ShooterGame)Global.Game).player.position,position);
        velocity.normalize();
        velocity.multiply(SPEED_CONSTANT);
        this.position.add(Vector2.scale(velocity , dt));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        Graphics2D gs = rotatedImage.createGraphics();
        gs.rotate(velocity.getDirection()+Math.PI/2, rotatedImage.getWidth()/2 , rotatedImage.getHeight()/2);
        if(velocity.x > 0)
            gs.rotate(Math.PI , rotatedImage.getWidth()/2 , rotatedImage.getHeight()/2);
        
        gs.drawImage(sprite , null , 0 , 0);
        g.drawImage(rotatedImage, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
        gs.setBackground(new Color(0,0,0,0));
        gs.clearRect(0, 0, rotatedImage.getWidth(), rotatedImage.getHeight());
        gs.dispose();
    }

    @Override
    public short getType(){
        return CollisionObject.CIRCLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_HOMING;
    }
}
