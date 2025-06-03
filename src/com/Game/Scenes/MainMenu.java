package com.Game.Scenes;

import com.Game.Objects.CreditsButton;
import com.Game.Objects.PlayButton;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.Game.Engine.Global;

public class MainMenu extends Scene {
    public PlayButton playButton;
    public CreditsButton creditsButton;
    public BufferedImage background;

    @SuppressWarnings("all")
    public void loadScene() throws Exception{
        playButton = new PlayButton((Global.realWidth-playButton.WIDTH)*.5f, 500);
        creditsButton = new CreditsButton((Global.realWidth-creditsButton.WIDTH)*.5f, 400);
        background = ImageIO.read(MainMenu.class.getResource("background/cloudBackground.jpg"));

        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) ->{
                int mouseX = Global.MOUSE.x;
                int mouseY = Global.MOUSE.y;
                if(mouseX > playButton.position.x
                    && mouseX < playButton.position.x+playButton.WIDTH
                    && mouseY > playButton.position.y
                    && mouseY < playButton.position.y+playButton.HEIGHT
                ) playButton.hovering = true;
                
                if(mouseX > creditsButton.position.x
                    && mouseX < creditsButton.position.x+creditsButton.WIDTH
                    && mouseY > creditsButton.position.y
                    && mouseY < creditsButton.position.y+creditsButton.HEIGHT
                ) creditsButton.hovering = true;

            }
        );

        Global.MOUSE.setMouseReleasedFunction(
            (m) ->{
                int mouseX = m.getX();
                int mouseY = m.getY();
                if(mouseX > playButton.position.x
                    && mouseX < playButton.position.x+playButton.WIDTH
                    && mouseY > playButton.position.y
                    && mouseY < playButton.position.y+playButton.HEIGHT
                )try {
                        Global.Game.switchScene();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                
                if(mouseX > creditsButton.position.x
                    && mouseX < creditsButton.position.x+creditsButton.WIDTH
                    && mouseY > creditsButton.position.y
                    && mouseY < creditsButton.position.y+creditsButton.HEIGHT
                ) try {
                    Global.Visualizer.switchScene();
                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        );

        Global.BACKGROUND_CANVAS.setDrawFunction(
            (g) ->{
                g.drawImage(background, 0, 0,Global.realWidth , Global.realHeight, null);
            }
        );

        Global.CANVAS[0].setDrawFunction(
            (g) -> {
                creditsButton.draw(g , Global.CANVAS[0]);
                playButton.draw(g , Global.CANVAS[0]);
                playButton.hovering = false;
                creditsButton.hovering = false;
            }
        );


        //Load classes
        PlayButton.loadSprite();
        CreditsButton.loadSprite();
    }

    @SuppressWarnings("all")
    public void unloadScene() throws Exception{
        Global.BACKGROUND_CANVAS.setDrawFunction((g)->{});
        Global.CANVAS[0].setDrawFunction((g)->{});
        Global.MOUSE.setMouseReleasedFunction((m)->{});
        Global.GAME_ENVIRONMENT.setUpdateFunction((dt)->{});
        
        //unload classes
        PlayButton.unload();
        CreditsButton.unload();

        //Unload objects
        playButton = null;
        creditsButton = null;
    }
}
