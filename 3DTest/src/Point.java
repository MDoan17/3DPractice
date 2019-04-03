
import java.awt.Graphics;
import java.awt.Color;

/**
 *
 * @author Mike
 */
public class Point {

    public int X;
    public int Y;
    public int Z;
    public Point origin;
    private Point radius;

    public Point(int x, int y, int z) {
        X = x;
        Y = y;
        Z = z;
        origin = new Point(x, y, z, true);
    }
    
    public Point(int x, int y, int z, boolean isOrigin) {
        X = x;
        Y = y;
        Z = z;
    }

    public Point(int x, int y, int z, Point origin) {
        X = x;
        Y = y;
        Z = z;
        this.origin = origin;
        radius = new Point(origin.X - X, origin.Y - Y, origin.Z - Z, true);
    }
    
    public void rotate(char dir, double rad) {
        switch (dir) {
            case 'y':
                rotateYaw(rad);
                break;
            case 'p':
                rotatePitch(rad);
                break;
            case 'r':
                rotateRoll(rad);
                break;
        }
    }

    private void rotateYaw(double rad) {
        X = (int) (origin.X - radius.X * Math.cos(rad));
        Z = (int) (origin.Z - radius.X * Math.sin(rad));
        if (Y <= origin.Y) {
            Y = origin.Y - Math.abs(radius.Y) - (Z / 8);
        } else {
            Y = origin.Y + Math.abs(radius.Y) + (Z / 8);
        }
    }

    private void rotatePitch(double rad) {
        Y = (int) (origin.Y - radius.Y * Math.cos(rad));
        Z = (int) (origin.Z - radius.Y * Math.sin(rad));
        if (X <= origin.X) {
            X = origin.X - Math.abs(radius.X) + (Z / 8);
        } else {
            X = origin.X + Math.abs(radius.X) - (Z / 8);
        }
    }

    private void rotateRoll(double rad) {
        double baseLength = 2 * Math.sqrt((origin.Y - radius.Y) + (origin.X - radius.X)) * Math.sin(rad);
        X = origin.X - radius.X + (int) (baseLength * Math.sin(rad));
        Y = origin.Y - radius.Y + (int) (baseLength * Math.cos(rad));
    }
    
    public void draw(boolean isOrigin, Graphics g) {
        int[] xPoints = {X - 2, X + 2, X + 2, X - 2};
        int[] yPoints = {Y - 2, Y - 2, Y + 2, Y + 2};
        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(xPoints, yPoints, 4);

        if (isOrigin) {
            int[] xPointsO = {origin.X - 2, origin.X + 2, origin.X + 2, origin.X - 2};
            int[] yPointsO = {origin.Y - 2, origin.Y - 2, origin.Y + 2, origin.Y + 2};
            g.setColor(Color.BLACK);
            g.fillPolygon(xPointsO, yPointsO, 4);
        }
    }
}
