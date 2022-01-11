//A class for our dots
import java.awt.*;

public class Dot {
    public int x, y;//coordinates
    public boolean painted;//is painted
    public int neighbours;//neighbours variable

    //constructor with 3 args
    public Dot(int x, int y, boolean painted) {
        this.x = x;
        this.y = y;
        this.painted = painted;
    }

    //setting dot's neighbours
    public static void setNeighbours(Dot[][] dots) {
        //all dots always exist because of generating the frame array
        Dot[] temp;//our temporary dot array
        for (int i = 1; i < dots.length - 1; i++) {
            for (int j = 1; j < dots[i].length - 1; j++) {
                Dot dot1;//eight neighbour dots
                Dot dot2;//eight neighbour dots
                Dot dot3;//eight neighbour dots
                Dot dot4;//eight neighbour dots
                Dot dot5;//eight neighbour dots
                Dot dot6;//eight neighbour dots
                Dot dot7;//eight neighbour dots
                Dot dot8;//eight neighbour dots

                dot1 = dots[i - 1][j - 1];//neighbour dots initialization
                dot2 = dots[i - 1][j];//neighbour dots initialization
                dot3 = dots[i - 1][j + 1];//neighbour dots initialization
                dot4 = dots[i][j - 1];//neighbour dots initialization
                dot5 = dots[i][j + 1];//neighbour dots initialization
                dot6 = dots[i + 1][j- 1];//neighbour dots initialization
                dot7 = dots[i + 1][j];//neighbour dots initialization
                dot8 = dots[i + 1][j + 1];//neighbour dots initialization
                //neighbour dot array
                temp = new Dot[]{
                        dot1,
                        dot2,
                        dot3,
                        dot4,
                        dot5,
                        dot6,
                        dot7,
                        dot8,
                };
                //calculating neighbours
                dots[i][j].neighbours = calculateNeigh(temp);
            }
        }
    }

    //calculation of neighbours
    public static int calculateNeigh(Dot[] dot) {
        int neigh = 0;//result variable
        //generating all missing dots
        for (int i = 0; i < dot.length; i++) {
            if (dot[i] == null){
                dot[i] = new Dot(0,0,false);
            }
        }
        //calculating neighbours
        if (dot[0].painted) neigh++;
        if (dot[1].painted) neigh++;
        if (dot[2].painted) neigh++;
        if (dot[3].painted) neigh++;
        //center is dot[4]
        if (dot[4].painted) neigh++;
        if (dot[5].painted) neigh++;
        if (dot[6].painted) neigh++;
        if (dot[7].painted) neigh++;

        return neigh;
    }

    //dot drawing function
    public void draw(Graphics g) {
        if (painted)
            //fillrect
            g.fillRect((int) ((x + Main.xPlus) * Main.scale), (int) ((y + Main.yPlus) * Main.scale), (int) (Main.DotSize * Main.scale), (int) (Main.DotSize * Main.scale));
        else
            //drawrect
            g.drawRect((int) ((x + Main.xPlus) * Main.scale), (int) ((y + Main.yPlus) * Main.scale), (int) (Main.DotSize * Main.scale), (int) (Main.DotSize * Main.scale));
    }
    //drawing with the color
    public void draw(Graphics g, Color color) {
        //color setting
        Color tmp = g.getColor();
        g.setColor(color);
        //the same drawing code
        if (painted)
            g.fillRect((int) ((x + Main.xPlus) * Main.scale), (int) ((y + Main.yPlus) * Main.scale), (int) (Main.DotSize * Main.scale), (int) (Main.DotSize * Main.scale));
        else
            g.drawRect((int) ((x + Main.xPlus) * Main.scale), (int) ((y + Main.yPlus) * Main.scale), (int) (Main.DotSize * Main.scale), (int) (Main.DotSize * Main.scale));
        g.setColor(tmp);
    }

    //printing dot's information
    public String toString() {
        return "Dot x: " + x + ", Dot y: " + y + " Is painted: " + painted + ", neighbours: " + neighbours;
    }

    //updating dots

    public static Dot[][] update (Dot[][] dots){
        Dot[][] res = new Dot[dots.length][dots[0].length];//result dot array

        for (int i = 0; i < dots.length; i++) {
            for (int j = 0; j < dots[i].length; j++) {
                Dot cur = dots[i][j];//temp dot
                //rules (can be changed)
                if (!cur.painted){
                    if (cur.neighbours == 3){
                        cur.painted = true;
                    }
                }
                if (cur.painted){
                    if (cur.neighbours != 2 && cur.neighbours != 3){
                        cur.painted = false;
                    }
                }
                //end of rules

                res[i][j] = cur;
            }
        }
        return res;
    }
}
