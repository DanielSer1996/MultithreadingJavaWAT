package Multithreading.Magazines;

import Multithreading.Components.Product;
import Multithreading.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

/**
 * Created by daniel on 29.05.17.
 */
public class ProductMagazine {
    private ArrayList<Product> products;
    private Image img;
    private int x, y, width, height;
    private Semaphore putIntoMagazine, takeFromMagazine;
    private Semaphore addOneInTime, takeOneInTime;
    private MainPanel panel;

    public ProductMagazine(MainPanel panel){
        this.panel = panel;
        this.putIntoMagazine = new Semaphore(18);
        this.takeFromMagazine = new Semaphore(0);
        this.addOneInTime = new Semaphore(1);
        this.takeOneInTime = new Semaphore(1);
        this.products = new ArrayList<>();
        this.img = loadImage();
        this.x = 450;
        this.y = 10;
        this.width = img.getWidth(panel);
        this.height = img.getHeight(panel);
    }

    public void putInto(Product product){
        try {
            this.putIntoMagazine.acquire();
            this.addOneInTime.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        products.add(product);
        takeFromMagazine.release();
        addOneInTime.release();
    }

    public Product takeFrom(){
        Product n;
        try {
            takeFromMagazine.acquire();
            takeOneInTime.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        n = products.remove(0);
        putIntoMagazine.release();
        takeOneInTime.release();
        return n;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
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

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures/productMagazine.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
