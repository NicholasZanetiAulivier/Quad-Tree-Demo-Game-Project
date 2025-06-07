package com.Game.Objects;

import java.io.IOException;

import java.awt.Color;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import com.DataType.Vector2;
public class EnemyEntityShooterFinalBoss extends EnemyEntityBasic{

    public Summoner summoner;
    public Dasher dasher;
    public Shooter shooter;


    public static class Summoner extends EnemyEntityShooterBasic{
        private static BufferedImage sprite;

        private static final int HITBOX_X_OFFSET = 6;
        private static final int HITBOX_Y_OFFSET = 22;
        private static final int HITBOX_WIDTH = 51;
        private static final int HITBOX_HEIGHT = 48;

        public static final int SPRITE_WIDTH = 100;
        public static final int SPRITE_HEIGHT = 100;
        
        public Vector2 velocity;
        public boolean isActive;
        private static float COOLDOWN = 1.2f;
        private float CD = COOLDOWN;

        public Summoner( float x , float y){
            position = new Vector2(x,y);
            velocity = new Vector2(0,0);
            HP=30000;
            hitbox = new HitboxRectangular(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET, HITBOX_WIDTH , HITBOX_HEIGHT);
        }

        public static void loadSprite() throws IOException{
            BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/EnemyBossSummoner.png"));
            sprite = new BufferedImage(temp.getWidth(), temp.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = sprite.createGraphics();
            g.rotate(Math.toRadians(180) , temp.getWidth()/2 , temp.getHeight()/2);
            g.drawImage(temp , null , 0 , 0);
            
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
        public void draw(Graphics g , ImageObserver observer){
            g.drawImage(sprite, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
        }

        @Override
        public short getType(){
            return CollisionObject.RECTANGLE;
        }

        @Override
        public short getIdentity(){
            return CollisionObject.ENEMY_SUMMONER_BOSS;
        }
    }

    public static class Shooter extends EnemyEntityShooterBasic{
        private static BufferedImage sprite;

        private static final int HITBOX_X_OFFSET = 6;
        private static final int HITBOX_Y_OFFSET = 22;
        private static final int HITBOX_WIDTH = 51;
        private static final int HITBOX_HEIGHT = 48;

        public Vector2 velocity;
        public boolean isActive;
        private static float COOLDOWN = 1.2f;
        private float CD = COOLDOWN;

        public static final int SPRITE_WIDTH = 100;
        public static final int SPRITE_HEIGHT = 100;

        public Shooter(float x , float y){
            position = new Vector2(x,y);
            velocity = new Vector2(0,0);
            HP=30000;
            hitbox = new HitboxRectangular(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET, HITBOX_WIDTH , HITBOX_HEIGHT);
        }

        public static void loadSprite() throws IOException{
            BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/EnemyBossShooter.png"));
            sprite = new BufferedImage(temp.getWidth(), temp.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = sprite.createGraphics();
            g.rotate(Math.toRadians(180) , temp.getWidth()/2 , temp.getHeight()/2);
            g.drawImage(temp , null , 0 , 0);
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
        public void draw(Graphics g , ImageObserver observer){
            g.drawImage(sprite, Math.round(position.x), Math.round(position.y), SPRITE_WIDTH,SPRITE_HEIGHT,observer);
        }
        
        @Override
        public short getType(){
            return CollisionObject.RECTANGLE;
        }

        @Override
        public short getIdentity(){
            return CollisionObject.ENEMY_SHOOTER_BOSS;
        }
    }

    public static class Dasher extends EnemyEntityShooterBasic{
        private static BufferedImage sprite;
        private BufferedImage rotatedImage;
        public Vector2 velocity;
        public boolean isActive;
        private static float COOLDOWN = 1.2f;
        private float CD = COOLDOWN;

        private static final int HITBOX_X_OFFSET = 6;
        private static final int HITBOX_Y_OFFSET = 22;
        private static final int HITBOX_RADIUS = 50;

        public static final int SPRITE_WIDTH = 100;
        public static final int SPRITE_HEIGHT = 100;

        public Dasher(float x , float y){
            position = new Vector2(x,y);
            velocity = new Vector2(1,1);
            HP = 30000;
            hitbox = new HitboxCircular(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET, HITBOX_RADIUS);
            rotatedImage = new BufferedImage(sprite.getWidth(), sprite.getHeight(), sprite.getType());
        }

        public static void loadSprite() throws IOException{
            BufferedImage temp = ImageIO.read(EnemyEntityBasic.class.getResource("rsc/Enemies/EnemyBossDasher.png"));
            sprite = new BufferedImage(temp.getWidth(), temp.getHeight(),BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = sprite.createGraphics();
            g.rotate(Math.toRadians(180) , temp.getWidth()/2 , temp.getHeight()/2);
            g.drawImage(temp , null , 0 , 0);
        }

        public static void unload(){
            sprite = null;
        }

        @Override
        public void update(float dt){
            // move(dt);
            // shoot(dt);
            velocity.rotate(dt);
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
            return CollisionObject.ENEMY_DASHER_BOSS;
        }
    }

    public EnemyEntityShooterFinalBoss(){
        position = new Vector2(-100, -100);
        velocity = new Vector2(0 , 0);
        acceleration = new Vector2(0, 0);
        HP = 100000;

        shooter = new Shooter(-100, -100);
        summoner = new Summoner(-100, -100);
        dasher = new Dasher(-100, -100);

        hitbox = null;
    }

    public static void loadSprite() throws IOException{
        Summoner.loadSprite();
        Shooter.loadSprite();
        Dasher.loadSprite();
    }

    public static void unload(){
        Summoner.unload();
        Shooter.unload();
        Dasher.unload();
    }

    @Override
    public void update(float dt){
        move(dt);
        shoot(dt);
    }

    @Override
    protected void move(float dt){
    }

    protected void shoot(float dt){
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
        shooter.draw(g, observer);
        summoner.draw(g, observer);
        dasher.draw(g, observer);
    }

    @Override
    public short getType(){
        return -1;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_BIG_BOSS;
    }
}
