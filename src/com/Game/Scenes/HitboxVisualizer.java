package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.Cloud;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public Cloud[] clouds;

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
                for (Cloud i : clouds){
                    i.draw(g, Global.CANVAS[0]);
                }
            }
        );

        //Load Classes
        Cloud.loadSprite();

        //Load Objects
        clouds = new Cloud[6];

        clouds[0] = new Cloud(0, 0, 0);
        clouds[1] = new Cloud(0, 150, 1);
        clouds[2] = new Cloud(150, 0, 2);
        clouds[3] = new Cloud(150, 150, 3);
        clouds[4] = new Cloud(150, 300, 4);
        clouds[5] = new Cloud(300, 300, 5);
    }

    @Override
    public void unloadScene() throws Exception{
        Cloud.unload();
    }
}
