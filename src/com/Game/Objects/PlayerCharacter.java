package com.Game.Objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;
import com.Game.Scenes.ShooterGame;

public class PlayerCharacter extends PlayerObject{
    private static BufferedImage[] sprite;
    
    private static final float PLAYER_SPEED = 200;
    private static final float PLAYER_SPEED_FAST = 600;
    public static final int SPRITE_WIDTH = 60;
    public static final int SPRITE_HEIGHT = 60;
    
    private static final float HITBOX_X_OFFSET = 27;
    private static final float HITBOX_Y_OFFSET = 23;
    private static final int HITBOX_WIDTH = 7;
    private static final int HITBOX_HEIGHT = 6;

    private int currentCycle = 0;

    public Vector2 position;
    public boolean dead = false;
    private boolean goingFast = true;
    private boolean shooting = false;
    private float shootCD = .05f;

    private int bulletCount = 5;

    public PlayerCharacter(){
        position = new Vector2(Global.realWidth/2,Global.realHeight/2);
        hitbox = new HitboxRectangular(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET, HITBOX_WIDTH,HITBOX_HEIGHT);
    }

    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(PlayerCharacter.class.getResource("rsc/Players/Ship_11.png"));

        sprite = new BufferedImage[2];
        sprite[0] = temp.getSubimage(0, 0,24, 24);
        sprite[1] = temp.getSubimage(0, 24, 24, 24);
    }

    public static void unload(){
        sprite[0].flush();
        sprite[1].flush();
        sprite = null;
    }

    public void setX(float x){
        position.setX(x);
        hitbox.setPosition(x+HITBOX_X_OFFSET, hitbox.getY());   
    }

    public void setY(float y){
        position.setY(y);
        hitbox.setPosition(hitbox.getX(), y+HITBOX_Y_OFFSET);
    }

    public void setPosition(float x , float y){
        position.setX(x);
        position.setY(y);
        hitbox.setPosition(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET);
    }

    public void goFast(){
        this.goingFast = true;
    }

    public void goSlow(){
        this.goingFast = false;
    }
    
    public void switchSpeed(){
        this.goingFast = !this.goingFast;
    }

    public void startShooting(){
        shooting = true;
    }

    public void stopShooting(){
        shooting = false;
    }

    @Override
    public void update(float dt){
        //TODO: make update function for player

        //  Move Player
        ShooterGame currScene = (ShooterGame)Global.currentScene;
        Vector2 speed = new Vector2(0,0);
        if(currScene.up) speed.y -= 1;
        if(currScene.down) speed.y += 1;
        if(currScene.left) speed.x -= 1;
        if(currScene.right) speed.x += 1;

        speed.normalize();
        speed.multiply((goingFast ? PLAYER_SPEED_FAST:PLAYER_SPEED)*dt);
        position.add(speed);
        // System.out.println(speed);
        
        if(position.x < -30) setX(-30);
        else if (position.x > Global.realWidth-SPRITE_WIDTH+30) setX(Global.realWidth-SPRITE_WIDTH+30);
        if(position.y < -30) setY(-30);
        else if (position.y > Global.realHeight-SPRITE_HEIGHT+30) setY(Global.realHeight-SPRITE_HEIGHT+30);
        
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);

        //  Shoot bullets if is pressing SHOOT
        shootCD -= dt;
        if(shooting && shootCD < 0){
            shoot();
        }
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        if(dead) return;
        g.drawImage(sprite[cycle()], Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }

    @Override
    public short getType(){
        return CollisionObject.RECTANGLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.PLAYER;
    }

    @Override 
    public Rectangle getBounds(){
        return hitbox.getBounds();
    }

    @Override
    public void isColliding(CollisionObject o){
        short n = o.getIdentity();
        switch(n){
            case CollisionObject.ITEM:{
                break;
            }

            case CollisionObject.ENEMY_BULLET_EXPLODE : {
                if (((EnemyBulletExploding)o).exploding){
                    dead = true;
                    System.out.println("Player died ex");
                }
                break;
            }
            default :{
                dead = true;
                System.out.println("Player died");
                break;
            }
        } 
    }

    public void shoot(){
        shootCD = 0.05f;
        for(int i = 0 ; i < bulletCount ; i++)
            ((ShooterGame)Global.currentScene).friendlyBullets.append(new PlayerBulletBasic(position.x+SPRITE_WIDTH/2-8, position.y+30));
        ((ShooterGame)Global.currentScene).friendlyBullets.append(new PlayerBulletBouncing(position.x+SPRITE_WIDTH/2-8, position.y+SPRITE_HEIGHT/2 , -1,-.2f));
        ((ShooterGame)Global.currentScene).friendlyBullets.append(new PlayerBulletBouncing(position.x+SPRITE_WIDTH/2-8, position.y+SPRITE_HEIGHT/2 , 1,-.2f));
        ((ShooterGame)Global.currentScene).friendlyBullets.append(new PlayerBulletBouncing(position.x+SPRITE_WIDTH/2-8, position.y+SPRITE_HEIGHT/2 , -1,-3f));
        ((ShooterGame)Global.currentScene).friendlyBullets.append(new PlayerBulletBouncing(position.x+SPRITE_WIDTH/2-8, position.y+SPRITE_HEIGHT/2 , 1,-3f));
    }

    public int cycle(){
        currentCycle = ++currentCycle % 16;
        return currentCycle/8;
    }
}
