package com.Game.Scenes;

import com.Game.Objects.CreditsButton;
import com.Game.Objects.PlayButton;
import com.Game.Engine.Global;

public class MainMenu extends Scene {
    public PlayButton playButton;
    public CreditsButton creditsButton;

    @SuppressWarnings("all")
    public void loadScene() throws Exception{
        playButton = new PlayButton((Global.realWidth-playButton.WIDTH)*.5f, 500);
        creditsButton = new CreditsButton((Global.realWidth-creditsButton.WIDTH)*.5f, 400);

        Global.BACKGROUND_CANVAS.setDrawFunction(
            (g) ->{
            }
        );

        Global.CANVAS[0].setDrawFunction(
            (g) -> {
                creditsButton.draw(g , Global.CANVAS[0]);
                playButton.draw(g , Global.CANVAS[0]);
            }
        );


        //Load classes
        PlayButton.loadSprite();
        CreditsButton.loadSprite();
    }

    @SuppressWarnings("all")
    public void unloadScene() throws Exception{

        //unload classes
        PlayButton.unload();
        CreditsButton.unload();
    }
}
