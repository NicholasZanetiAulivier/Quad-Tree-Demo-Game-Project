package com.Game.Scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import com.Game.Objects.PlayButton;
import com.Game.Engine.Global;

public class MainMenu extends Scene {
    public PlayButton playButton;
    public BufferedImage background;

    @SuppressWarnings("all")
    public void loadScene() throws Exception{
        playButton = new PlayButton((Global.realWidth-playButton.WIDTH)*.5f, 500);
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
            }
        );

        Global.BACKGROUND_CANVAS.setDrawFunction(
            (g) ->{
                g.drawImage(background, 0, 0,Global.realWidth , Global.realHeight, null);
            }
        );

        Global.CANVAS[0].setDrawFunction(
            (g) -> {
                playButton.draw(g , Global.CANVAS[0]);
                playButton.hovering = false;

                g.setColor(new Color(.5f,.5f,.5f,.5f));
                ((Graphics2D)g).fillRect(100, 150, 600, 300);
                g.setColor(Color.BLACK);
                ((Graphics2D)g).drawRect(100, 150, 600, 300);
                ((Graphics2D)g).setFont(new Font("Arial", Font.BOLD, 30));
                ((Graphics2D)g).drawString("'r' - Restart", 150, 200);
                ((Graphics2D)g).drawString("'z' - Shoot", 150, 250);
                ((Graphics2D)g).drawString("'Shift' - Focus", 150, 300);
                ((Graphics2D)g).drawString("'Esc/p' - Pause", 150, 350);
                ((Graphics2D)g).drawString("'F3' - Debug", 150, 400);
            }
        );


        //Load classes
        PlayButton.loadSprite();
    }

    @SuppressWarnings("all")
    public void unloadScene() throws Exception{
        Global.BACKGROUND_CANVAS.setDrawFunction((g)->{});
        Global.CANVAS[0].setDrawFunction((g)->{});
        Global.MOUSE.setMouseReleasedFunction((m)->{});
        Global.GAME_ENVIRONMENT.setUpdateFunction((dt)->{});
        
        //unload classes
        PlayButton.unload();

        //Unload objects
        playButton = null;
    }
}
