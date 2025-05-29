package com.Game.Objects;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;
import com.Game.Scenes.ShooterGame;

public class EnemyEntityShooterSpread extends EnemyEntityShooterBasic{
    private static BufferedImage sprite;
    private static BufferedImage damaged;

    private static final int HITBOX_X_OFFSET = 8;
    private static final int HITBOX_Y_OFFSET = 20;
    private static final int HITBOX_WIDTH = 49;
    private static final int HITBOX_HEIGHT = 40;
    
    private static final int SPRITE_WIDTH = 64;
    private static final int SPRITE_HEIGHT = 80;
    
    private static final float BASIC_COOLDOWN = .5f;
    private static final float STARTING_VELOCITY = 200;

    private static final float[][] shootShape = {
        {1f,-1f},
        {1f,1f},
        {-1f,1f},
        {-1f,-1f},
    };

    private Vector2 velocity;
    private float shootCD = BASIC_COOLDOWN;

    public EnemyEntityShooterSpread(float x , float y){
        position = new Vector2(x, y);
        velocity = new Vector2(0 , STARTING_VELOCITY);
        HP = 1000;
        hitbox = new HitboxRectangular(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET, HITBOX_WIDTH , HITBOX_HEIGHT);
    }

    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/EnemyShotgun.png"));
        sprite = new BufferedImage(temp.getWidth(), temp.getHeight(),temp.getType());
        Graphics2D g = sprite.createGraphics();
        g.rotate(Math.toRadians(180) , temp.getWidth()/2 , temp.getHeight()/2);
        g.drawImage(temp , null , 0 , 0);
        g.dispose();
        
        temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/EnemyShotgunDamaged.png"));
        damaged = new BufferedImage(temp.getWidth(), temp.getHeight(),temp.getType());
        g = damaged.createGraphics();
        g.rotate(Math.toRadians(180) , temp.getWidth()/2 , temp.getHeight()/2);
        g.drawImage(temp , null , 0 , 0);
        g.dispose();
    }

    @Override
    public void update(float dt){
        if(position.y > Global.originalHeight){
            shouldDestroy = true;
            return;
        }

        this.position.add(Vector2.scale(velocity , dt));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
        
        if((shootCD -= dt) < 0){
            shootCD = BASIC_COOLDOWN;
            try{
                float xBPos = position.x + .5f*SPRITE_WIDTH;                
                float yBPos = position.y + .5f*SPRITE_HEIGHT;                

                for(float[] i : shootShape)
                    ((ShooterGame)Global.currentScene).enemyBullets[Global.counter()].append(new EnemyBulletSpread(
                        new Vector2(xBPos , yBPos), new Vector2(i[0],i[1]))
                    );
            } catch(Throwable e ){
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics g , ImageObserver o){
        if(colliding){
            g.drawImage(damaged, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,o);
            colliding = false;
        }
        else g.drawImage(sprite, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,o);
    }

    public short getIdentity(){
        return CollisionObject.ENEMY_SHOOTER_SPREAD;
    }

    public short getType(){
        return CollisionObject.RECTANGLE;
    }
}
