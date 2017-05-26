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

    public FirstComponent(){
        this.imgFirst = loadImage();
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
