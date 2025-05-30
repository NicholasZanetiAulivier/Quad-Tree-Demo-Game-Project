package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.EnemyEntityShooterBomb;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public EnemyEntityShooterBomb[] curr;

    @Override
    public void switchScene() throws Exception{
        super.switchScene();
    }

    @SuppressWarnings("all")
    @Override
    public void loadScene() throws Exception{
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(0,
            (g) ->{
                for(EnemyEntityShooterBomb i : curr){
                    i.draw(g, Global.CANVAS[0]);
                    i.getHitbox().draw(g,Global.CANVAS[0]);
                }
            }
        );

        //Load Classes
        EnemyEntityShooterBomb.loadSprite();

        //Load Objects
        this.curr = new EnemyEntityShooterBomb[1];
        try{
            curr[0] = new EnemyEntityShooterBomb(300,200);
        } catch(Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
