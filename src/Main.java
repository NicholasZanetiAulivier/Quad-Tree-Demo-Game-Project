import com.Game.Engine.GameEnv;

public class Main {
    static int width = 500;
    static int height = 500;

    public static void main(String[] args) throws Exception {
        GameEnv game = GameEnv.init(width, height, "Swingtest");
        game.run();
    }
}
