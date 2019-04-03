
import java.awt.Graphics;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Mike
 */
public class Rectangle {

    private int x;
    private int y;
    private int width;
    private int height;
    private Point p1;
    private Point p2;
    private Point p3;
    private Point p4;
    private double roll;
    private double yaw;
    private double pitch;
    private Color side1;
    private Color side2;

    public Rectangle(int screenWidth, int screenHeight) {
        Random randInt = new Random();
        x = randInt.nextInt() % screenWidth;
        y = randInt.nextInt() % screenHeight;
        width = randInt.nextInt() % 250;
        height = randInt.nextInt() % 250;
        side1 = new Color(Math.abs(randInt.nextInt()) % 256, Math.abs(randInt.nextInt()) % 256, Math.abs(randInt.nextInt()) % 256);
        side2 = new Color(Math.abs(randInt.nextInt()) % 256, Math.abs(randInt.nextInt()) % 256, Math.abs(randInt.nextInt()) % 256);

        p1 = new Point(x, y, 0, new Point(x + width / 2, y + height / 2, 0));
        p2 = new Point(x + width, y, 0, new Point(x + width / 2, y + height / 2, 0));
        p3 = new Point(x + width, y + height, 0, new Point(x + width / 2, y + height / 2, 0));
        p4 = new Point(x, y + height, 0, new Point(x + width / 2, y + height / 2, 0));
    }
    
    public Rectangle(int x, int y, int width, int height, Color c1, Color c2) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        side1 = c1;
        side2 = c2;

        p1 = new Point(x, y, 0, new Point(x + width / 2, y + height / 2, 0));
        p2 = new Point(x + width, y, 0, new Point(x + width / 2, y + height / 2, 0));
        p3 = new Point(x + width, y + height, 0, new Point(x + width / 2, y + height / 2, 0));
        p4 = new Point(x, y + height, 0, new Point(x + width / 2, y + height / 2, 0));
    }

    public Rectangle(Point[] points, int x, int y, int width, int height, Color c1, Color c2, double yaw) {
        this.width = width;
        this.height = height;
        side1 = c1;
        side2 = c2;
        this.yaw = yaw;
        this.x = x;
        this.y = y;

        p1 = points[0];
        p2 = points[1];
        p3 = points[2];
        p4 = points[3];
    }

    public void addYaw(double value) {
        yaw += value;
        calcPoints('y');
    }
    
    public void addPitch(double value) {
        pitch += value;
        calcPoints('p');
    }
    
    public void addRoll(double value) {
        roll += value;
        calcPoints('r');
    }

    public void calcPoints(char dir) {
        switch (dir) {
            case 'y':
                p1.rotate(dir, yaw);
                p2.rotate(dir, yaw);
                p3.rotate(dir, yaw);
                p4.rotate(dir, yaw);
                break;
            case 'p':
                p1.rotate(dir, pitch);
                p2.rotate(dir, pitch);
                p3.rotate(dir, pitch);
                p4.rotate(dir, pitch);
                break;
            case 'r':
                p1.rotate(dir, roll);
                p2.rotate(dir, roll);
                p3.rotate(dir, roll);
                p4.rotate(dir, roll);
                break;
        }
    }

    public void draw(Graphics g) {
        int[] xPoints = {p1.X, p2.X, p3.X, p4.X};
        int[] yPoints = {p1.Y, p2.Y, p3.Y, p4.Y};
        int[] zPoints = {p1.Z, p2.Z, p3.Z, p4.Z};
        
        if (yaw != 0 && ((int) Math.abs(yaw / (Math.PI / 2)) % 4 == 1 || (int) Math.abs(yaw / (Math.PI / 2)) % 4 == 2)) {
            g.setColor(side1);
        } else if (pitch != 0 && ((int) Math.abs(pitch / (Math.PI / 2)) % 4 == 1 || (int) Math.abs(pitch / (Math.PI / 2)) % 4 == 2)) {
            g.setColor(side1);
        } else {
            g.setColor(side2);
        }
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(Color.black);
        g.drawPolygon(xPoints, yPoints, 4);

        if (Test3D.DEBUG) {
            p1.draw(true, g);
            p2.draw(false, g);
            p3.draw(false, g);
            p4.draw(false, g);
            g.drawString(String.format("yaw: %.2f", yaw), p1.origin.X - 30, p1.origin.Y - 20);
            g.drawString(String.format("pitch: %.2f", pitch), p1.origin.X - 30, p1.origin.Y);
            g.drawString(String.format("roll: %.2f", roll), p1.origin.X - 30, p1.origin.Y + 20);
            g.drawString(String.format("1 - x: %d, y: %d, z: %d", xPoints[0], yPoints[0], zPoints[0]), xPoints[0], yPoints[0]);
            g.drawString(String.format("2 - x: %d, y: %d, z: %d", xPoints[1], yPoints[1], zPoints[1]), xPoints[1], yPoints[1]);
            g.drawString(String.format("3 - x: %d, y: %d, z: %d", xPoints[2], yPoints[2], zPoints[2]), xPoints[2], yPoints[2]);
            g.drawString(String.format("4 - x: %d, y: %d, z: %d", xPoints[3], yPoints[3], zPoints[3]), xPoints[3], yPoints[3]);
        }
    }
}
