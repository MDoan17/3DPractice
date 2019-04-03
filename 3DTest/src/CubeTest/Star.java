
package CubeTest;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class to create a draw-able "star".
 * 
 * @author sam.scott1
 */
public class Star {

    private int x, y; // location
    private Color color; 

    /**
     * Constructor.
     * 
     * @param x location
     * @param y location
     * @param color color
     */
    public Star(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Draw the star on a Graphics object
     * @param g the Graphics object
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, 2, 2);
    }

    /**
     * @param color new star color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
