import com.Game.Audio.Sound;
import com.Game.Audio.SoundEffects;

public class Test {
    public static void main(String[] args) throws Exception{
        Sound t = new SoundEffects("sfx/Destroyed.wav" , 16);
        long s = System.nanoTime();
        while(true){
            while(System.nanoTime() - s < 250000000) continue;
            t.play();
            s = System.nanoTime();
            System.out.println(Runtime.getRuntime().totalMemory()/(1024*1024));
            System.out.println(Runtime.getRuntime().maxMemory()/(1024*1024));
            System.out.println(Runtime.getRuntime().freeMemory()/(1024*1024));
        }
    }
}
