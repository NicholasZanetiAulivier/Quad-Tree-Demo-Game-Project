import com.Game.Engine.GameEnv;
import com.Game.Engine.Global;

public class Test {
    public static void main(String[] args) throws Exception{
        try{
            new GameEnv(Global.originalWidth, Global.originalHeight, "Swingtest");
            Global.Visualizer.switchScene();
            Global.GAME_ENVIRONMENT.run();
        } catch(Exception e){
            e.printStackTrace();
            Global.MAIN_WINDOW.dispose();
            return;
        }
    }
}
