package CubeTest;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Mike
 */
public class Cube {

    public Vector3 origin;

    private Vector3[] points;
    private Rectangle[] sides;
    public boolean drawNums = false;

    public Cube(Vector3[] points, Vector3 origin) {
        this.points = points;
        this.origin = origin;

        instantiatePoints();
    }

    public Cube(Vector3 point1, Vector3 point2, Vector3 origin) {
        Vector3 p1 = point1;
        Vector3 p7 = point2;
        Vector3 p2 = new Vector3(p7.x, p1.y, p1.z);
        Vector3 p3 = new Vector3(p7.x, p1.y, p7.z);
        Vector3 p4 = new Vector3(p1.x, p1.y, p7.z);
        Vector3 p5 = new Vector3(p1.x, p7.y, p1.z);
        Vector3 p6 = new Vector3(p7.x, p7.y, p1.z);
        Vector3 p8 = new Vector3(p1.x, p7.y, p7.z);

        points = new Vector3[]{p1, p2, p3, p4, p5, p6, p7, p8};
        this.origin = origin;
        instantiatePoints();
    }

    public Cube(Vector3 point, Vector3 origin, int width, int height, int depth) {
        Vector3 p1 = point;
        Vector3 p2 = new Vector3(p1.x + width, p1.y, p1.z);
        Vector3 p3 = new Vector3(p1.x + width, p1.y, p1.z + depth);
        Vector3 p4 = new Vector3(p1.x, p1.y, p1.z + depth);
        Vector3 p5 = new Vector3(p1.x, p1.y + height, p1.z);
        Vector3 p6 = new Vector3(p1.x + width, p1.y + height, p1.z);
        Vector3 p7 = new Vector3(p1.x + width, p1.y + height, p1.z + depth);
        Vector3 p8 = new Vector3(p1.x, p1.y + height, p1.z + depth);

        points = new Vector3[]{p1, p2, p3, p4, p5, p6, p7, p8};
        this.origin = origin;
        instantiatePoints();
    }

    public void instantiatePoints() {
        Rectangle side1 = new Rectangle(points[2], points[1], points[5], points[6], new Color(255, 120, 0), this);
        Rectangle side2 = new Rectangle(points[3], points[2], points[6], points[7], new Color(240, 240, 240), this);
        Rectangle side3 = new Rectangle(points[0], points[3], points[7], points[4], Color.RED, this);
        Rectangle side4 = new Rectangle(points[1], points[0], points[4], points[5], Color.YELLOW, this);
        Rectangle side5 = new Rectangle(points[0], points[1], points[2], points[3], Color.BLUE, this);
        Rectangle side6 = new Rectangle(points[4], points[5], points[6], points[7], Color.GREEN, this);

        sides = new Rectangle[]{side1, side2, side3, side4, side5, side6};

        for (Vector3 point : points) {
            point.setCube(this);
        }
    }

    public void rotate1(double radians) {
        for (Vector3 point : points) {
            point.rotateTest1(radians);
        }
    }

    public void rotate2(double radians) {
        for (Vector3 point : points) {
            point.rotateTest2(radians);
        }
    }

    public void rotate3(double radians) {
        for (Vector3 point : points) {
            point.rotateTest3(radians);
        }
    }

    public Vector3[] getPoints() {
        return points;
    }

    public Rectangle[] getSides() {
        return sides;
    }

    public Vector3 averagePoint() {
        int avgX = 0;
        int avgY = 0;
        int avgZ = 0;

        for (Vector3 point : points) {
            avgX += point.x;
            avgY += point.y;
            avgZ += point.z;
        }

        avgX /= points.length;
        avgY /= points.length;
        avgZ /= points.length;

        return new Vector3(avgX, avgY, avgZ);
    }

    public void setOrigin(Vector3 origin) {
        this.origin = origin;
    }

    public float getCamDist() {
        Vector3 camera = CubeTest.camera;
        return (float) Math.sqrt(Math.pow(averagePoint().x - camera.x, 2) + Math.pow(averagePoint().y - camera.y, 2) + Math.pow(averagePoint().z + camera.z, 2));
    }

    public void draw(Graphics g, double zoom) {
        if (CubeTest.activeButton != 2) {
            for (Vector3 point : points) {
                point.draw(g, zoom);
            }
            origin.draw(g, zoom);
        }

    }
}
