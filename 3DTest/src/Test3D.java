
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * This is a template to help beginning students create Java programs that make
 * use of graphics. You must have a copy of GraphicsConsoleParent in the same
 * folder as this template or it will not work. out for more information.
 *
 * Please note that if you are using this template to create your own program,
 * you should change this comment header to describe what your program is for,
 * and you should add your name in a new @author field below.
 *
 * You might also want to change the name of the program from GraphicsConsole to
 * something more descriptive. If you do that you should find and replace all
 * occurrences of "GraphicsConsole" with the new name.
 *
 * Note that you MUST leave the original authors (Sam Scott and Kevin Forest) in
 * this header in addition to your own name, otherwise you may be committing an
 * act of academic dishonesty.
 *
 *
 * @author ***Your Name Here * @author Sam Scott
 * @author Kevin Forest
 *
 */
public class Test3D extends GraphicsConsoleParent {

    /**
     * This line creates the console, with the specified title, width, and
     * height (in pixels). Change the name from GraphicsConsole to match your
     * class name.
     */
    public static final boolean DEBUG = true;
    
    private static GraphicsConsoleParent console = new Test3D("3D Test", 800, 600);
    private static double radSpeed = 0.02;

    /**
     * Put your program in this method *
     */
    public void run() throws InterruptedException {
        // This line gets the Graphics object to draw on
        Graphics g = buffer.getGraphics();
        // *** YOUR CODE GOES BELOW HERE
        int frameRate = 1000 / 60;
        
        Rectangle rect1 = new Rectangle(100, 100, 100, 200, Color.blue, Color.red);
        Rectangle rect2 = new Rectangle(250, 120, 150, 120, Color.green, Color.yellow);
        Rectangle rect3 = new Rectangle(350, 320, 175, 210, Color.CYAN, Color.MAGENTA);

        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        while (true) {
            synchronized (buffer) {
                clearGraphics(g);
                
                rect1.draw(g);
                rect2.draw(g);
                rect3.draw(g);
                rect1.addPitch(0.02);
                rect2.addYaw(0.03);
                rect3.addPitch(0.05);
                readKeys(rect1);
            }
            Thread.sleep(frameRate);
        }
        // *** YOUR CODE GOES ABOVE HERE
    }

    public void readKeys(Rectangle rect) {
        if (isKeyDown(37)) {
            rect.addYaw(-radSpeed);
        } else if (isKeyDown(39)) {
            rect.addYaw(radSpeed);
        } else if (isKeyDown(38)) {
            rect.addPitch(-radSpeed);
        } else if (isKeyDown(40)) {
            rect.addPitch(radSpeed);
        }
    }

    /**
     * This is a constructor for the console. change the name to match the name
     * of your program.
     *
     * @param title console window title
     * @param width console width in pixels
     * @param height console height in pixels
     *
     */
    public Test3D(String title, int width, int height) {
        super(title, width, height);
    }

    //*********** DON'T CHANGE ANYTHING BELOW THIS LINE **************//
    /**
     * This is the main method. It's only job is to call your run method.
     *
     * @param args unused
     *
     */
    public static void main(String[] args) throws InterruptedException {
        console.run();
    }

}
