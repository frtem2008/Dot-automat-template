//key processing class
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private static final boolean[] keys = new boolean[66568];//all key array
    //some keys (use for other projects xD)
    private static boolean tab;
    private static boolean left;
    private static boolean right;
    private static boolean up;
    private static boolean down;
    private static boolean space;
    private static boolean shift;
    private static boolean f11;
    private static boolean s;
    private static boolean e;
    private static boolean r;
    private static boolean f;
    private static boolean q;
    private static boolean w;
    private static boolean d;
    private static boolean a;
    private static boolean c;
    private static boolean v;
    private static boolean g;
    private static boolean x;
    private static boolean z;
    private static boolean b;
    private static boolean n;
    private static boolean m;
    private static boolean u;
    private static boolean ctrl;
    private static boolean h;

    //empty constructor
    public Keyboard() {
    }

    //key getters
    public boolean getB() {
        return b;
    }

    public boolean getN() {
        return n;
    }

    public boolean getM() {
        return m;
    }

    public boolean getU() {
        return u;
    }

    public boolean getH() {
        return h;
    }
    public boolean getA() {
        return a;
    }
    public boolean getD() {
        return d;
    }
    public boolean getW() {
        return w;
    }
    public boolean getX() {
        return x;
    }

    public boolean getV() {
        return v;
    }

    public boolean getC() {
        return c;
    }

    public boolean getQ() {
        return q;
    }

    public boolean getF() {
        return f;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        return right;
    }

    public boolean getUp() {
        return up;
    }
    public boolean getDown() {
        return down;
    }

    public boolean getF11() {
        return f11;
    }

    public boolean getS() {
        return s;
    }

    public boolean getShift() {
        return shift;
    }
    public boolean getCtrl() {
        return ctrl;
    }

    //key updates
    public void update() {
        a = keys[KeyEvent.VK_A];
        d = keys[KeyEvent.VK_D];
        w = keys[KeyEvent.VK_W];
        h = keys[KeyEvent.VK_H];
        tab = keys[KeyEvent.VK_TAB];
        n = keys[KeyEvent.VK_N];
        m = keys[KeyEvent.VK_M];
        u = keys[KeyEvent.VK_U];
        ctrl = keys[KeyEvent.VK_CONTROL];
        b = keys[66];
        z = keys[90];
        up = keys[KeyEvent.VK_UP];
        left= keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];
        down = keys[KeyEvent.VK_DOWN];
        space = keys[32];
        shift = keys[16];
        f11 = keys[122];
        s = keys[83];
        e = keys[69];
        r = keys[82];
        f = keys[70];
        q = keys[81];
        c = keys[67];
        v = keys[86];
        g = keys[71];
        x = keys[88] || keys[127];
    }

    //implemented methods
    public void keyPressed(KeyEvent event) {
        keys[event.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent event) {
        keys[event.getKeyCode()] = false;
    }

    public void keyTyped(KeyEvent event) {
    }
}
