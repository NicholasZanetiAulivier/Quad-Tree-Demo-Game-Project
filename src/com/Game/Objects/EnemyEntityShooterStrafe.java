package com.Game.Objects;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Color;

import com.DataType.Vector2;
import com.Game.Engine.Global;
import com.Game.Scenes.ShooterGame;

public class EnemyEntityShooterStrafe extends EnemyEntityShooterBasic{
    private static BufferedImage[] sprite;

    private static final int HITBOX_X_OFFSET = 6;
    private static final int HITBOX_Y_OFFSET = 6;
    private static final int HITBOX_RADIUS = 8;
    
    private static final int SPRITE_WIDTH = 30;
    private static final int SPRITE_HEIGHT = 30;
    
    private static final float BASIC_COOLDOWN = .5f;
    private static final float STARTING_VELOCITY = 200;

    private static final float[][] shootShape = {
        {-1f,1f},
        {1f,1f}
    };

    private float[][] thisShootShape;
    private Vector2 velocity;
    private float shootCD = BASIC_COOLDOWN;
    private int currState = 0;
    private BufferedImage rotatedImage;

    public EnemyEntityShooterStrafe(float x , float y , float dirX , float dirY){
        position = new Vector2(x, y);
        velocity = new Vector2(dirX , dirY);
        velocity.normalize();
        thisShootShape = new float[2][2];
        for (int i = 0 ; i < 2 ; i++){
            Vector2 temp = new Vector2(shootShape[i][0],shootShape[i][1]);
            // temp.imaginaryMultiply(velocity);
            temp.add(velocity);
            temp.normalize();
            thisShootShape[i][0] = temp.x;
            thisShootShape[i][1] = temp.y;
        }
        velocity.multiply(STARTING_VELOCITY);
        HP = 100;
        rotatedImage = new BufferedImage(sprite[0].getWidth(), sprite[0].getHeight(), BufferedImage.TYPE_INT_ARGB);
        hitbox = new HitboxCircular(x+HITBOX_X_OFFSET, y+HITBOX_Y_OFFSET, HITBOX_RADIUS);
    }

    public static void loadSprite() throws IOException{
        BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/EnemyStrafer.png"));

        sprite = new BufferedImage[2];

        sprite[0] = temp.getSubimage(0,0,31,31);
        sprite[1] = temp.getSubimage(0, 31, 31, 31);
    }

    public static void unload(){
        for(BufferedImage i : sprite) i.flush();
        sprite = null;
    }

    @Override
    protected void move(float dt){
        this.position.add(Vector2.scale(velocity , dt));
        hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
    }

    @Override
    protected void shoot(float dt){
        if((shootCD -= dt) < 0){
            shootCD = BASIC_COOLDOWN;
            try{
                float xBPos = position.x + .5f*SPRITE_WIDTH;                
                float yBPos = position.y + .5f*SPRITE_HEIGHT;                

                for(float[] i : thisShootShape)
                    ((ShooterGame)Global.currentScene).enemyBullets[Global.counter()].append(new EnemyBulletAccelerating(
                        new Vector2(xBPos , yBPos), new Vector2(i[0],i[1]))
                    );
            } catch(Throwable e ){
                e.printStackTrace();
            }
        }
    }

    public void draw(Graphics g , ImageObserver o){
        Graphics2D gs = rotatedImage.createGraphics();
        gs.rotate(velocity.getDirection()+Math.PI/2, rotatedImage.getWidth()/2 , rotatedImage.getHeight()/2);
        if(velocity.x > 0)
            gs.rotate(Math.PI , rotatedImage.getWidth()/2 , rotatedImage.getHeight()/2);
        BufferedImage temp = sprite[cycle()];
        gs.drawImage(temp , null , 0 , 0);
        g.drawImage(rotatedImage, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,o);
        gs.setBackground(new Color(0,0,0,0));
        gs.clearRect(0, 0, rotatedImage.getWidth(), rotatedImage.getHeight());
        gs.dispose();
    }

    public short getIdentity(){
        return CollisionObject.ENEMY_SHOOTER_STRAFE;
    }

    public short getType(){
        return CollisionObject.CIRCLE;
    }

    public int cycle(){
        currState += 1;
        currState %= 8;
        return currState/4;
    }
}
