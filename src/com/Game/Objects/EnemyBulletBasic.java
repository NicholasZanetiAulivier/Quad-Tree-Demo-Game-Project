package com.Game.Objects;

import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.geom.Rectangle2D;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public class EnemyBulletBasic extends EnemyBullet{
    private static BufferedImage sprite;
    
    private static final float BULLET_VELOCITY = 200;
    private static final int BULLET_WIDTH = 70;
    private static final int BULLET_HEIGHT = 70;
    
    private static final int HITBOX_RADIUS = 3;
    private static final int HITBOX_X_OFFSET = 32;
    private static final int HITBOX_Y_OFFSET = 31;
    
    private Vector2 position;
    private BufferedImage rotatedImage;
    
    public EnemyBulletBasic(Vector2 pos , Vector2 dire) throws Throwable{
        position = pos;
        direction = dire;
        direction.normalize();
        rotatedImage = new BufferedImage(sprite.getWidth(),sprite.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        Graphics2D gs = rotatedImage.createGraphics();
        if(direction.x < 0)
            gs.rotate(Math.PI , rotatedImage.getWidth()/2 , rotatedImage.getHeight()/2);
        gs.rotate(direction.getDirection(), rotatedImage.getWidth()/2 , rotatedImage.getHeight()/2);
        gs.drawImage(sprite , null , 0 , 0);
        hitbox = new HitboxCircular(position.x+HITBOX_X_OFFSET,position.y+HITBOX_Y_OFFSET,HITBOX_RADIUS);
    }

    public EnemyBulletBasic(float x , float y , float dirX , float dirY) throws Throwable{
        this(new Vector2(x,y) , new Vector2(dirX,dirY));
    }
    
    public static void loadSprite() throws IOException{
        sprite = ImageIO.read(PlayerBulletBasic.class.getResource("rsc/Bullets/EnemyBulletBasic.png"));
    }

    public static void unload(){
        sprite = null;
    }

    @Override
    public void update(float dt){
        if(position.y > Global.realHeight || position.y < -70 || position.x < -70 || position.x > Global.realWidth) {
            shouldDestroy = true;
            return;
        }
        position.add(Vector2.scale(direction, dt*BULLET_VELOCITY));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        g.drawImage(rotatedImage, Math.round(position.x), Math.round(position.y),BULLET_WIDTH, BULLET_HEIGHT, o);
    }

    @Override
    public boolean hasCollisions(){
        return true;
    }

    @Override
    public short getType(){
        return CollisionObject.CIRCLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_BULLET_BASIC;
    }

    @Override
    public void checkCollision(CollisionObject c){
        super.checkCollision(c);
    }

    @Override
    public Rectangle2D getBounds(){
        return hitbox.getBounds();
    }

    @Override
    public void isColliding(CollisionObject c){
    }
}
