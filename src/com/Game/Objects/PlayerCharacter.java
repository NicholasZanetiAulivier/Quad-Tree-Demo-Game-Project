package com.Game.Objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;
import com.Game.Scenes.ShooterGame;

public class PlayerCharacter extends PlayerObject{
    private static BufferedImage sprite = null;
    
    private static final float PLAYER_SPEED = 250;
    private static final float PLAYER_SPEED_FAST = 400;
    private static final int SPRITE_WIDTH = 64;
    private static final int SPRITE_HEIGHT = 64;
    
    private static final float HITBOX_X_OFFSET = 30;
    private static final float HITBOX_Y_OFFSET = 26;
    private static final int HITBOX_WIDTH = 4;
    private static final int HITBOX_HEIGHT = 8;

    private Vector2 position;
    private RectangularHitbox hitbox;
    private boolean goingFast = false;
    private boolean shooting = false;
    private float shootCD = .05f;
    

    public PlayerCharacter(){
        position = new Vector2(Global.realWidth/2,Global.realHeight/2);
        hitbox = new RectangularHitbox(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET, HITBOX_WIDTH,HITBOX_HEIGHT);
        
    }

    public static void loadSprite() throws IOException{
        sprite = ImageIO.read(PlayerCharacter.class.getResource("rsc/Players/Ship_11.png"));
    }

    public static void unload(){
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
        
        hitbox.setPosition(position.x+speed.x, position.y+speed.y);

        //  Shoot bullets if is pressing SHOOT
        shootCD -= dt;
        if(shooting && shootCD < 0){
            shootCD = 0.05f;
            ((ShooterGame)Global.currentScene).friendlyObjects.append(new PlayerBulletBasic(position.x+24, position.y+30));
            ((ShooterGame)Global.currentScene).friendlyObjects.append(new PlayerBulletBasic(position.x+12, position.y+36));
            ((ShooterGame)Global.currentScene).friendlyObjects.append(new PlayerBulletBasic(position.x+36, position.y+36));
        }
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        //TODO: make draw function for player
        g.drawImage(sprite, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }
}
