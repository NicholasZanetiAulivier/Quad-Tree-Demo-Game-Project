package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.EnemyBulletSpread;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public EnemyBulletSpread[] curr;

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
                for(EnemyBulletSpread i : curr){
                    i.draw(g, Global.CANVAS[0]);
                    i.getHitbox().draw(g,Global.CANVAS[0]);
                }
            }
        );

        //Load Classes
        EnemyBulletSpread.loadSprite();

        //Load Objects
        this.curr = new EnemyBulletSpread[1];
        try{
            curr[0] = new EnemyBulletSpread(300,200,1,1);
        } catch(Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
