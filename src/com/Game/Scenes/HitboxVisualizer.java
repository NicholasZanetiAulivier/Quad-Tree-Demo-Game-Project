package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.EnemyBulletBasic;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public EnemyBulletBasic[] curr1;

    @Override
    public void switchScene() throws Exception{
        super.switchScene();
    }

    @Override
    public void loadScene() throws Exception{
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(0,
            (g) ->{
                for(EnemyBulletBasic i : curr1){
                    i.draw(g, Global.CANVAS[0]);
                    i.getHitbox().draw(g,Global.CANVAS[0]);
                }
            }
        );

        //Load Classes
        EnemyBulletBasic.loadSprite();

        //Load Objects
        this.curr1 = new EnemyBulletBasic[8];
        try{
            this.curr1[7] = new EnemyBulletBasic(600,300 , 1 , 1);
            this.curr1[1] = new EnemyBulletBasic(600,300 , -1 , 1);
            this.curr1[0] = new EnemyBulletBasic(600,300 , -1 , -1);
            this.curr1[2] = new EnemyBulletBasic(600,300 , 1 , -1);
            this.curr1[3] = new EnemyBulletBasic(600,300 , 0 , 1);
            this.curr1[4] = new EnemyBulletBasic(600,300 , 1 , 0);
            this.curr1[5] = new EnemyBulletBasic(600,300 , 0 , -1);
            this.curr1[6] = new EnemyBulletBasic(600,300 , -1 , 0);
        } catch(Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
