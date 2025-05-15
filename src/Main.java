import com.Game.Engine.GameEnv;
import com.Game.Engine.Global;

public class Main {

    public static void main(String[] args) throws Exception {
        new GameEnv(Global.originalWidth, Global.originalHeight, "Swingtest");
        Global.GAME_ENVIRONMENT.run();
    }
}
