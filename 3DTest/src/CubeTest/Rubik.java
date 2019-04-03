package CubeTest;

import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;

/**
 *
 * @author Mike
 */
public class Rubik {

    public static Cube[] cubes;
    public static Cube[][][] cubePos;

    public static int prevRotation = 1;

    private static double radians = Math.PI / 2;

    private static ArrayList<Rectangle> drawOrder;

    public static void initRubic() {

        Vector3 p1 = new Vector3(350, 250, -150);
        Vector3 p2 = new Vector3(350, 150, -150);
        Vector3 p3 = new Vector3(350, 350, -150);
        Vector3 p4 = new Vector3(250, 250, -150);
        Vector3 p5 = new Vector3(250, 150, -150);
        Vector3 p6 = new Vector3(250, 350, -150);
        Vector3 p7 = new Vector3(450, 250, -150);
        Vector3 p8 = new Vector3(450, 150, -150);
        Vector3 p9 = new Vector3(450, 350, -150);

        Vector3 p11 = new Vector3(350, 250, -50);
        Vector3 p12 = new Vector3(350, 150, -50);
        Vector3 p13 = new Vector3(350, 350, -50);
        Vector3 p14 = new Vector3(250, 250, -50);
        Vector3 p15 = new Vector3(250, 150, -50);
        Vector3 p16 = new Vector3(250, 350, -50);
        Vector3 p17 = new Vector3(450, 250, -50);
        Vector3 p18 = new Vector3(450, 150, -50);
        Vector3 p19 = new Vector3(450, 350, -50);

        Vector3 p21 = new Vector3(350, 250, 50);
        Vector3 p22 = new Vector3(350, 150, 50);
        Vector3 p23 = new Vector3(350, 350, 50);
        Vector3 p24 = new Vector3(250, 250, 50);
        Vector3 p25 = new Vector3(250, 150, 50);
        Vector3 p26 = new Vector3(250, 350, 50);
        Vector3 p27 = new Vector3(450, 250, 50);
        Vector3 p28 = new Vector3(450, 150, 50);
        Vector3 p29 = new Vector3(450, 350, 50);
        Vector3 origin = new Vector3(CubeTest.WIDTH / 2, CubeTest.HEIGHT / 2, 0);

        Cube c1 = new Cube(p1, origin, 100, 100, 100);
        Cube c2 = new Cube(p2, origin, 100, 100, 100);
        Cube c3 = new Cube(p3, origin, 100, 100, 100);
        Cube c4 = new Cube(p4, origin, 100, 100, 100);
        Cube c5 = new Cube(p5, origin, 100, 100, 100);
        Cube c6 = new Cube(p6, origin, 100, 100, 100);
        Cube c7 = new Cube(p7, origin, 100, 100, 100);
        Cube c8 = new Cube(p8, origin, 100, 100, 100);
        Cube c9 = new Cube(p9, origin, 100, 100, 100);

        Cube c11 = new Cube(p11, origin, 100, 100, 100);
        Cube c12 = new Cube(p12, origin, 100, 100, 100);
        Cube c13 = new Cube(p13, origin, 100, 100, 100);
        Cube c14 = new Cube(p14, origin, 100, 100, 100);
        Cube c15 = new Cube(p15, origin, 100, 100, 100);
        Cube c16 = new Cube(p16, origin, 100, 100, 100);
        Cube c17 = new Cube(p17, origin, 100, 100, 100);
        Cube c18 = new Cube(p18, origin, 100, 100, 100);
        Cube c19 = new Cube(p19, origin, 100, 100, 100);

        Cube c21 = new Cube(p21, origin, 100, 100, 100);
        Cube c22 = new Cube(p22, origin, 100, 100, 100);
        Cube c23 = new Cube(p23, origin, 100, 100, 100);
        Cube c24 = new Cube(p24, origin, 100, 100, 100);
        Cube c25 = new Cube(p25, origin, 100, 100, 100);
        Cube c26 = new Cube(p26, origin, 100, 100, 100);
        Cube c27 = new Cube(p27, origin, 100, 100, 100);
        Cube c28 = new Cube(p28, origin, 100, 100, 100);
        Cube c29 = new Cube(p29, origin, 100, 100, 100);
        cubes = new Cube[]{
            c1, c2, c3, c4, c5, c6, c7, c8, c9,
            c11, c12, c13, c14, c15, c16, c17, c18, c19,
            c21, c22, c23, c24, c25, c26, c27, c28, c29
        };
        cubePos = new Cube[][][]{
            {
                {c5, c2, c8},
                {c4, c1, c7},
                {c6, c3, c9},},
            {
                {c15, c12, c18},
                {c14, c11, c17},
                {c16, c13, c19},},
            {
                {c25, c22, c28},
                {c24, c21, c27},
                {c26, c23, c29},}
        };
        setCubeSideCol(cubes, true);
    }

    public static void setCubeSideCol(Cube[] cubes, boolean set) {
        for (Cube cube1 : cubes) {
            for (Cube cube2 : cubes) {
                if (cube1 != cube2) {
                    for (Rectangle c1Side : cube1.getSides()) {
                        for (Rectangle c2Side : cube2.getSides()) {
                            if (Math.abs(c1Side.averagePoint().x - c2Side.averagePoint().x) < 1 && Math.abs(c1Side.averagePoint().y - c2Side.averagePoint().y) < 1 && Math.abs(c1Side.averagePoint().z - c2Side.averagePoint().z) < 1) {
                                c1Side.setColor(Color.BLACK);
                                c2Side.setColor(Color.BLACK);
                                c1Side.setDrawn(set);
                                c2Side.setDrawn(set);
                            }
                        }
                    }
                }
            }
        }
    }

    private static void getDrawOrder() {

        drawOrder = new ArrayList();
        ArrayList<Rectangle> allSides = new ArrayList();
        for (Cube cube : Rubik.cubes) {
            for (Rectangle side : cube.getSides()) {
                allSides.add(side);
            }
        }

        for (Rectangle side : allSides) {
            boolean added = false;
            if (drawOrder.isEmpty()) {
                drawOrder.add(side);
            } else {
                for (int i = 0; i < drawOrder.size(); i++) {
                    if (side.getCamDist() > drawOrder.get(i).getCamDist()) {
                        drawOrder.add(i, side);
                        added = true;
                        break;
                    }
                }
                if (!added) {
                    drawOrder.add(side);
                }
            }
        }
    }

    public static void rotateCubes(double rads) {
        for (Cube cube : cubes) {
            switch (CubeTest.rotationMode) {
                case 1:
                    cube.rotate1(rads);
                    break;
                case 2:
                    cube.rotate2(rads);
                    break;
                case 3:
                    cube.rotate3(rads);
                    break;
            }
        }
    }

    public static void runRubik(Graphics g) {
        getDrawOrder();
        for (Rectangle rect : drawOrder) {
            rect.draw(g);
        }

        for (Cube cube : cubes) {
            cube.draw(g, radians);
        }
        rotateCubes(CubeTest.rotation);
    }

    public static boolean rotateBottom(boolean isInstant) {
        Cube[] selection = new Cube[]{
            cubePos[0][2][0], cubePos[0][2][1], cubePos[0][2][2],
            cubePos[1][2][0], cubePos[1][2][1], cubePos[1][2][2],
            cubePos[2][2][0], cubePos[2][2][1], cubePos[2][2][2]
        };

        boolean isDone = false;
        double rotation = !isInstant ? radians > 0.04 ? 0.04 : radians : radians;
        
        for (Cube cube : selection) {
            cube.rotate1(rotation);
        }

        radians -= rotation;
        isDone = radians <= 0 ? true : false;
        if (isDone) {
            radians = Math.PI / 2;
            cubePos[2][2][0] = selection[0];
            cubePos[2][2][2] = selection[6];
            cubePos[0][2][2] = selection[8];
            cubePos[0][2][0] = selection[2];
            cubePos[1][2][0] = selection[1];
            cubePos[2][2][1] = selection[3];
            cubePos[1][2][2] = selection[7];
            cubePos[0][2][1] = selection[5];
        }
        return isDone;
    }

    public static boolean rotateCenter(boolean isInstant) {
        Cube[] selection = new Cube[]{
            cubePos[0][1][0], cubePos[0][1][1], cubePos[0][1][2],
            cubePos[1][1][0], cubePos[1][1][1], cubePos[1][1][2],
            cubePos[2][1][0], cubePos[2][1][1], cubePos[2][1][2]
        };

        boolean isDone = false;
        double rotation = !isInstant ? radians > 0.04 ? 0.04 : radians : radians;

        for (Cube cube : selection) {
            cube.rotate1(rotation);
        }

        radians -= rotation;
        isDone = radians <= 0 ? true : false;
        if (isDone) {
            radians = Math.PI / 2;
            cubePos[2][1][0] = selection[0];
            cubePos[2][1][2] = selection[6];
            cubePos[0][1][2] = selection[8];
            cubePos[0][1][0] = selection[2];
            cubePos[1][1][0] = selection[1];
            cubePos[2][1][1] = selection[3];
            cubePos[1][1][2] = selection[7];
            cubePos[0][1][1] = selection[5];
        }
        return isDone;
    }

    public static boolean rotateTop(boolean isInstant) {
        Cube[] selection = new Cube[]{
            cubePos[0][0][0], cubePos[0][0][1], cubePos[0][0][2],
            cubePos[1][0][0], cubePos[1][0][1], cubePos[1][0][2],
            cubePos[2][0][0], cubePos[2][0][1], cubePos[2][0][2]
        };

        boolean isDone = false;
        double rotation = !isInstant ? radians > 0.04 ? 0.04 : radians : radians;

        for (Cube cube : selection) {
            cube.rotate1(rotation);
        }

        radians -= rotation;
        isDone = radians <= 0 ? true : false;
        if (isDone) {
            radians = Math.PI / 2;
            cubePos[2][0][0] = selection[0];
            cubePos[2][0][2] = selection[6];
            cubePos[0][0][2] = selection[8];
            cubePos[0][0][0] = selection[2];
            cubePos[1][0][0] = selection[1];
            cubePos[2][0][1] = selection[3];
            cubePos[1][0][2] = selection[7];
            cubePos[0][0][1] = selection[5];
        }
        return isDone;
    }

    public static boolean rotateLeft(boolean isInstant) {
        Cube[] selection = new Cube[]{
            cubePos[0][0][2], cubePos[1][0][2], cubePos[2][0][2],
            cubePos[0][1][2], cubePos[1][1][2], cubePos[2][1][2],
            cubePos[0][2][2], cubePos[1][2][2], cubePos[2][2][2]
        };

        boolean isDone = false;
        double rotation = !isInstant ? radians > 0.04 ? 0.04 : radians : radians;

        for (Cube cube : selection) {
            cube.rotate2(rotation);
        }

        radians -= rotation;
        isDone = radians <= 0 ? true : false;
        if (isDone) {
            radians = Math.PI / 2;
            cubePos[2][0][2] = selection[0];
            cubePos[0][0][2] = selection[6];
            cubePos[0][2][2] = selection[8];
            cubePos[2][2][2] = selection[2];
            cubePos[2][1][2] = selection[1];
            cubePos[1][0][2] = selection[3];
            cubePos[0][1][2] = selection[7];
            cubePos[1][2][2] = selection[5];
        }
        return isDone;
    }
}
