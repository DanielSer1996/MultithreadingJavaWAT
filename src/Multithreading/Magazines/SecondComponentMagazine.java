package Multithreading.Magazines;

import Multithreading.Components.SecondComponent;
import Multithreading.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by Daniel on 22.05.2017.
 */
public class SecondComponentMagazine {
    private List<SecondComponent> magazine;
    private Semaphore takeFromMagazine, putIntoMagazine;
    private int x, y, width, height;
    private Image img;
    MainPanel panel;

    public SecondComponentMagazine(MainPanel panel){
        this.panel = panel;
        this.magazine = new ArrayList<>();
        takeFromMagazine = new Semaphore(0);
        putIntoMagazine = new Semaphore(10);
        this.img = loadImage();
        this.x = 200;
        this.y = 400;
        this.width = img.getWidth(panel);
        this.height = img.getHeight(panel);
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
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

    private void putIntoMagazine(SecondComponent secondComponent){
        try {
            putIntoMagazine.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        magazine.add(secondComponent);
        takeFromMagazine.release();
    }

    private SecondComponent takeFromMagazine(){
        SecondComponent n;
        try {
            takeFromMagazine.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        n = magazine.remove(0);
        if(magazine.size() == 0 && putIntoMagazine.hasQueuedThreads()){
            putIntoMagazine.release(10);
        }
        return n;
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures\\secondComponentMagazine.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
