package com.Game.Scenes;

import com.Game.Engine.Global;
import com.Game.Objects.EnemyEntityHoming;
/*
 * Scene to visualize hitbox
 */

public class HitboxVisualizer extends Scene {
    public EnemyEntityHoming curr;

    @Override
    public void switchScene() throws Exception{
        super.switchScene();
    }

    @Override
    public void loadScene() throws Exception{
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
                curr.update(dt);
            }
        );

        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                curr.draw(g, Global.CANVAS);
                curr.getHitbox().draw(g,Global.CANVAS);
            }
        );

        //Load Classes
        EnemyEntityHoming.loadSprite();

        //Load Objects
        this.curr = new EnemyEntityHoming(600,300);
    }

    @Override
    public void unloadScene() throws Exception{

    }
}
