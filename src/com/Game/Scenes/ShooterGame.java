package com.Game.Scenes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.Color;
import javax.imageio.ImageIO;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.GameQuadTree;
import com.Game.Engine.Global;
import com.Game.Objects.CollisionObject;
import com.Game.Objects.EnemyBullet;
import com.Game.Objects.EnemyBulletAccelerating;
import com.Game.Objects.EnemyBulletBasic;
import com.Game.Objects.EnemyBulletExploding;
import com.Game.Objects.EnemyBulletSpread;
import com.Game.Objects.EnemyEntityBasic;
import com.Game.Objects.EnemyEntityHoming;
import com.Game.Objects.EnemyEntityShooterBasic;
import com.Game.Objects.EnemyEntityShooterBomb;
import com.Game.Objects.EnemyEntityShooterSpread;
import com.Game.Objects.EnemyEntityShooterStrafe;
import com.Game.Objects.PlayerBulletBasic;
import com.Game.Objects.PlayerBulletBouncing;
import com.Game.Objects.PlayerCharacter;
import com.Game.Objects.PlayerBullet;
import com.DataStruct.Denode;
import com.Game.Objects.Item;
import com.Game.Objects.Item_10;

/*
 * Needs at least 4 layers of canvas
 */

public class ShooterGame extends Scene{
    public Image[] backgrounds;

    public PlayerCharacter player;
    public DoublyLinkedList<PlayerBullet> friendlyBullets;
    public DoublyLinkedList<EnemyEntityBasic> enemyShips;
    public DoublyLinkedList<EnemyBullet>[] enemyBullets;
    public DoublyLinkedList<Item> items;

    public GameQuadTree partition ;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;

    public int points=0;

    public boolean debug = false;

    public boolean retry = false;
    public float timeCooldown = 1f;
    public int wave = 2;
    public int phase = 0;
    public float survivedFor = 0;
    public float deathCD = 2f;
    public int lives = 2;
    public int bufferCounter = 0;
    public float backgroundYLevel = 0;
    static final float backGroundScrollSpeed = 100;
    static int mapHeight;
    static int mapWidth;

    @SuppressWarnings("all")
    public void loadScene() throws Exception{
        //TODO: Make a loadScene function

        backgrounds = new Image[2];
        backgrounds[0] = ImageIO.read(ShooterGame.class.getResource("background/map.png"));
        backgrounds[0] = backgrounds[0].getScaledInstance(Global.originalWidth,((BufferedImage)backgrounds[0]).getHeight()*2 , Image.SCALE_DEFAULT);
        mapHeight = backgrounds[0].getHeight(null);
        mapWidth = backgrounds[0].getWidth(null);
        backgrounds[1] = ImageIO.read(ShooterGame.class.getResource("background/cloudBackground.jpg"));

        //Load Classes
        PlayerCharacter.loadSprite();
        PlayerBulletBasic.loadSprite();
        PlayerBulletBouncing.loadSprite();
        EnemyEntityBasic.loadSprite();
        EnemyEntityHoming.loadSprite();
        EnemyEntityShooterBasic.loadSprite();
        EnemyEntityShooterSpread.loadSprite();
        EnemyEntityShooterStrafe.loadSprite();
        EnemyEntityShooterBomb.loadSprite();
        EnemyBulletBasic.loadSprite();
        EnemyBulletSpread.loadSprite();
        EnemyBulletAccelerating.loadSprite();
        EnemyBulletExploding.loadSprite();
        Item_10.loadSprite();

        //Load objects
        player = new PlayerCharacter();
        friendlyBullets = new DoublyLinkedList<>();
        enemyShips = new DoublyLinkedList<>();
        enemyBullets = (DoublyLinkedList<EnemyBullet>[])(new DoublyLinkedList[4]);
        for(int i = 0 ; i < 4 ; i++){
            enemyBullets[i] = new DoublyLinkedList<>();
        }
        items = new DoublyLinkedList<>();

        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
                if(player.dead && lives > 0){
                    deathCD -= dt;
                    if(deathCD <= 0){
                        player = new PlayerCharacter();
                        deathCD = 2f;
                        for(int i = 0 ; i < 4 ; i++)
                            enemyBullets[i] = new DoublyLinkedList<>();
                        lives--;
                    }
                }

                backgroundYLevel += dt * backGroundScrollSpeed;
                if(backgroundYLevel >= 0) backgroundYLevel -= mapHeight;

                if (retry){
                    friendlyBullets = new DoublyLinkedList<>();
                    enemyShips = new DoublyLinkedList<>();
                    items = new DoublyLinkedList<>();
                    for(int i = 0 ; i < 4 ; i++)
                        enemyBullets[i] = new DoublyLinkedList<>();
                    player = new PlayerCharacter();

                    points = 0;
                    timeCooldown = 1f;
                    lives = 2;
                    deathCD = 2f;
                    survivedFor = 0;
                    wave = 0;
                    phase = 0;
                    retry = false;
                    return;
                }

                Denode<PlayerBullet> friendlyBullet;
                Denode<EnemyEntityBasic> enemy;
                Denode<EnemyBullet> enemyBullet;
                Denode<Item> item;
                
                /*
                 * Collision Detection (QuadTree)
                 */

                partition = new GameQuadTree(0, 0, Global.realWidth, Global.realHeight,1);
                
                //Insert player bullet ke QuadTree
                friendlyBullet = friendlyBullets.getHead();
                while(friendlyBullet != null){
                    partition.insert((CollisionObject)friendlyBullet.getData());
                    friendlyBullet = friendlyBullet.getNext();
                }
                

                //Collision Detect enemy-player bullet
                enemy = enemyShips.getHead();
                int checks = 0;
                
                /*
                 * THIS IS THE QUADTREE IMPLEMENTATION
                 */

                while(enemy != null){
                    CollisionObject e = (CollisionObject)enemy.getData();
                    Denode<CollisionObject> pBullet = partition.retrieve(e).getHead();
                    while(pBullet != null){
                        CollisionObject p = pBullet.getData();
                        e.checkCollision(p);
                        pBullet = pBullet.getNext();
                        checks++; 
                    }
                    enemy = enemy.getNext();
                }

                /*
                 * END QUADTREE IMPLEMENTATION
                 */

                /*
                * BRUTEFORCE IMPLEMENTATION
                */

                // while (enemy != null){
                //     CollisionObject e = (CollisionObject)enemy.getData();
                //     Denode<PlayerBullet> pBullet = friendlyBullets.getHead();
                //     while(pBullet != null){
                //         CollisionObject p = (CollisionObject)pBullet.getData();
                //         e.checkCollision(p);
                //         pBullet = pBullet.getNext();
                //         checks++;
                //     }
                //     enemy = enemy.getNext();
                // }
                
                /*
                * END BRUITEFORCE IMPLEMENTATION
                */

                // System.out.println(checks);


                //Collision detect player-enemyBullet
                if(!(player.dead || player.autoMoving) && (player.invincibleCD -= dt) < 0){
                    for (int i = 0 ; i < 4 ; i++){
                        enemyBullet = enemyBullets[i].getHead();
                        while(enemyBullet != null){

                            CollisionObject p = enemyBullet.getData();
                            player.checkCollision(p);
                            enemyBullet = enemyBullet.getNext();
                        }
                    }
                    //Collision detect player-enemy
                    enemy = enemyShips.getHead();
                    while(enemy != null){
                        CollisionObject p = enemy.getData();
                        player.checkCollision(p);
                        enemy = enemy.getNext();
                    }

                    //Collision detect player-item
                    item = items.getHead();
                    while(item != null){
                        CollisionObject p = item.getData();
                        player.checkCollision(p);
                        item = item.getNext();
                    }
                }

                
                 /*
                 * Delete dead objects
                 */

                friendlyBullet = friendlyBullets.getHead();
                while(friendlyBullet != null){
                    if (friendlyBullet.getData().shouldDestroy){
                        Denode<PlayerBullet> temp = friendlyBullet.getNext();
                        friendlyBullets.detachDenode(friendlyBullet);
                        friendlyBullet = temp;
                    } else {
                        friendlyBullet = friendlyBullet.getNext();
                    }
                }

                item = items.getHead();
                while(item != null){
                    if (item.getData().shouldDestroy){
                        Denode<Item> temp = item.getNext();
                        items.detachDenode(item);
                        item = temp;
                    } else {
                        item = item.getNext();
                    }
                }

                enemy = enemyShips.getHead();
                while(enemy != null){
                    if (enemy.getData().shouldDestroy){
                        Denode<EnemyEntityBasic> temp = enemy.getNext();
                        enemyShips.detachDenode(enemy);
                        enemy = temp;
                    } else {
                        enemy = enemy.getNext();
                    }
                }

                for(int i = 0 ; i < 4 ; i++){
                    enemyBullet = enemyBullets[i].getHead();
                    while(enemyBullet != null){
                        if(enemyBullet.getData().shouldDestroy){
                            Denode<EnemyBullet> temp = enemyBullet.getNext();
                            enemyBullets[i].detachDenode(enemyBullet);
                            enemyBullet = temp;
                        } else{
                            enemyBullet = enemyBullet.getNext();
                        }
                    }
                }

                /*
                 * Update game objects
                 */

                //At certain times, randomly call a new wave
                timeCooldown -= dt;
                survivedFor += dt;
                if(timeCooldown <= 0) runRandomScriptedEvent();


                //  Update Player
                player.update(dt);

                //  Update friendlyBullets
                friendlyBullet = friendlyBullets.getHead();
                while(friendlyBullet != null){
                    friendlyBullet.getData().update(dt);
                    friendlyBullet = friendlyBullet.getNext();
                }

                //  Update enemyShips
                enemy = enemyShips.getHead();
                while(enemy != null){
                    enemy.getData().update(dt);
                    enemy = enemy.getNext();
                }

                for(int i = 0 ; i < 4 ; i++){
                    enemyBullet = enemyBullets[i].getHead();
                    while(enemyBullet != null){
                        enemyBullet.getData().update(dt);
                        enemyBullet = enemyBullet.getNext();
                    }
                }

                item = items.getHead();
                while(item != null){
                    item.getData().update(dt);
                    item = item.getNext();
                }
            }
        );

        Global.DEBUG_CANVAS.setDrawFunction( 
            (g) ->{
                if(debug){
                    graphicsEnhance(g);
                    for(DoublyLinkedList<EnemyBullet> e : enemyBullets){
                        Denode<EnemyBullet> eNode = e.getHead();
                        while(eNode != null){
                            eNode.getData().getHitbox().draw(g,Global.DEBUG_CANVAS);
                            eNode = eNode.getNext();
                        }
                    }
                    Denode<PlayerBullet> p = friendlyBullets.getHead();
                    while(p != null){
                        p.getData().getHitbox().draw(g , Global.DEBUG_CANVAS);
                        p = p.getNext();
                    }
                    Denode<EnemyEntityBasic> e = enemyShips.getHead();
                    while(e != null){
                        e.getData().getHitbox().draw(g , Global.DEBUG_CANVAS);
                        e = e.getNext();
                    }
                    Denode<Item> i = items.getHead();
                    while(i != null){
                        i.getData().getHitbox().draw(g , Global.DEBUG_CANVAS);
                        i = i.getNext();
                    }
                    player.getHitbox().draw(g,Global.DEBUG_CANVAS);
                    partition.draw(g);
                }
            }
        );

        Global.BACKGROUND_CANVAS.setDrawFunction(
            (g) ->{
                graphicsEnhance(g);
                if(backgroundYLevel <= -mapHeight+Global.originalHeight )
                    g.drawImage(backgrounds[0], 0, (int)backgroundYLevel+mapHeight, Global.BACKGROUND_CANVAS);
                g.drawImage(backgrounds[0], 0, (int)backgroundYLevel, Global.BACKGROUND_CANVAS);
            }
        );

        Global.CANVAS[0].setDrawFunction(
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyBullet> enemyBullet = enemyBullets[0].getHead();
                while(enemyBullet != null){
                    enemyBullet.getData().draw(g,Global.CANVAS[0]);
                    enemyBullet = enemyBullet.getNext();
                }

                g.drawString(String.valueOf(points), 10, Global.realHeight-10);
            }
        );


        Global.CANVAS[1].setDrawFunction(
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyBullet> enemyBullet = enemyBullets[1].getHead();
                while(enemyBullet != null){
                    enemyBullet.getData().draw(g,Global.CANVAS[1]);
                    enemyBullet = enemyBullet.getNext();
                }
            }
        );

        Global.CANVAS[2].setDrawFunction(
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyBullet> enemyBullet = enemyBullets[2].getHead();
                while(enemyBullet != null){
                    enemyBullet.getData().draw(g,Global.CANVAS[2]);
                    enemyBullet = enemyBullet.getNext();
                }

            }
        );

        Global.CANVAS[3].setDrawFunction(
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyBullet> enemyBullet = enemyBullets[3].getHead();
                while(enemyBullet != null){
                    enemyBullet.getData().draw(g,Global.CANVAS[3]);
                    enemyBullet = enemyBullet.getNext();
                }

            }
        );

        Global.CANVAS[4].setDrawFunction( 
            (g) ->{
                graphicsEnhance(g);
                Denode<PlayerBullet> friendlyBullet = friendlyBullets.getHead();
                while( friendlyBullet != null){
                    friendlyBullet.getData().draw(g , Global.CANVAS[4]);
                    friendlyBullet = friendlyBullet.getNext();
                }
            }
        );

        Global.CANVAS[5].setDrawFunction(
            (g) ->{
                graphicsEnhance(g);
                Denode<EnemyEntityBasic> enemy = enemyShips.getHead();
                while(enemy != null){
                    enemy.getData().draw(g, Global.CANVAS[5]);
                    enemy = enemy.getNext();
                }
            }
        );

        Global.CANVAS[6].setDrawFunction( 
            (g) -> {
                graphicsEnhance(g);
                player.draw(g , Global.CANVAS[6]);
            }
        );

        Global.CANVAS[7].setDrawFunction( 
            (g) ->{
                graphicsEnhance(g);
                Denode<Item> item = items.getHead();
                while(item != null){
                    item.getData().draw(g, Global.CANVAS[7]);
                    item = item.getNext();
                }
            }
        );

        Global.KEYBOARD.setKeyPressFunction(
            (k) ->{
                int n = k.getKeyCode();
                if(n == KeyEvent.VK_UP) up = true;
                if(n == KeyEvent.VK_DOWN) down = true;
                if(n == KeyEvent.VK_LEFT) left = true;
                if(n == KeyEvent.VK_RIGHT) right = true;
                if(n == KeyEvent.VK_SHIFT) player.goSlow();
                if(n == KeyEvent.VK_Z) player.startShooting();
                if(n == KeyEvent.VK_R) retry = true;
                if(n == KeyEvent.VK_F3) debug = !debug;
            }
        );

        Global.KEYBOARD.setKeyReleaseFunction(
            (k) ->{
                int n = k.getKeyCode();
                if(n == KeyEvent.VK_UP) up = false;
                if(n == KeyEvent.VK_DOWN) down = false;
                if(n == KeyEvent.VK_LEFT) left = false;
                if(n == KeyEvent.VK_RIGHT) right = false;
                if(n == KeyEvent.VK_SHIFT) player.goFast();
                if(n == KeyEvent.VK_Z) player.stopShooting();
            }
        );
    }

    public static void graphicsEnhance(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(Global.RH);
        g2d.setColor(new Color(0xFF0000));
    }

    @SuppressWarnings("all")
    public void unloadScene() throws Exception{
        //TODO: Make an unloadScene function

        //Unload Objects
        player = null;
        friendlyBullets = null;
        enemyShips = null;
        enemyBullets = null;
        items = null;

        //Unload classes
        PlayerCharacter.unload();
        PlayerBulletBasic.unload();
        PlayerBulletBouncing.unload();
        EnemyEntityBasic.unload();
        EnemyEntityHoming.unload();
        EnemyEntityShooterBasic.unload();
        EnemyEntityShooterStrafe.unload();
        EnemyEntityShooterBomb.unload();
        EnemyBulletBasic.unload();
        EnemyBulletSpread.unload();
        EnemyEntityShooterSpread.unload();
        EnemyBulletAccelerating.unload();
        EnemyBulletExploding.unload();
        Item_10.unload();

        //Unload draw
        for(int i = 0 ; i < 8 ; i++){
            Global.CANVAS[i].setDrawFunction((g)->{});
        }
        Global.BACKGROUND_CANVAS.setDrawFunction((dt)->{});
        Global.DEBUG_CANVAS.setDrawFunction((dt)->{});
    }

    public void resetListeners(){
        up = down = left = right = false;
    }

    public void spawn(int id , float xLoc, float yLoc , float xDir , float yDir , float xAccel , float yAccel){
        switch(id){
            case CollisionObject.ENEMY_BASIC : {
                enemyShips.append(new EnemyEntityBasic(xLoc , yLoc , xDir , yDir , xAccel , yAccel));
                break;
            }
            case CollisionObject.ENEMY_HOMING : {
                enemyShips.append(new EnemyEntityHoming(xLoc , yLoc));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_BASIC: {
                enemyShips.append(new EnemyEntityShooterBasic(xLoc , yLoc , xDir , yDir , xAccel , yAccel));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_SPREAD :{
                enemyShips.append(new EnemyEntityShooterSpread(xLoc , yLoc , xDir , yDir , xAccel , yAccel));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_STRAFE : {
                enemyShips.append(new EnemyEntityShooterStrafe(xLoc, yLoc, xDir, yDir));
                break;
            }
            case CollisionObject.ENEMY_SHOOTER_BOMB : {
                enemyShips.append(new EnemyEntityShooterBomb(xLoc , yLoc));
                break;
            }
        }
    }

    //Implicit wave and phase variables
    public void runRandomScriptedEvent(){
        //Just hardcode all the events
        switch(wave){

            //Wave 1
            case 0: {
                switch(phase++) {
                    case 0: {
                        timeCooldown = 1f;
                        float spacing = Global.realWidth/5;
                        for (int i = 0 ; i < 5 ; i++){
                            spawn(CollisionObject.ENEMY_BASIC , spacing*i,-64 , (float)Math.random()*100-50 , 200, (float)Math.random()*1000-500 , (float)Math.random()*200-250);
                        }
                        break;
                    }

                    case 1 : case 2: case 3:{
                        timeCooldown = .2f;
                        for (int i = 0 ; i < 6 ; i++){
                            spawn(CollisionObject.ENEMY_BASIC , (float)Math.random()*(Global.realWidth-50),-64 , (float)Math.random()*200-100 , 900, (float)Math.random()*200-100 , -500);
                        }
                        if(points >= 500 || survivedFor > 60){
                            phase = 4;
                            return;
                        }
                        phase = phase % 4;
                        break;
                    }

                    //Desperation Phase
                    case 4:{
                        timeCooldown = .25f;
                        int offset = bufferCounter%2;
                        float xAcc = offset*200 * (offset == 1 ? -1 : 1);
                        float spacing = Global.realWidth/8f;
                        for (int i = 0 ; i < 4 ; i++){
                            spawn(CollisionObject.ENEMY_BASIC, spacing*(2*i+offset), -64, 0, 900, xAcc , -100);
                        }
                        if(bufferCounter++ == 20){
                            wave++;
                            phase = 0;
                            survivedFor = 0;
                            timeCooldown = 3f;
                            System.out.println("Wave 2");
                            player.bulletCount += 1;
                            return;
                        }
                        phase = 4;
                        break;
                    }
                }
                break;
            }

            //Wave 2
            case 1:{
                switch(phase++){
                    case 0: case 3:{
                        timeCooldown = .7f;
                        spawn(CollisionObject.ENEMY_SHOOTER_BASIC , Global.realWidth-80 , -64 , 0 , 300 , 0 , 0);
                        spawn(CollisionObject.ENEMY_SHOOTER_BASIC , 16 , -64 , 0 , 300 , 0 , 0);
                        break;
                    }
                    case 1 :  case 2: case 4:{
                        timeCooldown = .2f;
                        int c = (int)player.position.x;
                        spawn(CollisionObject.ENEMY_BASIC , c , -64 , 0 , 700 , 0 , 100);
                        spawn(CollisionObject.ENEMY_BASIC , c+30 , -64 , 200 , 200 , 0 , 100);
                        spawn(CollisionObject.ENEMY_BASIC , c-30 , -64 , -200 , 200 , 0 , 100);
                        spawn(CollisionObject.ENEMY_BASIC , c+60 , -64 , 200 , 100 , 0 , 100);
                        spawn(CollisionObject.ENEMY_BASIC , c-60 , -64 , -200 , 100 , 0 , 100);
                        spawn(CollisionObject.ENEMY_BASIC , c+120 , -64 , 200 , 50 , 0 , 100);
                        spawn(CollisionObject.ENEMY_BASIC , c-120 , -64 , -200 , 50 , 0 , 100);

                        if(survivedFor > 30 || points >= 1500 ){
                            phase = 5;
                            return;
                        }

                        phase = phase % 5;
                        break;
                    }

                    case 5:{
                        timeCooldown = .2f;
                        spawn(CollisionObject.ENEMY_SHOOTER_BASIC , Global.realWidth-80 , -64 , 0 , 300 , 0 , 0);
                        spawn(CollisionObject.ENEMY_SHOOTER_BASIC , 16 , -64 , 0 , 300 , 0 , 0);
                        if(bufferCounter++ > 5){
                            timeCooldown = .5f;
                            bufferCounter = 0;
                            return;
                        }
                        phase = 5;
                        break;
                    }

                    //Desparation Phase
                    case 6 :{
                        timeCooldown = .1f;
                        bufferCounter += 30;
                        spawn(CollisionObject.ENEMY_SHOOTER_BASIC , bufferCounter , -64 , 0 , 700 , 0 , -1500);
                        if(bufferCounter > 750){
                            phase = 7;
                        } else {
                            phase = 6;
                        }
                        break;
                    }

                    case 7: {
                        timeCooldown = .1f;
                        spawn(CollisionObject.ENEMY_SHOOTER_BASIC , bufferCounter , -64 , 0 , 700 , 0 , -1500);
                        if(bufferCounter < 60){
                            wave++;
                            phase = 0;
                            bufferCounter = 0;
                            timeCooldown = 2f;
                            player.bulletCount++;
                            return;
                        } else {
                            phase = 7;
                        }
                        bufferCounter -= 30;
                        break;
                    }
                }
                break;
            }
            
            //Wave 3: SURVIVE WAVE
            case 2 : {
                switch(phase++){
                    case 0:{
                        timeCooldown = .7f;
                        spawn(CollisionObject.ENEMY_SHOOTER_SPREAD , (Global.originalWidth-EnemyEntityShooterSpread.SPRITE_WIDTH)/2 , -80+(float)Math.random()*30-15 , 0 , 200 , 0 ,0);
                        if(bufferCounter++ == 5) {
                            bufferCounter = 0;
                            return;
                        }
                        else phase = 0;
                        break;
                    }

                    case 1: {
                        timeCooldown = .7f;
                        spawn(CollisionObject.ENEMY_SHOOTER_SPREAD , (Global.originalWidth-EnemyEntityShooterSpread.SPRITE_WIDTH)/2-200 , -80+(float)Math.random()*30-15 , 0 , 200 , 0 ,0);
                        if(bufferCounter++ == 5){
                            bufferCounter = 0;
                            return;
                        }
                        else phase = 1;
                        break;
                    }

                    case 2 : {
                        timeCooldown = .7f;
                        spawn(CollisionObject.ENEMY_SHOOTER_SPREAD , (Global.originalWidth-EnemyEntityShooterSpread.SPRITE_WIDTH)/2+200 , -80+(float)Math.random()*30-15 , 0 , 200 , 0 ,0);
                        if(bufferCounter++ == 5){
                            bufferCounter = 0;
                            return;
                        }
                        else phase = 2;
                        break;
                    }

                    case 3 :{
                        timeCooldown = .2f;
                        spawn(CollisionObject.ENEMY_SHOOTER_SPREAD , (Global.originalWidth-EnemyEntityShooterSpread.SPRITE_WIDTH)/2-200+200*(bufferCounter%3) , -80+(float)Math.random()*30-15 , 0 , 400 , 0 ,0);
                        if(bufferCounter++ == 18){
                            bufferCounter = 0;
                            return;
                        }
                        else phase = 3;
                        break;
                    }
                }
                break;
            }
        }
    }

}
