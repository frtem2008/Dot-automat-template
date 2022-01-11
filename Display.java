//main starting program class
import javax.swing.*;

public class Display extends JFrame {
    public static JFrame frame = new JFrame("Platformer");//our frame
    public static int w = 1600;//width
    public static int h = 900;//heigth


    static {
        System.setProperty("sun.java2d.opengl", "True");//enable openGl
    }

    public static void main(String[] args) throws InterruptedException {
        Display.frame.setExtendedState(6);//full screen frame
        frame.setUndecorated(true);//no cross or tray buttons
        Main m = new Main();//dot generation
        frame.setVisible(true);
        m.startDrawing(frame);//starting drawing
    }
}