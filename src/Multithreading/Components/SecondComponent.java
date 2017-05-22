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

    public SecondComponent(){
        this.imgSecond = loadImage();
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures\\secondComponent.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
