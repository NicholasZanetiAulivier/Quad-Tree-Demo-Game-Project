package com.Game.Scenes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

import com.Game.Engine.DrawFunc;
import com.Game.Engine.Global;
import com.Game.Engine.Scene;
import com.Game.Engine.UpdateFunc;
import com.Game.Objects.BasicSprite;
import com.Game.Objects.Drawable;

public class MainMenu extends Scene{
    public static boolean s = true;
    
    private static DrawFunc draw = new DrawFunc() {
        @Override
        public void draw(Graphics g , Scene scene){
            Drawable obj = new BasicSprite();

            Graphics2D g2d = (Graphics2D) g;
            obj.draw(g2d , Global.CANVAS);
        }
    };

    private static UpdateFunc update = new UpdateFunc() {  
        @Override
        public void update(long dt , Scene scene){
            if (s) Global.CANVAS.setBackground(new Color(0x000000));
            else Global.CANVAS.setBackground(new Color(0xFFFFFF));
            s = !s;
        }
    };

    @Override
    public void switchScene() {
        Global.GAME_ENVIRONMENT.setDrawFunction(draw);
        Global.GAME_ENVIRONMENT.setUpdateFunction(update);

    }

}
