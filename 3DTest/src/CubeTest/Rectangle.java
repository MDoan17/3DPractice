package CubeTest;

import java.awt.Graphics;
import java.awt.Color;

/**
 *
 * @author Mike
 */
public class Rectangle {

    private final Vector3 p1;
    private final Vector3 p2;
    private final Vector3 p3;
    private final Vector3 p4;
    private Color col;
    private Cube myCube;

    private boolean isDrawn = true;

    public Rectangle(Vector3 p1, Vector3 p2, Vector3 p3, Vector3 p4, Color col, Cube cube) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.col = col;

        setCube(cube);
    }

    public Cube getCube() {
        return myCube;
    }

    public void setDrawn(boolean isDrawn) {
        this.isDrawn = isDrawn;
    }

    private void setCube(Cube cube) {
        myCube = cube;
    }

    public void setColor(Color col) {
        this.col = col;
    }

    public Vector3[] getPoints() {
        return new Vector3[]{p1, p2, p3, p4};
    }

    public Vector3 averagePoint() {
        float avgX = (p1.x + p2.x + p3.x + p4.x) / 4;
        float avgY = (p1.y + p2.y + p3.y + p4.y) / 4;
        float avgZ = (p1.z + p2.z + p3.z + p4.z) / 4;

        return new Vector3(avgX, avgY, avgZ);
    }

    public float getCamDist() {
        Vector3 camera = CubeTest.camera;
        return (float) Math.sqrt(Math.pow(averagePoint().x - camera.x, 2) + Math.pow(averagePoint().y - camera.y, 2) + Math.pow(averagePoint().z + camera.z, 2));
    }

    public void draw(Graphics g) {
        int[] xPoints = {(int) p1.toVector2().x, (int) p2.toVector2().x, (int) p3.toVector2().x, (int) p4.toVector2().x};
        int[] yPoints = {(int) p1.toVector2().y, (int) p2.toVector2().y, (int) p3.toVector2().y, (int) p4.toVector2().y};
                    int cRed = (col.getRed() - (int) ((averagePoint().z + 150) / 3)) < 0 ? 0 : (col.getRed() - (int) ((averagePoint().z + 150) / 3) > 255 ? 255 : (col.getRed() - (int) ((averagePoint().z + 150) / 3)));
                    int cGreen = (col.getGreen() - (int) ((averagePoint().z + 150) / 3)) < 0 ? 0 : (col.getGreen() - (int) ((averagePoint().z + 150) / 3) > 255 ? 255 : (col.getGreen() - (int) ((averagePoint().z + 150) / 3)));
                    int cBlue = (col.getBlue() - (int) ((averagePoint().z + 150) / 3)) < 0 ? 0 : (col.getBlue() - (int) ((averagePoint().z + 150) / 3) > 255 ? 255 : (col.getBlue() - (int) ((averagePoint().z + 150) / 3)));
        if (isDrawn) {
            if (CubeTest.activeButton != 1) {
                if (!CubeTest.isGradient) {
                    Color drawCol = new Color(cRed, cGreen, cBlue);
                    g.setColor(drawCol);
                    g.fillPolygon(xPoints, yPoints, 4);
                } else {
                    int drawLength = xPoints[0] - xPoints[2];
                    Vector3[] points = {p1, p2, p3, p4};
                    gradientPolygon(points, xPoints, yPoints, drawLength, g);
                }
            }
        }
        g.setColor(Color.BLACK);
        //g.setColor(new Color(cRed - 25 < 0 ? 0 : cRed - 25, cGreen - 25 < 0 ? 0 : cGreen - 25, cBlue - 25 < 0 ? 0 : cBlue - 25));
        g.drawPolygon(xPoints, yPoints, 4);
    }

    public void gradientPolygon(Vector3[] points, int[] xPoints, int[] yPoints, int drawLength, Graphics g) {
        double yDeltaTop = (yPoints[0] - yPoints[1]) / (double) drawLength;
        double yDeltaBottom = (yPoints[2] - yPoints[3]) / (double) drawLength;
        int aRed, aGreen, aBlue;
        double colDelta;
        if (points[0].z < averagePoint().z) {
            colDelta = (averagePoint().z - points[0].z) / (double) drawLength / 2;
        } else {
            colDelta = (points[0].z - averagePoint().z) / (double) drawLength / 2;
        }
        for (int i = 0; i < Math.abs(drawLength); i++) {
            aRed = col.getRed() - (int) averagePoint().z - (int) (colDelta * i) - 200;
            aGreen = col.getGreen() - (int) averagePoint().z - (int) (colDelta * i) - 200;
            aBlue = col.getBlue() - (int) averagePoint().z - (int) (colDelta * i) - 200;
            aRed = aRed > 255 ? 255 : aRed < 0 ? 0 : aRed;
            aGreen = aGreen > 255 ? 255 : aGreen < 0 ? 0 : aGreen;
            aBlue = aBlue > 255 ? 255 : aBlue < 0 ? 0 : aBlue;
            g.setColor(new Color(aRed, aGreen, aBlue));
            if (xPoints[0] < xPoints[1]) {
                g.drawLine(xPoints[0] + i, yPoints[0] + (int) (yDeltaTop * i), xPoints[0] + i, yPoints[3] - (int) (yDeltaBottom * i));
            } else {
                g.drawLine(xPoints[1] + i, yPoints[0] + (int) (yDeltaTop * i), xPoints[1] + i, yPoints[3] - (int) (yDeltaBottom * i));
            }
        }
    }
}
