import com.Game.Engine.GameEnv;

public class Main {
    static int width = 500;
    static int height = 500;

    public static void main(String[] args) throws Exception {
        GameEnv.init(width, height, "Swingtest");
        GameEnv.game.run();
    }
}
