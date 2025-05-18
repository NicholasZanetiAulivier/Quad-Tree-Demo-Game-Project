import com.Game.Audio.BackGroundMusic;
import com.Game.Audio.Sound;
import com.Game.Audio.SoundEffects;

public class Test {
    public static void main(String[] args) throws Exception{
        String[] h = {"sfx/Destroyed.wav","sfx/GameOver.wav","sfx/Press.wav","sfx/Win.wav"};
        Sound t = new BackGroundMusic(h);
        long s = System.nanoTime();
        while(true){
            while(System.nanoTime() - s < 250000000) continue;
            s = System.nanoTime();
            System.out.println(Runtime.getRuntime().totalMemory()/(1024*1024));
            System.out.println(Runtime.getRuntime().maxMemory()/(1024*1024));
            System.out.println(Runtime.getRuntime().freeMemory()/(1024*1024));
        }
    }
}
