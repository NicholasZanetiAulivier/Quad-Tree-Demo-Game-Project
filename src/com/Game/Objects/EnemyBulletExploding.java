package com.Game.Objects;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import javax.imageio.ImageIO;

import com.DataType.Vector2;
import com.Game.Engine.Global;

public class EnemyBulletExploding extends EnemyBulletBasic{
    private static BufferedImage[] sprite;

    private static final int BULLET_WIDTH = 16;
    private static final int BULLET_HEIGHT = 16;

    private static final int HITBOX_RADIUS = 56;
    private static final int HITBOX_X_OFFSET = -48;
    private static final int HITBOX_Y_OFFSET = -48;

    private static final float BULLET_SPEED = 300;

    private float explodeTime;
    private int cycleTime;
    public boolean exploding;

    public EnemyBulletExploding(Vector2 pos , Vector2 v , float bombTime){
        position = pos;
        direction = v;
        direction.normalize();;
        direction.multiply(BULLET_SPEED);
        explodeTime = bombTime;
        exploding = false;
        cycleTime = 0;
        hitbox = new HitboxCircular(position.x+HITBOX_X_OFFSET,position.y+HITBOX_Y_OFFSET,HITBOX_RADIUS);
    }

    public EnemyBulletExploding(float x , float y , float vx , float vy, float bombTime){
        this(new Vector2(x,y) , new Vector2(vx,vy) ,bombTime);
    }

    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(EnemyBulletSpread.class.getResource("rsc/Bullets/EnemyBombs.png"));

        sprite = new BufferedImage[4];

        sprite[0] = temp.getSubimage(0, 0, 8, 8);
        sprite[1] = temp.getSubimage(8, 0, 8, 8);
        sprite[2] = temp.getSubimage(0, 8, 8, 8);
        sprite[3] = temp.getSubimage(8, 8, 8, 8);

        temp.flush();
        temp = null;
    }

    public static void unload(){
        sprite = null;
    }

    @Override
    public void update(float dt){
        if(stall(dt)) return;
        if(destroyCheck()) return;
        move(dt);
    }

    protected boolean destroyCheck(){
        if(position.y > Global.realHeight+100 || position.y < -100 || position.x < -100 || position.x > Global.realWidth+100) {
            shouldDestroy = true;
            return true;
        }
        return false;
    }

    protected void move(float dt){
        position.add(Vector2.scale(direction, dt));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    protected boolean stall(float dt){
        explodeTime -= dt;
        if(explodeTime <= 0){
            if(exploding){
                shouldDestroy = true;
                return true;
            } else {
                exploding = true;
                explodeTime = .5f;
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g , ImageObserver o){
        if(exploding){
            g.drawImage(sprite[cycle()], Math.round(position.x), Math.round(position.y),BULLET_WIDTH, BULLET_HEIGHT, o);
            ((Graphics2D)g).fillOval(hitbox.getX(), hitbox.getY(), (int)Math.round(hitbox.bounds.getWidth()), (int)Math.round(hitbox.bounds.getHeight()));
        } else{
            g.setColor(Color.RED);
            g.drawImage(sprite[cycle()], Math.round(position.x), Math.round(position.y),BULLET_WIDTH, BULLET_HEIGHT, o);
            Graphics2D g2d = (Graphics2D)g;
            g2d.draw(hitbox.hitbox);
        }
    }

    @Override
    public short getType(){
        return CollisionObject.CIRCLE;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_BULLET_EXPLODE;
    }

    public int cycle(){
        cycleTime = ++cycleTime % 28;
        return cycleTime/7;
    }
}
