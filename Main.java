//Main drawing and calculating class
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

public class Main {
    public static int fps = 60;//fps
    public static int chps = 5;//changes or iterations per second
    public static int defaultChps = chps;

    public static int speed = 10;//canvas moving speed
    public static double scale = 1;//canvas scale

    public static double xPlus, yPlus;//for canvas moving

    public static double concentration = 1d / 3d; //black dots count / white dots count

    public static int DotSize = 10;//default dot size

    public static int black, white;//for actual concentration calculations

    public static Dot[][] dots = new Dot[200][200]; //our dots array
    //keyboard, mouse, and our frame
    public static Keyboard keyboard = new Keyboard();
    JFrame frame;
    Mouse mouse = new Mouse();

    public Main() {
        System.out.println("Generating dots");
        Main.fillDots();//generating dots

        //for Dot.setNeighbours
        Dot[][] dots1 = new Dot[dots.length + 2][dots[0].length + 2];
        for (int i = 0; i < dots1.length; i++) {
            Arrays.fill(dots1[i], null);
        }
        for (int i = 1; i < dots1.length - 1; i++) {
            for (int j = 1; j < dots1[i].length - 1; j++) {
                dots1[i][j] = dots[i - 1][j - 1];
            }
        }
        Dot.setNeighbours(dots1);
    }

    public static void fillDots() {
        //black and white dots variables
        black = 0;
        white = 0;

        for (int i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[i].length; j++) {
                //dots[i][j] = new Dot(DotSize * i, DotSize * j, false); - empty dots array

                double color = Math.random();//painted or not
                if (color >= concentration) {
                    //painted dot
                    dots[i][j] = new Dot(DotSize * i, DotSize * j, false);
                    white++;
                } else {
                    //empty dot
                    dots[i][j] = new Dot(DotSize * i, DotSize * j, true);
                    black++;
                }
            }
        }
        //printing statistics
        System.out.println("Total black dots: " + black);
        System.out.println("Total white dots: " + white);
        System.out.println("Black to white is " + ((double) black / (double) white));
    }

    public void startDrawing(JFrame frame) throws InterruptedException {
        //all drawing here

        this.frame = frame;//initialising our frame

        //key listeners
        frame.addKeyListener(keyboard);
        frame.addMouseWheelListener(mouse);
        frame.addMouseListener(mouse);
        frame.addMouseMotionListener(mouse);
        //buffer strategy to reduce lags
        frame.createBufferStrategy(2);
        //creating a grid from dots
        //makes your picture more pretty
        DotSize -= 5 * scale;

        //the second thread for calculations
        //makes chps intependent from fps
        CalculationDotThread myCalculationThread = new CalculationDotThread();
        Thread calculationThread = new Thread(myCalculationThread);
        calculationThread.start();


        long frameLength = 1000 / fps;//fps initialisation

        Dot xDot = dots[dots.length - 1][dots[0].length - 1];//just a helping dot for controllers

        System.out.println("main while true begin");

        //drawing cycle
        while (true) {
            long start = System.currentTimeMillis();//start millis

            BufferStrategy bs = frame.getBufferStrategy();//buffer strategy to reduce lag
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();//graphicks

            g.clearRect(0, 0, frame.getWidth(), frame.getHeight());//clearing the screen every frame

            this.draw(g);//the main drawing method

            bs.show();//showing one of our buffers

            g.dispose();//garbage delete

            long end = System.currentTimeMillis();//end millis
            long len = end - start;//frame length calculated

            //fps stabilization (to synchronize calculation and main thread)
            if (len < frameLength) {
                Thread.sleep(frameLength - len);
            }
            //program exiting key
            if (keyboard.getQ()) {
                System.out.println("Выход");
                System.exit(20);
            }

            //control keys for chps
            if (keyboard.getUp()) {
                chps++;
            }

            if (keyboard.getDown()) {
                if (chps > 0)
                    chps--;
            }

            //control keys for moving with bounds

            if (keyboard.getD()) {
                if (xDot.x + xPlus > Display.w)
                    xPlus -= speed / scale;
            }
            if (keyboard.getA()) {
                if (dots[0][0].x + xPlus < 0)
                    xPlus += speed / scale;
            }
            if (keyboard.getW()) {
                if (dots[0][0].y - yPlus > 0) {
                    yPlus += speed / scale;
                }
            }
            if (keyboard.getS()) {
                if (xDot.y + yPlus > Display.h)
                    yPlus -= speed / scale;
            }

            //updating keys
            keyboard.update();
        }
    }

    //drawing function
    public void draw(Graphics g) {
        for (int i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[i].length; j++) {
                //some optimization
                if (dots[i][j].x + xPlus < Display.w + 50 * scale && dots[i][j].x + xPlus > -50 * scale) {
                    if (dots[i][j].y + yPlus < Display.h + 50 * scale && dots[i][j].y + yPlus > -50 * scale) {
                        //drawing dots
                        if (dots[i][j].painted) {
                            dots[i][j].draw(g, Color.BLACK);
                        } else {
                            dots[i][j].draw(g);
                        }
                    }
                }
            }
        }
        //test for chps
        g.setColor(Color.red);
        g.setFont(new Font("Calibri", Font.BOLD, 26));
        g.drawString(chps + " chps", 100, 100);
        g.setColor(Color.black);
    }

    //the second thread for calculations
    static class CalculationDotThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    //if we update dots
                    if (Main.chps != 0) {
                        long frameLength = 1000 / Main.chps;//dynamic chps update
                        //the same chps optimisation as fps from Main.startDrawing()
                        long start = System.currentTimeMillis();
                        long end = System.currentTimeMillis();
                        long len = end - start;
                        //updating dots
                        Main.dots = Dot.update(Main.dots);
                        //setting dot's neighbours
                        Dot.setNeighbours(Main.dots);

                        if (len < frameLength) {
                            Thread.sleep(frameLength - len);
                        }
                    } else {
                        //waiting for updates
                        while (Main.chps == 0) {
                            Thread.sleep(1);
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}