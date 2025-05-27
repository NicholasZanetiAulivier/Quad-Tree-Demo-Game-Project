package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.EnemyBulletBasic;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public EnemyBulletBasic curr1;
    public EnemyBulletBasic curr2;
    public EnemyBulletBasic curr3;
    public EnemyBulletBasic curr4;

    @Override
    public void switchScene() throws Exception{
        super.switchScene();
    }

    @Override
    public void loadScene() throws Exception{
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
                curr1.update(dt);
                curr2.update(dt);
                curr3.update(dt);
                curr4.update(dt);
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                curr4.draw(g, Global.CANVAS);
                curr4.getHitbox().draw(g,Global.CANVAS);
                curr3.draw(g, Global.CANVAS);
                curr3.getHitbox().draw(g,Global.CANVAS);
                curr2.draw(g, Global.CANVAS);
                curr2.getHitbox().draw(g,Global.CANVAS);
                curr1.draw(g, Global.CANVAS);
                curr1.getHitbox().draw(g,Global.CANVAS);
            }
        );

        //Load Classes
        EnemyBulletBasic.loadSprite();

        //Load Objects
        this.curr1 = new EnemyBulletBasic(600,300,-1,0);
        this.curr2 = new EnemyBulletBasic(300,300,0,-1);
        this.curr3 = new EnemyBulletBasic(300,100,1,0);
        this.curr4 = new EnemyBulletBasic(100,100,0,1);
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
