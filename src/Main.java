
import com.Game.Engine.GameEnv;
import com.Game.Engine.Global;

public class Main {

    public static void main(String[] args) {
        try{
            new GameEnv(Global.originalWidth, Global.originalHeight, "Swingtest",7);
            Global.GAME_ENVIRONMENT.run();
        } catch(Exception e){
            e.printStackTrace();
            Global.MAIN_WINDOW.dispose();
            return;
        }
    }
}
