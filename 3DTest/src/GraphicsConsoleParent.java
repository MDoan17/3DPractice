
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * This class is to be used with the GraphicsConsole template to enable
 * beginning programmers to use graphics in Java. Users of the template should
 * not change any code in this class unless they are sure they know what they
 * are doing.
 *
 * @author Sam Scott
 * @author Kevin Forest
 *
 */
abstract class GraphicsConsoleParent extends JPanel implements ActionListener, KeyListener {

    /**
     * Call this method to set the background color for the clearGraphics
     * method.
     *
     * @param c The new background color.
   *
     */
    public void setBackgroundColor(Color c) {
        backgroundColor = c;
    }

    /**
     * Call this method to get the width in pixels *
     */
    public int getWidth() {
        return consoleWidth;
    }

    /**
     * Call this method to get the height in pixels *
     */
    public int getHeight() {
        return consoleHeight;
    }

    /**
     * Call this method in your program if you want to pause and wait for the
     * user to hit a key before proceeding.
     *
     * @return The key pressed.
   *
     */
    public char waitForKey() throws InterruptedException {
        int key = 0;
        typedKeyChar = 0;

        while (Character.isISOControl((char) key) && key != KeyEvent.VK_ENTER && key != KeyEvent.VK_ESCAPE || key == KeyEvent.VK_SHIFT || key == KeyEvent.VK_CAPS_LOCK) {
            key = typedKeyChar;
            Thread.sleep(1);
        }

        typedKeyChar = 0;
        return (char) key;
    }

    /**
     * Call this method in your program if you want to pause and wait for the
     * user to hit a key before proceeding.
     *
     * This method also works for shift, alt and other non-printable keys.
     *
     * @return The integer code for the key pressed.
   *
     */
    public int waitForKeyCode() throws InterruptedException {
        currentKeyCode = 0;
        int key = 0;

        while ((key = getKeyCode()) == 0) {
            Thread.sleep(1);
        }

        return key;
    }

    /**
     * Call this method to find out what key is currently being held down.
     *
     * @return An integer representation of the current key, or 0 if no key is
     * held down.
     */
    public int getKeyCode() {
        return currentKeyCode;
    }

    /**
     * Call this method to find out the last key the user pressed.
     *
     * @return An integer representation of the last key pressed, or 0 if no key
     * has been pressed.
     */
    public int getLastKeyCode() {
        return lastKeyCode;
    }

    /**
     * Call this method to find out what key is currently being held down.
     *
     * @return A char representation of the current key, or (char)0 if no key is
     * held down.
     */
    public char getKeyChar() {
        return currentKeyChar;
    }

    /**
     * Call this method to find out the last key the user pressed.
     *
     * @return A char representation of the last key pressed, or (char)0 if no
     * key has been pressed.
     */
    public char getLastKeyChar() {
        return lastKeyChar;
    }

    /**
     * Call this method to find out if a specific key is being held down right
     * now.
     *
     * @param key An integer representation of the key to check.
     * @return True if the key is held down, false otherwise.
     */
    public boolean isKeyDown(int key) {
        return keysDown[key];
    }

    /**
     * A convenience method to clear a graphics object to the background color
     *
     * @param g The Graphics object to clear
   *
     */
    public void clearGraphics(Graphics g) {
        Color c = g.getColor();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, consoleWidth, consoleHeight);
        g.setColor(c);
    }

    /**
     * Gets the color of the requested pixel
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @return the color of the pixel
   *
     */
    public Color getPixelColor(int x, int y) {
        return new Color((buffer.getRGB(x, y) >> 16) & 255, (buffer.getRGB(x, y) >> 8) & 255, buffer.getRGB(x, y) & 255);
    }

    /**
     * Sets the requested pixel to the requested color
     *
     * @param x the x coordinate of the pixel
     * @param y the y coordinate of the pixel
     * @param c the color to set
   *
     */
    public void setPixelColor(int x, int y, Color c) {
        buffer.setRGB(x, y, (c.getAlpha() << 24) | (c.getRed() << 16) | (c.getGreen() << 8) | c.getBlue());
    }

    /**
     * The image buffer for drawing. Use this object to synchronize your drawing
     * to avoid screen flicker, and use buffer.getGraphics() to get a graphics
     * object to draw on.
   *
     */
    protected final BufferedImage buffer;

    //************ NOTHING BELOW IS TO BE USED IN THE GRAPHICS CONSOLE *******************//
    /**
     * Set to true when we are ready to draw *
     */
    private boolean ready = false;
    /**
     * The background color for clearGraphics *
     */
    private Color backgroundColor = Color.WHITE;
    /**
     * The height of the console window *
     */
    private int consoleHeight;
    /**
     * The width of the console window *
     */
    private int consoleWidth;
    /**
     * Current key held down *
     */
    private int currentKeyCode = 0;
    /**
     * Last key pressed *
     */
    private int lastKeyCode = 0;
    /**
     * Array of booleans set to true when corresponding key held down *
     */
    private boolean[] keysDown = new boolean[256];
    /**
     * Current key held down *
     */
    private char currentKeyChar = 0;
    /**
     * Last key pressed *
     */
    private char lastKeyChar = 0;
    /**
     * Last key from keyTyped event *
     */
    private char typedKeyChar = 0;

    /**
     * Constructor for a graphics console
     *
     * @param title The title of the console window
     * @param width The width in pixels
     * @param height The height in pixels
    *
     */
    public GraphicsConsoleParent(String title, int width, int height) {
        consoleWidth = width;
        consoleHeight = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = buffer.getGraphics();
        g.setColor(Color.BLACK);
        clearGraphics(g);
        addKeyListener(this);

        Timer drawTimer = new Timer(25, this);
        drawTimer.start();

        JFrame frame = new JFrame();
        frame.setContentPane(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setTitle(title);
        frame.setVisible(true);
        this.requestFocusInWindow();
        // centers and resizes the frame, courtesy of Kevin McLean
        frame.setLocationRelativeTo(null);
        frame.setSize(new Dimension(frame.getWidth() - (getWidth() - width), frame.getHeight() - (getHeight() - height)));
    }

    /**
     * Called by the timer to repaint the screen and request focus
     *
     * @param e unused action event
   *
     */
    public void actionPerformed(ActionEvent e) {
        requestFocusInWindow();
        repaint();
    }

    /**
     * Responds to the paint event *
     */
    public void paintComponent(Graphics g) {
        synchronized (buffer) // synchronized to avoid flicker
        {
            g.drawImage(buffer, 0, 0, this);
        }
        ready = true;
    }

    /**
     * Event handler for keyPressed event. Records key and exits.
     *
     * @param e The keyboard event
   *
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        char keyChar = e.getKeyChar();

        lastKeyCode = keyCode;
        lastKeyChar = keyChar;
        currentKeyCode = keyCode;
        currentKeyChar = keyChar;
        if (keyCode >= 0 && keyCode < 256) {
            keysDown[keyCode] = true;
        }
    }

    /**
     * Event handler for keyReleased event. Records key and exits.
     *
     * @param e The keyboard event
   *
     */
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        currentKeyCode = 0;
        currentKeyChar = 0;
        if (keyCode >= 0 && keyCode < 256) {
            keysDown[keyCode] = false;
        }
    }

    /**
     * Event handler for keyTyped event. Records key and exits.
     *
     * @param e The keyboard event
   *
     */
    public void keyTyped(KeyEvent e) {
        typedKeyChar = e.getKeyChar();
    }

    /**
     * The method that contains the main program *
     */
    public abstract void run() throws InterruptedException;
}
