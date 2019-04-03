package CubeTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Mike
 */
public class Vector3 {

    public float x;
    public float y;
    public float z;

    private double thetaYaw;
    private double thetaPitch;
    private double thetaRoll;

    private Cube myCube = null;
    private boolean isCoords = false;

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setCube(Cube cube) {
        myCube = cube;
        updateAngles('n');
    }

    public void updateAngles(char curAxis) {
        if (curAxis != 'y') {
            thetaYaw = getPlanarAngle('x', 'z');
        }
        if (curAxis != 'p') {
            thetaPitch = getPlanarAngle('y', 'z');
        }
        if (curAxis != 'r') {
            thetaRoll = getPlanarAngle('x', 'y');
        }
    }

    public double getPlanarAngle(char axisOne, char axisTwo) {
        Vector2 origin = null, point = null, radial = null;
        double radius = 0, a;
        int plane = 0;

        if (axisOne == 'x' && axisTwo == 'z' || axisOne == 'z' && axisTwo == 'x') {
            origin = new Vector2(myCube.origin.x, myCube.origin.z);
            point = new Vector2(x, z);
            radius = sideLength(point, origin);
            radial = new Vector2((float) (origin.x + radius), origin.y);
            plane = 1;
        } else if (axisOne == 'y' && axisTwo == 'z' || axisOne == 'z' && axisTwo == 'y') {
            origin = new Vector2(myCube.origin.z, myCube.origin.y);
            point = new Vector2(z, y);
            radius = sideLength(point, origin);
            radial = new Vector2((float) (origin.x - radius), origin.y);
            plane = 2;
        } else if (axisOne == 'x' && axisTwo == 'y' || axisOne == 'y' && axisTwo == 'x') {
            origin = new Vector2(myCube.origin.x, myCube.origin.y);
            point = new Vector2(x, y);
            radius = sideLength(point, origin);
            radial = new Vector2((float) (origin.x + radius), origin.y);
            plane = 3;
        }

        a = sideLength(point, radial);
        double returnVal = Math.acos(1 - (Math.pow(a, 2) / (2 * Math.pow(radius, 2))));

        switch (plane) {
            case 1:
                if (z > origin.y) {
                    returnVal *= -1;
                }   break;
            case 2:
                if (y > origin.y) {
                    returnVal *= -1;
                }   break;
            case 3:
                if (y > origin.y) {
                    returnVal *= -1;
                }   break;
        }

        return returnVal;
    }

    public void toggleCoords() {
        isCoords = isCoords ? false : true;
    }

    public void rotateTest1(double radians) {
        thetaYaw += radians;
        thetaYaw -= thetaYaw > Math.PI * 2 ? Math.PI * 2 : 0;
        thetaYaw += thetaYaw < Math.PI * -2 ? Math.PI * 2 : 0;

        Vector2 origin = new Vector2(myCube.origin.x, myCube.origin.z);
        Vector2 point = new Vector2(x, z);
        double radius = sideLength(point, origin);

        x = (float) (origin.x + radius * Math.cos(thetaYaw));
        z = (float) (origin.y - radius * Math.sin(thetaYaw));

        updateAngles('y');
    }

    public void rotateTest2(double radians) {
        thetaPitch += radians;
        thetaPitch -= thetaPitch > Math.PI * 2 ? Math.PI * 2 : 0;
        thetaPitch += thetaPitch < Math.PI * -2 ? Math.PI * 2 : 0;

        Vector2 origin = new Vector2(myCube.origin.z, myCube.origin.y);
        Vector2 point = new Vector2(z, y);
        double radius = sideLength(point, origin);

        z = (float) (origin.x + radius * Math.cos(thetaPitch));
        y = (float) (origin.y + radius * Math.sin(thetaPitch));

        updateAngles('p');
    }

    public void rotateTest3(double radians) {
        thetaRoll += radians;
        thetaRoll -= thetaRoll > Math.PI * 2 ? Math.PI * 2 : 0;
        thetaRoll += thetaRoll < Math.PI * -2 ? Math.PI * 2 : 0;
        
        Vector2 origin = new Vector2(myCube.origin.x, myCube.origin.y);
        Vector2 point = new Vector2(x, y);
        double radius = sideLength(point, origin);

        x = (float) (origin.x + radius * Math.cos(thetaRoll));
        y = (float) (origin.y + radius * Math.sin(thetaRoll));

        updateAngles('r');
    }
    
    public static double sideLength(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double sideLength(Vector2 p1, Vector2 p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static double sideLength(Vector3 p1, Vector3 p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2) + Math.pow(p1.z - p2.z, 2));
    }

    public Vector2 toVector2() {
        double camX = CubeTest.camera.x;
        double camY = CubeTest.camera.y;
        double camZ = CubeTest.camera.z;

        double focal = (camZ - z) / camZ;

        int projX = (int) (((x - camX) * focal * CubeTest.zoom) + camX);
        int projY = (int) (((y - camY) * focal * CubeTest.zoom) + camY);
        return new Vector2(projX, projY, false);
    }

    public Vector2 toVector2(boolean coords) {
        double camX = CubeTest.camera.x;
        double camY = CubeTest.camera.y;
        double camZ = CubeTest.camera.z;

        double focal = (camZ - z) / camZ;

        int projX = (int) (((x - camX) * focal * CubeTest.zoom) + camX);
        int projY = (int) (((y - camY) * focal * CubeTest.zoom) + camY);
        return new Vector2(projX, projY, coords);
    }

    public void draw(Graphics g, double zoom) {
        toVector2(isCoords).draw(g);

        if (CubeTest.isDebugNums && CubeTest.menu == 1 && isCoords) {
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            g.setColor(Color.BLACK);
            g.drawString(String.format("x: %.0f, y: %.0f, z: %.0f", x, y, z), (int) toVector2().x + 2, (int) toVector2().y - 2);
            g.drawString(String.format("y: %.2f, p: %.2f, r: %.2f", thetaYaw, thetaPitch, thetaRoll), (int) toVector2().x + 2, (int) toVector2().y - 12);
        }
    }

    public void drawLine(Vector3 connection, Graphics g, double zoom) {
        toVector2().drawLine(connection.toVector2(), g);
    }
}
