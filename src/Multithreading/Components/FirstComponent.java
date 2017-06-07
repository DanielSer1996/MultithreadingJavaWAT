package Multithreading.Components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Daniel on 22.05.2017.
 */
public class FirstComponent {
    private Image imgFirst;
    private int x, y, width, height;

    public Image getImgFirst() {
        return imgFirst;
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

    public int getHeight() {
        return height;
    }

    public FirstComponent(){
        this.imgFirst = loadImage();
        this.width = 35;
        this.height = 18;
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures/firstComponent.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
