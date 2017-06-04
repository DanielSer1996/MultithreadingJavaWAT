package Multithreading.Trucks;

import Multithreading.Components.Product;
import Multithreading.Magazines.ProductMagazine;
import Multithreading.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by daniel on 02.06.17.
 */
public class Customer implements Runnable{
    private int x, y, width, height;
    private Image img;
    private MainPanel panel;
    private ProductMagazine productMagazine;
    private int packSize;
    private boolean isTimeToRand;
    private int comingDelay;
    private Multithreading.Clock.Clock clock;
    private ArrayList<Product> pack;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }

    public Customer(MainPanel panel, ProductMagazine productMagazine){
        this.panel = panel;
        this.productMagazine = productMagazine;
        this.x = 801;
        this.img = loadImage();
        this.width = img.getWidth(panel);
        this.height = img.getHeight(panel);
        this.y = productMagazine.getY()+productMagazine.getHeight() - this.height-60;
        this.packSize = 9;
        isTimeToRand = true;
        this.clock = new Multithreading.Clock.Clock();
        this.pack = new ArrayList<>();
        new Thread(clock).start();
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures/consumerTruck.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void randomTime(){
        this.comingDelay = new Random().nextInt(10)+5;
        this.isTimeToRand = false;
    }

    private void comeToMagazine(){
        while(this.x > productMagazine.getX()+productMagazine.getWidth()+10){
            this.x--;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getOutFromMagazine(){
        while(this.x < 801){
            this.x++;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while(true) {
            if (isTimeToRand) {
                randomTime();
            }
            if (clock.getSeconds() == comingDelay) {
                comeToMagazine();
                for (int i = 0; i < packSize && productMagazine.getProducts().size()>0; i++) {
                    pack.add(productMagazine.takeFrom());
                    try {
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getOutFromMagazine();
                clock.setSeconds(0);
                isTimeToRand = true;
            }
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
