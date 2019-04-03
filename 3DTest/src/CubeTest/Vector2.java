package CubeTest;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Mike
 */
public class Vector2 {

    public final float x;
    public final float y;
    private boolean isCoords = false;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(float x, float y, boolean coords) {
        this.x = x;
        this.y = y;
        isCoords = coords;
    }

    public double sideLength(Vector2 p1) {
        return Math.sqrt(Math.pow(x - p1.x, 2) + Math.pow(y - p1.y, 2));
    }

    public void draw(Graphics g) {
        if (CubeTest.activeButton != 2) {
            g.setColor(Color.DARK_GRAY);
            g.fillRect((int) x - 1, (int) y - 1, 3, 3);
            g.drawOval((int) x - 10, (int) y - 10, 20, 20);
        }

        if (CubeTest.isDebugNums && CubeTest.menu == 1 && isCoords) {
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            g.setColor(Color.BLACK);
            g.drawString(String.format("x: %.0f, y: %.0f", x, y), (int) x + 2, (int) y + 12);
        }
    }

    public void drawLine(Vector2 connection, Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.drawLine((int) x, (int) y, (int) connection.x, (int) connection.y);
    }
}
