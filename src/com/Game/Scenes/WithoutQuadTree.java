package com.Game.Scenes;

import java.awt.Graphics2D;

import com.DataStruct.DoublyLinkedList;
import com.DataStruct.Denode;
import com.Game.Engine.Global;
import com.Game.Objects.ArtificialCircle;
import com.Game.Objects.Drawable;
import com.Game.Objects.Entity;

public class WithoutQuadTree extends Scene{
    public DoublyLinkedList<ArtificialCircle> circles;
   
    @Override
    public void switchScene() throws Exception {
        if(Global.currentScene != null) Global.currentScene.unloadScene();
        this.loadScene();

        //Draw Function
        Global.GAME_ENVIRONMENT.setDrawFunction(
            (g) ->{
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHints(Global.RH);

                Denode<?> item = this.circles.getHead();

                while(item != null){
                    ((Drawable)item.getData()).draw(g2d , Global.CANVAS);
                    g2d.drawString(item.getData().toString(), 0, 100);
                    item = item.getNext();
                }
            }
        );
        
        //Update Function
        Global.GAME_ENVIRONMENT.setUpdateFunction(
            (dt) -> {
<<<<<<< HEAD:src/com/Game/Scenes/WithoutQuadTree.java
                Denode<?> item = this.circles.getHead();
=======
                Denode<?> item = this.objectList.getHead();
            
>>>>>>> main:src/com/Game/Scenes/MainMenu.java
                while(item != null){
                    ((Entity)item.getData()).update(dt);
                    item = item.getNext();
                }
            }
        );
        Global.currentScene = this;
    }

    @Override
    public void loadScene() throws Exception{
        this.circles = new DoublyLinkedList<>();
        this.circles.append(new ArtificialCircle(760, 560, 0, 0, 20));

    }

    @Override
    public void unloadScene() throws Exception{
        this.circles = null;
    }

}
