import com.Game.Engine.GameEnv;
import com.Game.Engine.Global;
import com.Game.Objects.*;

import java.io.*;

public class Main {
    static int width = 500;
    static int height = 500;

    public static void main(String[] args) throws Exception {
        File s = new File("rsc/spriteTest.jpg");
        System.out.println(s);
        BasicSprite.loadSprite(s);
        GameEnv.init(width, height, "Swingtest");
        Global.MainMenu.switchScene();
        Global.GAME_ENVIRONMENT.run();
    }
}
