
import com.Game.Engine.GameEnv;
import com.Game.Engine.Global;

public class Main {

    public static void main(String[] args) {
        try{
            new GameEnv(Global.originalWidth, Global.originalHeight, "Draconic Airforce: Training Regiment",8);
            // Global.Game.switchScene();
            Global.MainMenu.switchScene();
            long c = System.nanoTime();
            while(System.nanoTime() <= c+1_000_000_000)continue;
            Global.GAME_ENVIRONMENT.run();
        } catch(Exception e){
            e.printStackTrace();
            Global.MAIN_WINDOW.dispose();
            return;
        }
    }
}
