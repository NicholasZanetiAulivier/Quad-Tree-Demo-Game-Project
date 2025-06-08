package com.Game.Objects;

import java.io.IOException;

import java.awt.Color;
import javax.imageio.ImageIO;
import javax.management.MalformedObjectNameException;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import com.DataType.Vector2;
import com.Game.Engine.Global;


public class EnemyEntityShooterFinalBoss extends EnemyEntityBasic{

    public Summoner summoner;
    public Dasher dasher;
    public Shooter shooter;

    public static final float SWITCH_TIMER = 20f;

    public int whoseTurn;
    public float timer = SWITCH_TIMER;
    public EnemyEntityShooterBasic currentActive = null;


    public static class Summoner extends EnemyEntityShooterBasic{
        private static BufferedImage sprite;

        private static final int HITBOX_X_OFFSET = 6;
        private static final int HITBOX_Y_OFFSET = 24;
        private static final int HITBOX_WIDTH = 89;
        private static final int HITBOX_HEIGHT = 48;

        public static final int SPRITE_WIDTH = 100;
        public static final int SPRITE_HEIGHT = 100;

        public static final float GO_BACK_SPEED = 200;
        
        public Vector2 velocity;
        public Vector2 whereToGo;
        public int shootingWhomst = 0;
        public boolean isActive;
        public EnemyEntityShooterFinalBoss parent;
        private float timeCooldown = .5f;
        private boolean shooting = false;

        public Summoner( float x , float y){
            position = new Vector2(x,y);
            velocity = new Vector2(0,0);
            shooting = false;
            isActive = false;
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
            if(shooting)shoot(dt);
        }

        @Override
        public void move(float dt){
            if(isActive){
                if(position.y < 0){
                    position.y += GO_BACK_SPEED * dt;
                }
                if(!shooting){
                    float x = (float)Math.random()*(Global.originalWidth-SPRITE_WIDTH);
                    float y = (float)Math.random()*(50);
                    whereToGo = new Vector2(x,y);
                    velocity = new Vector2(.5f*(x-position.x) , .5f*(y-position.y));
                    shooting = true;
                } else {
                    position.add(Vector2.scale(velocity, dt));
                    if(Vector2.getDistance(whereToGo, position) <= 100){
                        shooting = false;
                    }
                }
            } else{
                shooting = false;
                if(position.y+SPRITE_HEIGHT > 0){
                    position.y -= GO_BACK_SPEED * dt;        
                }
            }
            hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
        }

        @Override
        public void shoot(float dt){
            if(isActive){
                timeCooldown -= dt;
                if(timeCooldown <= 0){
                    cycleAmmo();
                    switch(shootingWhomst){
                        case 0 : case 4 : case 5 : case 6 : case 8 : case 9 : case 7:{
                            Global.Game.spawn(
                                CollisionObject.ENEMY_BASIC,
                                position.x+(SPRITE_WIDTH-EnemyEntityBasic.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityBasic.SPRITE_HEIGHT)/2,
                                (float)Math.random()*(1000)-500,
                                -100,
                                (float)Math.random()*(500)-250,
                                200
                            );
                            Global.Game.spawn(
                                CollisionObject.ENEMY_BASIC,
                                position.x+(SPRITE_WIDTH-EnemyEntityBasic.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityBasic.SPRITE_HEIGHT)/2,
                                (float)Math.random()*(1000)-500,
                                -100,
                                (float)Math.random()*(500)-250,
                                200
                            );
                            Global.Game.spawn(
                                CollisionObject.ENEMY_BASIC,
                                position.x+(SPRITE_WIDTH-EnemyEntityBasic.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityBasic.SPRITE_HEIGHT)/2,
                                (float)Math.random()*(200)-100,
                                -50,
                                (float)Math.random()*(100)-50,
                                200
                            );
                            Global.Game.spawn(
                                CollisionObject.ENEMY_BASIC,
                                position.x+(SPRITE_WIDTH-EnemyEntityBasic.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityBasic.SPRITE_HEIGHT)/2,
                                (float)Math.random()*(200)-100,
                                -50,
                                (float)Math.random()*(100)-50,
                                200
                            );
                            timeCooldown = .1f;
                            break;
                        }
                        case 1 :{
                            Global.Game.spawn(
                                CollisionObject.ENEMY_SHOOTER_STRAFE ,
                                position.x+(SPRITE_WIDTH-EnemyEntityShooterStrafe.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityShooterStrafe.SPRITE_HEIGHT)/2,
                                0,
                                300,
                                0,
                                100
                            );
                            Global.Game.spawn(
                                CollisionObject.ENEMY_SHOOTER_STRAFE ,
                                position.x+(SPRITE_WIDTH-EnemyEntityShooterStrafe.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityShooterStrafe.SPRITE_HEIGHT)/2,
                                200,
                                200,
                                0,
                                100
                            );
                            Global.Game.spawn(
                                CollisionObject.ENEMY_SHOOTER_STRAFE ,
                                position.x+(SPRITE_WIDTH-EnemyEntityShooterStrafe.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityShooterStrafe.SPRITE_HEIGHT)/2,
                                -200,
                                200,
                                0,
                                100
                            );
                            timeCooldown = .5f;
                            break;
                        }
                        case 2 : {
                            Global.Game.spawn(
                                CollisionObject.ENEMY_SHOOTER_BASIC,
                                position.x+(SPRITE_WIDTH-EnemyEntityShooterBasic.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityShooterBasic.SPRITE_HEIGHT)/2,
                                100,
                                50,
                                -20,
                                -5
                            );
                            Global.Game.spawn(
                                CollisionObject.ENEMY_SHOOTER_BASIC,
                                position.x+(SPRITE_WIDTH-EnemyEntityShooterBasic.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityShooterBasic.SPRITE_HEIGHT)/2,
                                -100,
                                50,
                                20,
                                -5
                            );
                            timeCooldown = .5f;
                            break;
                        }
                        case 3 :{
                            Global.Game.spawn(
                                CollisionObject.ENEMY_SHOOTER_SPREAD,
                                position.x+(SPRITE_WIDTH-EnemyEntityShooterSpread.SPRITE_WIDTH)/2,
                                position.y+(SPRITE_HEIGHT-EnemyEntityShooterSpread.SPRITE_HEIGHT)/2,
                                0,
                                300,
                                0,
                                0
                            );
                            timeCooldown = .5f;
                            break;
                        }
                    }
                }
            }
        }

        public void cycleAmmo(){
            shootingWhomst = (int)Math.round(Math.random()*9);
        }

        public void activate(){
            isActive = true;
        }

        public void deactivate(){
            isActive = false;
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
        private static final int HITBOX_Y_OFFSET = 28;
        private static final int HITBOX_WIDTH =89;
        private static final int HITBOX_HEIGHT = 48;

        public static final int SPRITE_WIDTH = 100;
        public static final int SPRITE_HEIGHT = 100;
        
        public EnemyEntityShooterFinalBoss parent ;

        public static final float GO_BACK_SPEED = 200;

        public Vector2 velocity;
        public Vector2 whereToGo ;
        public boolean isActive;
        public int bulletPattern = 0;
        public int bulletPhase = 0;
        public float bufferCounter = 0;
        public float bufferCounter2 = 0;
        private float timeCooldown = .1f;
        public boolean shouldMove = false;


        public Shooter(float x , float y){
            position = new Vector2(x,y);
            velocity = new Vector2(0,0);
            isActive = false;
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
        public void move(float dt){
            if(isActive){
                if(position.y < 0){
                    position.y += GO_BACK_SPEED * dt;
                }
                if(shouldMove){
                    velocity = Vector2.subtract(whereToGo, position);
                    position.add(Vector2.scale(velocity,dt));
                    if(Vector2.getDistance(whereToGo, position) <= 100){
                        shouldMove = false;
                    }
                }
            } else {
                if(position.y + SPRITE_HEIGHT > 0){
                    position.y -= GO_BACK_SPEED * dt;
                }
            }
            hitbox.setPosition(position.x+HITBOX_X_OFFSET, position.y+HITBOX_Y_OFFSET);
        }

        @Override
        public void shoot(float dt){
            if(isActive){
                timeCooldown -= dt;
                if(timeCooldown <= 0){
                    switch (bulletPattern){
                        
                        //BULLET PATTERN 1
                        case 0 :{
                            switch (bulletPhase){
                                case 0 :{
                                    timeCooldown = .5f;
                                    shouldMove = true;
                                    bufferCounter = 0;
                                    if(bufferCounter2 > 0) whereToGo = new Vector2((float)Math.random()*600 , (float)Math.random()*100);
                                    else whereToGo = new Vector2(250,150);
                                    bulletPhase++;
                                    break;
                                }
                                
                                case 1 :{
                                    timeCooldown = .01f;
                                    if((bufferCounter += .2f) <= 20)try{
                                        Global.Game.enemyBullets[Global.counter()].append(
                                            new EnemyBulletBasic(position.x+(SPRITE_WIDTH-EnemyBulletBasic.BULLET_WIDTH)/2,position.y+(SPRITE_HEIGHT-EnemyBulletBasic.BULLET_HEIGHT)/2,(float)Math.sin(bufferCounter) , (float)Math.cos(bufferCounter))
                                        );
                                        if(bufferCounter2 >0){
                                            Global.Game.enemyBullets[Global.counter()].append(
                                                new EnemyBulletBasic(position.x+(SPRITE_WIDTH-EnemyBulletBasic.BULLET_WIDTH)/2,position.y+(SPRITE_HEIGHT-EnemyBulletBasic.BULLET_HEIGHT)/2,(float)Math.sin(Math.PI-bufferCounter) , (float)Math.cos(Math.PI-bufferCounter))
                                            );
                                        }
                                    } catch(Throwable e ){e.printStackTrace();}
                                    else {
                                        bulletPhase++;
                                    }
                                    break;
                                }

                                case 2 : {
                                    timeCooldown = .5f;
                                    shouldMove = true;
                                    bufferCounter = 0;
                                    if(bufferCounter2 > 0) whereToGo = new Vector2((float)Math.random()*600 , (float)Math.random()*100);
                                    else whereToGo = new Vector2(500,200);
                                    bulletPhase++;
                                    break;
                                }

                                case 3 :{
                                    timeCooldown = .01f;
                                    if((bufferCounter -= .2f) >= -20)try{
                                        Global.Game.enemyBullets[Global.counter()].append(
                                            new EnemyBulletBasic(position.x+(SPRITE_WIDTH-EnemyBulletBasic.BULLET_WIDTH)/2,position.y+(SPRITE_HEIGHT-EnemyBulletBasic.BULLET_HEIGHT)/2,(float)Math.sin(bufferCounter) , (float)Math.cos(bufferCounter))
                                        );

                                        if(bufferCounter2 >0){
                                            Global.Game.enemyBullets[Global.counter()].append(
                                                new EnemyBulletBasic(position.x+(SPRITE_WIDTH-EnemyBulletBasic.BULLET_WIDTH)/2,position.y+(SPRITE_HEIGHT-EnemyBulletBasic.BULLET_HEIGHT)/2,(float)Math.sin(Math.PI-bufferCounter) , (float)Math.cos(Math.PI-bufferCounter))
                                            );
                                        }
                                    } catch(Throwable e ){e.printStackTrace();}
                                    else if(bufferCounter2 == 0){
                                        bufferCounter2++;
                                        bulletPhase = 0;
                                    } else {
                                        bulletPattern++;
                                        parent.switchTurns();
                                        bulletPhase = 0;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        public void activate(){
            isActive = true;
            bulletPhase = 0;
            timeCooldown = .5f;
        }

        public void deactivate(){
            isActive = false;
            shouldMove = false;
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

        private static final int HITBOX_X_OFFSET = 25;
        private static final int HITBOX_Y_OFFSET = 25;
        private static final int HITBOX_RADIUS = 24;

        public static final int SPRITE_WIDTH = 100;
        public static final int SPRITE_HEIGHT = 100;

        public EnemyEntityShooterFinalBoss parent;

        public Dasher(float x , float y){
            position = new Vector2(x,y);
            velocity = new Vector2(1,1);
            HP = 10000;
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
            // velocity.rotate(dt);
        }

        public void activate(){
            isActive = true;
        }

        public void deactivate(){
            isActive = false;
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

        shooter = new Shooter(-100, -100);
        shooter.parent = this;
        Global.Game.enemyShips.append(shooter);
        summoner = new Summoner(-100, -100);
        summoner.parent = this;
        Global.Game.enemyShips.append(summoner);
        dasher = new Dasher(-100, -100);
        dasher.parent = this;
        Global.Game.enemyShips.append(dasher);

        whoseTurn = Math.round((float)(Math.random()*3));
        //Temp
        whoseTurn = 2;
        switchTurns();


        hitbox = null;
    }

    public void switchTurns(){
        whoseTurn = ++whoseTurn % 3;
        switch(whoseTurn){
            case 0 : {
                currentActive = shooter;
                shooter.activate();
                summoner.deactivate();
                dasher.deactivate();
                timer = 999f;
                break;
            }

            case 1 : {
                currentActive = summoner;
                summoner.activate();
                shooter.deactivate();
                dasher.deactivate();
                timer = SWITCH_TIMER;
                break;
            }

            case 2 : {
                currentActive = dasher;
                dasher.activate();
                shooter.deactivate();
                summoner.deactivate();
                timer = SWITCH_TIMER;
                break;
            } 
        }
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
        timer -= dt;
        if(timer <= 0){
            switchTurns();
        }
    }

    protected void shoot(float dt){
        if(shooter.shouldDestroy && summoner.shouldDestroy && dasher.shouldDestroy){
            shouldDestroy = true;
        }
    }

    @Override
    public void draw(Graphics g , ImageObserver observer){
    }

    @Override
    public short getType(){
        return -1;
    }

    @Override
    public short getIdentity(){
        return CollisionObject.ENEMY_BIG_BOSS;
    }

    public void checkCollision(CollisionObject o){
        return;
    }
}
