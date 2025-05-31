package com.Game.Objects;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import com.DataType.Vector2;
import com.Game.Engine.Global;
import com.Game.Scenes.ShooterGame;

public class EnemyEntityShooterBasic extends EnemyEntityBasic{
    private static BufferedImage sprite;
    
    private static final int HITBOX_X_OFFSET = 6;
    private static final int HITBOX_Y_OFFSET = 22;
    private static final int HITBOX_WIDTH = 51;
    private static final int HITBOX_HEIGHT = 48;
    
    private static final int SPRITE_WIDTH = 64;
    private static final int SPRITE_HEIGHT = 80;
    
    private static final float BASIC_COOLDOWN = .2f;
    private static final float STARTING_VELOCITY = 600;

    private static Vector2 acceleration = new Vector2(0,-700);
    private Vector2 velocity;
    private float shootCD = BASIC_COOLDOWN;

    public EnemyEntityShooterBasic(float x , float y){
        position = new Vector2(x, y);
        velocity = new Vector2(0 , STARTING_VELOCITY);
        HP = 1;
        hitbox = new HitboxRectangular(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET, HITBOX_WIDTH , HITBOX_HEIGHT);
    }

    public EnemyEntityShooterBasic(){
        this(0,0);
    }

    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/EnemyShooter.png"));
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
        if(destroyCheck()) return;
        move(dt);
        shoot(dt);
    }

    @Override
    protected void move(float dt){
        Vector2 halfAccel = Vector2.scale(acceleration , 0.5f*dt);
        velocity.add(halfAccel);
        this.position.add(Vector2.scale(velocity , dt));
        velocity.add(halfAccel);
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    protected void shoot(float dt){
        if((shootCD -= dt) < 0){
            shootCD = BASIC_COOLDOWN;
            try{
                ((ShooterGame)Global.currentScene).enemyBullets[Global.counter()].append(new EnemyBulletBasic(
                    new Vector2(position.x+SPRITE_WIDTH/2 , position.y+SPRITE_HEIGHT/2), Vector2.subtract(((ShooterGame)Global.currentScene).player.position,position))
                );
            } catch(Throwable e ){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        g.drawImage(sprite, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
    }

    @Override
    public short getType(){
        return CollisionObject.RECTANGLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_SHOOTER_BASIC;
    }
}
