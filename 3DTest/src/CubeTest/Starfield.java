/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CubeTest;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author michael.doan3
 */
public class Starfield {
    private Star[] stars;
    
    public Starfield(int width, int height, int numOfStars){
        stars = new Star[numOfStars];
        for (int i = 0; i < stars.length; i++) {
            int gs = (int)(Math.random() * 256);
            stars[i] = new Star((int) (Math.random() * width), (int) (Math.random() * height), new Color(gs, gs, gs));
        }
    }
    
    public Starfield(int x, int y, int width, int height, int numOfStars){
        stars = new Star[numOfStars];
        for (int i = 0; i < stars.length; i++) {
            int gs = (int)(Math.random() * 256);
            stars[i] = new Star((int) (Math.random() * width) + x, (int) (Math.random() * height) + y, new Color(gs, gs, gs));
        }
    }
    
    public void twinkle() {
        for (Star star : stars) {
            if ((int) (Math.random() * 1000) == 5) {
                int gs = (int) (Math.random() * 256);
                star.setColor(new Color(gs, gs, gs));
            }
        }
    }
    
    public void draw(Graphics g) {
        for (Star star : stars) {
            star.draw(g);
        }
    }
}
