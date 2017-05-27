package Multithreading.Components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Daniel on 22.05.2017.
 */
public class SecondComponent {
    private Image imgSecond;
    private int x, y, width, height;

    public Image getImgSecond() {
        return imgSecond;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public SecondComponent(){
        this.imgSecond = loadImage();
        this.width = 15;
        this.height = 60;
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures/secondComponent.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
