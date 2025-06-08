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

public class EnemyEntityShooterBomb extends EnemyEntityBasic{
    private static BufferedImage[] sprite;
    
    private static final int HITBOX_X_OFFSET = 6;
    private static final int HITBOX_Y_OFFSET = 22;
    private static final int HITBOX_WIDTH = 51;
    private static final int HITBOX_HEIGHT = 48;
    
    private static final int SPRITE_WIDTH = 64;
    private static final int SPRITE_HEIGHT = 80;
    
    private static final float BASIC_COOLDOWN = .5f;

    private Vector2 whereToGo;
    private int cycleTime = 0;
    private boolean stationary = false;
    private float shootCD = BASIC_COOLDOWN;

    public EnemyEntityShooterBomb(float x , float y ){
        position = new Vector2(-Global.realWidth/2, -100);
        // position = new Vector2(x,y);
        whereToGo = new Vector2(x,y);
        HP = 10000;
        hitbox = new HitboxRectangular(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET, HITBOX_WIDTH , HITBOX_HEIGHT);
    }

    public EnemyEntityShooterBomb(){
        this(0,0);
    }

    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/EnemyBomber.png"));

        sprite = new BufferedImage[2];

        for (int i = 0 ; i < 2 ; i++){
            BufferedImage t2 = temp.getSubimage(0, i*24, 24, 24); 
            sprite[i] = new BufferedImage(t2.getWidth(), t2.getHeight(), t2.getType());

            Graphics2D g = sprite[i].createGraphics();
            g.rotate(Math.toRadians(180) , t2.getWidth()/2 , t2.getHeight()/2);
            g.drawImage(t2 , null , 0 , 0);
            
        }
    }

    public static void unload(){
        sprite = null;
    }

    @Override
    public void update(float dt){
        move(dt);
        shoot(dt);
    }

    @Override
    protected void move(float dt){
        if(!stationary){
            velocity = Vector2.subtract(whereToGo, position);
            velocity.multiply(dt);
            position.add(velocity);
            hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
            if(Vector2.subtract(whereToGo, position).getLength() < 30) stationary = true;
            return;
        }
        whereToGo = new Vector2((float)Math.random()*(Global.realWidth-SPRITE_WIDTH), (float)Math.random()*(Global.realHeight/2));
        stationary = false;
    }

    protected void shoot(float dt){
        if((shootCD -= dt) < 0){
            shootCD = BASIC_COOLDOWN;
            try{
                ((ShooterGame)Global.currentScene).enemyBullets[Global.counter()].append(new EnemyBulletExploding(
                    new Vector2(position.x+SPRITE_WIDTH/2 , position.y+SPRITE_HEIGHT/2), Vector2.subtract(((ShooterGame)Global.currentScene).player.position,position) , (float)Math.random()*2)
                );
                EnemyEntityShooterBasic.shoot.play();
                ((ShooterGame)Global.currentScene).enemyBullets[Global.counter()].append(new EnemyBulletExploding(
                    new Vector2(position.x+SPRITE_WIDTH/2 , position.y+SPRITE_HEIGHT/2), Vector2.add(Vector2.subtract(((ShooterGame)Global.currentScene).player.position,position) , new Vector2((float)Math.random()*1000-500 , (float)Math.random()*1000-500)) , (float)Math.random()*2)
                    );
                EnemyEntityShooterBasic.shoot.play();
            } catch(Throwable e ){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        g.drawImage(sprite[cycle()], Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
    }

    @Override
    public short getType(){
        return CollisionObject.RECTANGLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_SHOOTER_BOMB;
    }

    private int cycle(){
        cycleTime = ++cycleTime % 16;
        return cycleTime/8;
    }

    @Override
    public void isColliding(CollisionObject c){
        colliding = true;
        short id = c.getIdentity();
        if(id == CollisionObject.PLAYER_BULLET_BASIC || id == CollisionObject.PLAYER_BULLET_BOUNCING){
            HP = HP - ((PlayerBullet)c).damage;
            if(HP <= 0){
                shouldDestroy = true;
                dropItems(new Item_10(position.x,position.y));
                Global.Game.bossDefeated = true;
            }
        }
    }
}
