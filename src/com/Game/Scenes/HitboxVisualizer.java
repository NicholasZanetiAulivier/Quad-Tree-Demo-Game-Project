package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.EnemyEntityShooterFinalBoss;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public EnemyEntityShooterFinalBoss.Dasher currD;
    public EnemyEntityShooterFinalBoss.Summoner currS;
    public EnemyEntityShooterFinalBoss.Shooter currSh;

    @Override
    public void switchScene() throws Exception{
        super.switchScene();
    }

    @SuppressWarnings("all")
    @Override
    public void loadScene() throws Exception{
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
                currD.update(dt);

            }
        );

        Global.CANVAS[0].setDrawFunction(
            (g) ->{
                currD.draw(g,Global.CANVAS[0]);
                currD.getHitbox().draw(g,Global.CANVAS[0]);
                currS.draw(g,Global.CANVAS[0]);
                currS.getHitbox().draw(g,Global.CANVAS[0]);
                currSh.draw(g,Global.CANVAS[0]);
                currSh.getHitbox().draw(g,Global.CANVAS[0]);
            }
        );

        //Load Classes
        EnemyEntityShooterFinalBoss.loadSprite();

        //Load Objects
        currSh = new EnemyEntityShooterFinalBoss.Shooter(10, 10);
        currD = new EnemyEntityShooterFinalBoss.Dasher(200, 200);
        currS = new EnemyEntityShooterFinalBoss.Summoner(200, 400);
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
