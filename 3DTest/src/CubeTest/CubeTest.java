package CubeTest;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

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
 * @author Mike Doan
 * @author Sam Scott
 * @author Kevin Forest
 *
 */
public class CubeTest extends GraphicsConsoleParent {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static int menu = 0;
    public static boolean isDebugNums = false;
    public static boolean isGradient = false;
    public static int rotationMode = 1;
    public static int activeButton = 2;
    public static int activeBG = 1;
    public static double rotation = 0;
    public static double zoom = 1;
    public static Vector3 camera = new Vector3(WIDTH / 2, HEIGHT / 2, 1000);
    public static Starfield sf2 = new Starfield(WIDTH - 110, 100, 100, 60, 100);

    private static int toRotate = 0;
    private static boolean activeRotate = false;
    private static boolean keyToggle = false;
    private static boolean buttonToggle = false;
    private static int pan = 0;
    private static double realFPS = 60;
    
    /**
     * This line creates the console, with the specified title, width, and
     * height (in pixels). Change the name from GraphicsConsole to match your
     * class name.
     */
    public static GraphicsConsoleParent console = new CubeTest("Cubing", WIDTH, HEIGHT);

    /**
     * Put your program in this method *
     */
    public void run() throws InterruptedException {
        // This line gets the Graphics object to draw on
        Graphics g = buffer.getGraphics();
        console.setBackgroundColor(Color.BLACK);
        // *** YOUR CODE GOES BELOW HERE
        int frameRate = 1000 / 60;
        long lastFrame = System.currentTimeMillis();
        double frameTime;

        BufferedImage sSheet = null;
        try {
            sSheet = ImageIO.read(getClass().getResource("images/Spritesheet.png"));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        BufferedImage[][] sprites = splitSheet(sSheet, 2, 4);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        Starfield sf = new Starfield(WIDTH, HEIGHT, 1000);

        Rubik.initRubic();

        while (true) {
            synchronized (buffer) {
                if (activeBG == 1) {
                    console.setBackgroundColor(Color.WHITE);
                } else if (activeBG == 2) {
                    console.setBackgroundColor(Color.BLACK);
                }
                clearGraphics(g);
                if (activeBG == 2) {
                    sf.draw(g);
                    sf.twinkle();
                }

                readKeys();
                readMouse(g);

                Rubik.runRubik(g);
                if (toRotate == -1) {
                    toRotate = Rubik.rotateTop(false) ? 0 : toRotate;
                } else if (toRotate == 1) {
                    toRotate = Rubik.rotateTop(false) ? 0 : toRotate;
                }
                drawMenu(sprites, g);
            }
            Thread.sleep(frameRate);
            frameTime = System.currentTimeMillis() - lastFrame;
            lastFrame = System.currentTimeMillis();
            realFPS = 1000 / frameTime;
        }
        // *** YOUR CODE GOES ABOVE HERE
    }

    public void drawMenu(BufferedImage[][] sSheet, Graphics g) {
        g.setColor(Color.LIGHT_GRAY);

        if (menu != 0) {
            g.fillRect(WIDTH - 120 + pan, 0, 120, HEIGHT);
            g.fillRoundRect(WIDTH - 150 + pan, 5, 50, 100, 5, 5);
            g.fillRoundRect(WIDTH - 150 + pan, 110, 50, 100, 5, 5);
            g.fillRoundRect(WIDTH - 150 + pan, 495, 50, 100, 5, 5);
        }
        if (menu == 1) {
            debugMenu(sSheet, g);
        } else if (menu == 3) {
            bgMenu(g);
        } else {
            g.fillRoundRect(WIDTH - 30, 5, 50, 100, 5, 5);
            g.fillRoundRect(WIDTH - 30, 110, 50, 100, 5, 5);
            g.fillRoundRect(WIDTH - 30, 495, 50, 100, 5, 5);
        }

        Graphics2D g2 = (Graphics2D) g;
        AffineTransform rotate = new AffineTransform();
        rotate.setToRotation(-Math.PI / 2);
        g2.setTransform(rotate);
        g2.setColor(Color.BLACK);
        if (menu != 0) {
            g2.drawString("Debug", -73, WIDTH - 130 + pan);
            g2.drawString("Background", -580, WIDTH - 130 + pan);
        } else {
            g2.drawString("Debug", -73, WIDTH - 10);
            g2.drawString("Background", -580, WIDTH - 10);
        }
        rotate.setToRotation(0);
        g2.setTransform(rotate);

        switch (menu) {
            case 1:
                checkClick(Rubik.cubes);
                g.drawString(String.format("FPS: %.2f", realFPS), 10, 20);
                g.drawString(String.format("Speed: %.2f", rotation), 10, 30);
                g.drawString(String.format("X: %d Y: %d", MouseInfo.getPointerInfo().getLocation().x - console.getLocationOnScreen().x, MouseInfo.getPointerInfo().getLocation().y - console.getLocationOnScreen().y), 10, 40);
                g.drawString(String.format("Zoom: %.2f", zoom), 10, 50);
                break;
            case 2:
                break;
            default:
                break;
        }

        if (pan > 0) {
            pan -= 2;
        }
    }

    public void debugMenu(BufferedImage[][] sSheet, Graphics g) {
        g.drawImage(sSheet[activeButton == 0 ? 1 : 0][0], WIDTH - 110 + pan, 10, null);
        g.drawImage(sSheet[activeButton == 1 ? 1 : 0][1], WIDTH - 110 + pan, 120, null);
        g.drawImage(sSheet[activeButton == 2 ? 1 : 0][2], WIDTH - 110 + pan, 230, null);
        g.drawImage(sSheet[activeButton == 3 ? 1 : 0][3], WIDTH - 110 + pan, 340, null);
        g.fill3DRect(WIDTH - 110 + pan, 450, 100, 20, !isDebugNums);
        g.fill3DRect(WIDTH - 110 + pan, 480, 100, 20, !activeRotate);
        g.fill3DRect(WIDTH - 110 + pan, 510, 100, 20, !isGradient);
        g.fill3DRect(WIDTH - 110 + pan, 540, 100, 20, true);
        g.fill3DRect(WIDTH - 110 + pan, 570, 100, 20, true);
        g.setColor(Color.BLACK);
        g.drawString("Coordinates", WIDTH - 100 + pan, 465);
        g.drawString("Rotation", WIDTH - 90 + pan, 495);
        g.drawString("Gradient", WIDTH - 90 + pan, 525);
        g.drawString(rotationMode == 1 ? "Yaw" : rotationMode == 2 ? "Pitch" : "Roll", WIDTH - 75 + pan, 555);
        g.drawString("Reset", WIDTH - 75 + pan, 585);
    }

    public void bgMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("White", WIDTH - 110 + pan, 15);
        g.setColor(Color.WHITE);
        g.fillRect(WIDTH - 110 + pan, 20, 100, 60);
        g.setColor(Color.BLACK);
        g.drawRect(WIDTH - 110 + pan, 20, 100, 60);
        
        g.setColor(Color.BLACK);
        g.drawString("Starfield", WIDTH - 110 + pan, 95);
        g.setColor(Color.BLACK);
        g.fillRect(WIDTH - 110 + pan, 100, 100, 60);
        g.setColor(Color.BLACK);
        g.drawRect(WIDTH - 110 + pan, 100, 100, 60);
        if (pan == 0) {
            sf2.draw(g);
            sf2.twinkle();
        }
    }

    public BufferedImage[][] splitSheet(BufferedImage source, int width, int height) {
        int cellWidth = source.getWidth() / width;
        int cellHeight = source.getHeight() / height;

        BufferedImage[][] returnImages = new BufferedImage[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                returnImages[i][j] = source.getSubimage(cellWidth * i, cellWidth * j, cellWidth, cellHeight);
            }
        }

        return returnImages;
    }

    public void readMouse(Graphics g) {
        int mouseX = MouseInfo.getPointerInfo().getLocation().x - console.getLocationOnScreen().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y - console.getLocationOnScreen().y;
        if (menu == 0) {
            if (isButtonDown(0) && mouseX > WIDTH - 30 && mouseX < WIDTH && mouseY > 5 && mouseY < 105 && !buttonToggle) {
                pan = 110;
                menu = 1;
                buttonToggle = true;
            } else if (isButtonDown(0) && mouseX > WIDTH - 30 && mouseX < WIDTH && mouseY > 110 && mouseY < 210 && !buttonToggle) {
                pan = 110;
                menu = 2;
                buttonToggle = true;
            } else if (isButtonDown(0) && mouseX > WIDTH - 30 && mouseX < WIDTH && mouseY > 495 && mouseY < 595 && !buttonToggle) {
                pan = 110;
                menu = 3;
                buttonToggle = true;
            }
        } else if (isButtonDown(0)) {
            if (mouseX > WIDTH - 150 + pan && mouseX < WIDTH - 100 + pan && mouseY > 5 && mouseY < 105 && !buttonToggle) {
                menu = menu == 1 ? 0 : 1;
                buttonToggle = true;
            }
            if (mouseX > WIDTH - 150 + pan && mouseX < WIDTH - 100 + pan && mouseY > 110 && mouseY < 210 && !buttonToggle) {
                menu = menu == 2 ? 0 : 2;
                buttonToggle = true;
            }
            if (mouseX > WIDTH - 150 + pan && mouseX < WIDTH - 100 + pan && mouseY > 495 && mouseY < 595 && !buttonToggle) {
                menu = menu == 3 ? 0 : 3;
                buttonToggle = true;
            }
            if (menu == 1) {
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 10 && mouseY < 110 && !buttonToggle) {
                    activeButton = 0;
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 120 && mouseY < 220 && !buttonToggle) {
                    activeButton = 1;
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 230 && mouseY < 330 && !buttonToggle) {
                    activeButton = 2;
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 340 && mouseY < 440 && !buttonToggle) {
                    activeButton = 3;
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 450 && mouseY < 470 && !buttonToggle) {
                    isDebugNums = isDebugNums ? false : true;
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 480 && mouseY < 500 && !buttonToggle) {
                    activeRotate = activeRotate ? false : true;
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 510 && mouseY < 530 && !buttonToggle) {
                    isGradient = isGradient ? false : true;
                    Rubik.setCubeSideCol(Rubik.cubes, !isGradient);
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 540 && mouseY < 560 && !buttonToggle) {
                    rotationMode = rotationMode == 1 ? 2 : rotationMode == 2 ? 3 : 1;
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 570 && mouseY < 590 && !buttonToggle) {
                    Rubik.initRubic();
                    buttonToggle = true;
                }
            } else if (menu == 3) {
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 20 && mouseY < 80 && !buttonToggle) {
                    activeBG = 1;
                    buttonToggle = true;
                }
                if (mouseX > WIDTH - 110 + pan && mouseX < WIDTH - 10 + pan && mouseY > 100 && mouseY < 160 && !buttonToggle) {
                    activeBG = 2;
                    buttonToggle = true;
                }
            }
        }

        if (!isButtonDown(0) && !isButtonDown(1) && !isButtonDown(2)) {
            buttonToggle = false;
        }

        zoom = getWheelRotation();
    }

    public void checkClick(Cube[] cubes) {
        int mouseX = MouseInfo.getPointerInfo().getLocation().x - CubeTest.console.getLocationOnScreen().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y - CubeTest.console.getLocationOnScreen().y;

        if (isButtonDown(0)) {
            for (Cube cube : cubes) {
                for (Vector3 point : cube.getPoints()) {
                    if (point.toVector2().sideLength(new Vector2(mouseX, mouseY)) < 10 && !buttonToggle) {
                        point.toggleCoords();
                    }
                }
                if (cube.origin.toVector2().sideLength(new Vector2(mouseX, mouseY)) < 10 && !buttonToggle) {
                    cube.origin.toggleCoords();
                }
            }
            buttonToggle = true;
        }
    }

    public void readKeys() {
        if (isKeyDown(KeyEvent.VK_ENTER) && !isKeyDown(KeyEvent.VK_SHIFT) && !keyToggle) {
            rotationMode = 1;
            toRotate = -1;
            keyToggle = true;
        } else if (isKeyDown(KeyEvent.VK_ENTER) && !keyToggle) {
            rotationMode = 1;
            toRotate = 1;
            keyToggle = true;
        } else if (!isKeyDown(KeyEvent.VK_ENTER) && keyToggle) {
            keyToggle = false;
        }

        if (isKeyDown(KeyEvent.VK_A)) {
            Rubik.prevRotation = rotationMode;
            rotationMode = 1;
            rotation = -0.04;
        } else if (isKeyDown(KeyEvent.VK_D)) {
            Rubik.prevRotation = rotationMode;
            rotationMode = 1;
            rotation = 0.04;
        } else if (isKeyDown(KeyEvent.VK_W)) {
            Rubik.prevRotation = rotationMode;
            rotationMode = 2;
            rotation = -0.04;
            if (Rubik.prevRotation == 1) {
                Rubik.rotateCubes(Math.PI);
            }
        } else if (isKeyDown(KeyEvent.VK_S)) {
            Rubik.prevRotation = rotationMode;
            rotationMode = 2;
            rotation = 0.04;
            if (Rubik.prevRotation == 1) {
                Rubik.rotateCubes(Math.PI);
            }
        } else if (isKeyDown(KeyEvent.VK_Q)) {
            Rubik.prevRotation = rotationMode;
            rotationMode = 3;
            rotation = -0.04;
            if (Rubik.prevRotation == 2) {
                //Rubik.rotateCubes(-Math.PI * 2 / 3);
            }
        } else if (isKeyDown(KeyEvent.VK_E)) {
            Rubik.prevRotation = rotationMode;
            rotationMode = 3;
            rotation = 0.04;
            if (Rubik.prevRotation == 2) {
                //Rubik.rotateCubes(-Math.PI * 2 / 3);
            }
        } else {
            rotation = 0;
        }
        
        /*
        if (isKeyDown(37)) {
            if (activeRotate) {
                rotation1 -= 0.01;
            } else {
                rotation1 = -0.04;
            }
        } else if (isKeyDown(39)) {
            if (activeRotate) {
                rotation1 += 0.01;
            } else {
                rotation1 = 0.04;
            }
        } else if (!activeRotate) {
            rotation1 = 0;
        }
        if (isKeyDown(38)) {
            if (activeRotate) {
                rotation2 -= 0.01;
            } else {
                rotation2 = -0.04;
            }
        } else if (isKeyDown(40)) {
            if (activeRotate) {
                rotation2 += 0.01;
            } else {
                rotation2 = 0.04;
            }
        } else if (!activeRotate) {
            rotation2 = 0;
        }*/
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
    public CubeTest(String title, int width, int height) {
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
