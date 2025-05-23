
import com.Game.Engine.GameEnv;
import com.Game.Engine.Global;

public class Main {

    public static void main(String[] args) {
        try{
            new GameEnv(Global.originalWidth, Global.originalHeight, "Swingtest");
            Global.GAME_ENVIRONMENT.run();
        } catch(Exception e){
            System.out.println(e);
            Global.MAIN_WINDOW.dispose();
            return;
        }
    }
}
