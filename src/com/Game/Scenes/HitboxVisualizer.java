package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.Item;
import com.Game.Objects.Item_10;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public Item_10 curr1;

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
                curr1.draw(g, Global.CANVAS[0]);
                curr1.getHitbox().draw(g,Global.CANVAS[0]);
            }
        );

        //Load Classes
        Item_10.loadSprite();

        //Load Objects
        try{
            this.curr1 = new Item_10(600,300);
        } catch(Throwable e){
            e.printStackTrace();
        }
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
