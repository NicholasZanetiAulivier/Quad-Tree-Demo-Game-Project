package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.PlayerBulletBasic;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public PlayerBulletBasic[] curr;

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

        Global.CANVAS[0].setDrawFunction(
            (g) ->{
                for(PlayerBulletBasic i : curr){
                    i.draw(g, Global.CANVAS[0]);
                    i.getHitbox().draw(g,Global.CANVAS[0]);
                }
            }
        );

        //Load Classes
        PlayerBulletBasic.loadSprite();

        //Load Objects
        this.curr = new PlayerBulletBasic[1];
        try{
            curr[0] = new PlayerBulletBasic(300,200 , 0 , 0);
        } catch(Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
