package Multithreading.Trucks;

import Multithreading.Components.Product;
import Multithreading.Magazines.ProductMagazine;
import Multithreading.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Created by daniel on 02.06.17.
 */
public class Customer implements Runnable{
    private int x, y, width, height;
    private Image img;
    private ProductMagazine productMagazine;
    private int packSize;
    private Multithreading.Clock.Clock clock;
    private ArrayList<Product> pack;
    private Semaphore waitForTurn;

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
        this.productMagazine = productMagazine;
        this.x = 801;
        this.img = loadImage();
        this.width = img.getWidth(panel);
        this.height = img.getHeight(panel);
        this.y = productMagazine.getY()+productMagazine.getHeight() - this.height-60;
        this.packSize = 12;
        this.clock = new Multithreading.Clock.Clock(this);
        this.pack = new ArrayList<>();
        this.waitForTurn = new Semaphore(0);
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

    public void semaphoreUp(){
        this.waitForTurn.release();
    }

    @Override
    public void run() {
        while(true) {
            try {
                waitForTurn.acquire();
                comeToMagazine();
                for (int i = 0; i < packSize && productMagazine.getProducts().size()>0; i++) {
                    pack.add(productMagazine.takeFrom());
                    sleep(50);
                }
                getOutFromMagazine();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
