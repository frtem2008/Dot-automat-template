//Mouse controller class
//don't know what to comment here...
import java.awt.event.*;

public class Mouse extends MouseAdapter implements MouseListener, MouseWheelListener, MouseMotionListener {
    @Override
    public void mouseClicked(MouseEvent e) {
        int button = e.getButton();
        if (button == 2) {
            if (Main.keyboard.getCtrl()) {
                Main.chps = Main.defaultChps;
            } else {
                Main.scale = 1;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int uns = e.getUnitsToScroll();

        if (Main.keyboard.getCtrl()) {
            if (System.getProperty("os.name").startsWith("Mac")) {
                if (Main.chps + uns >= 0)
                    Main.chps += uns;
            } else {
                if (Main.chps - uns >= 0)
                    Main.chps -= uns;
            }
        }else{
            if (Main.scale > 0) {
                if (System.getProperty("os.name").startsWith("Mac")) {
                    if (Main.scale + uns > 0)
                        Main.scale += uns / 10d;
                } else {
                    if (Main.scale - uns > 0)
                        Main.scale -= uns / 10d;
                }
            }
        }
    }
}

