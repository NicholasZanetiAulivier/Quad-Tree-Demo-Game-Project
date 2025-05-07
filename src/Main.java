import com.screen.*;

public class Main {
    static int width = 500;
    static int height = 500;

    public static void main(String[] args) throws Exception {
        MainWindow window = new MainWindow(width,height,"Swing Window Name");
        System.out.println(window);
    }
}
