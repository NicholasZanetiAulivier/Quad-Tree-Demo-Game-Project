package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.PlayerBulletBouncing;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public PlayerBulletBouncing[] curr;

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
                for(PlayerBulletBouncing i : curr){
                    i.draw(g, Global.CANVAS[0]);
                    i.getHitbox().draw(g,Global.CANVAS[0]);
                }
            }
        );

        //Load Classes
        PlayerBulletBouncing.loadSprite();

        //Load Objects
        this.curr = new PlayerBulletBouncing[1];
        try{
            curr[0] = new PlayerBulletBouncing(300, 200, 1,1 );
        } catch(Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
