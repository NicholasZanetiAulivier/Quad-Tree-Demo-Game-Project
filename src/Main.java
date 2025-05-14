import com.Game.Engine.GameEnv;
import com.Game.Engine.Global;
import com.Game.Objects.*;

public class Main {

    public static void main(String[] args) throws Exception {
        BasicSprite.loadSprite("rsc/spriteTest.jpg");
        GameEnv.init(Global.originalWidth, Global.originalHeight, "Swingtest");
        Global.MainMenu.switchScene();
        Global.GAME_ENVIRONMENT.run();
    }
}
