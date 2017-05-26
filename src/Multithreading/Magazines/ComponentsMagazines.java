package Multithreading.Magazines;

import Multithreading.Components.FirstComponent;
import Multithreading.Components.SecondComponent;
import Multithreading.MainPanel;
import Multithreading.Trucks.Provider;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * Created by Daniel on 22.05.2017.
 */
public class ComponentsMagazines {
    private List<FirstComponent> magazineFirst;
    private List<SecondComponent> magazineSecond;
    private Semaphore takeFromMagazineFirst, putIntoMagazineFirst;
    private Semaphore takeFromMagazineSecond, putIntoMagazineSecond;
    private Semaphore carBusy;
    private int xFirst, yFirst, widthFirst, heightFirst;
    private int xSecond, ySecond, widthSecond, heightSecond;
    private Image imgFirst;
    private Image imgSecond;
    private MainPanel panel;
    Provider provider;

    public ComponentsMagazines(MainPanel panel){
        this.panel = panel;
        this.magazineFirst = new ArrayList<>();
        this.magazineSecond = new ArrayList<>();
        this.takeFromMagazineFirst = new Semaphore(0);
        this.putIntoMagazineFirst = new Semaphore(1);
        this.takeFromMagazineSecond = new Semaphore(0);
        this.putIntoMagazineSecond = new Semaphore(1);
        this.carBusy = new Semaphore(1);
        this.imgFirst = loadImage();
        this.imgSecond = loadImage();
        this.xFirst = 200;
        this.yFirst = 40;
        this.xSecond = 200;
        this.ySecond = 400;
        this.widthFirst = imgFirst.getWidth(panel);
        this.heightFirst = imgFirst.getHeight(panel);
        this.widthSecond = imgSecond.getWidth(panel);
        this.heightSecond = imgSecond.getHeight(panel);
    }

    public void setProvider(Provider provider){
        this.provider = provider;
    }

    public int getxFirst() {
        return xFirst;
    }

    public void setxFirst(int xFirst) {
        this.xFirst = xFirst;
    }

    public int getyFirst() {
        return yFirst;
    }

    public void setyFirst(int yFirst) {
        this.yFirst = yFirst;
    }

    public int getWidthFirst() {
        return widthFirst;
    }

    public void setWidthFirst(int widthFirst) {
        this.widthFirst = widthFirst;
    }

    public int getHeightFirst() {
        return heightFirst;
    }

    public void setHeightFirst(int heightFirst) {
        this.heightFirst = heightFirst;
    }

    public Image getImgFirst() {
        return imgFirst;
    }

    public void setImgFirst(Image imgFirst) {
        this.imgFirst = imgFirst;
    }

    public int getxSecond() {
        return xSecond;
    }

    public void setxSecond(int xSecond) {
        this.xSecond = xSecond;
    }

    public int getySecond() {
        return ySecond;
    }

    public void setySecond(int ySecond) {
        this.ySecond = ySecond;
    }

    public int getWidthSecond() {
        return widthSecond;
    }

    public void setWidthSecond(int widthSecond) {
        this.widthSecond = widthSecond;
    }

    public int getHeightSecond() {
        return heightSecond;
    }

    public void setHeightSecond(int heightSecond) {
        this.heightSecond = heightSecond;
    }

    public Image getImgSecond() {
        return imgSecond;
    }

    public void setImgSecond(Image imgSecond) {
        this.imgSecond = imgSecond;
    }

    public List<FirstComponent> getMagazineFirst() {
        return magazineFirst;
    }

    public List<SecondComponent> getMagazineSecond() {
        return magazineSecond;
    }

    public void putIntoMagazineFirst(FirstComponent firstComponent){
        try {
            putIntoMagazineFirst.acquire();
            carBusy.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        provider.comeToMagazine(1);
        while(magazineFirst.size() < 10) {
            magazineFirst.add(firstComponent);
            takeFromMagazineFirst.release();
            panel.repaint();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        provider.getAwayFromMagazine();
        carBusy.release();
    }

    public FirstComponent takeFromMagazineFirst(){
        FirstComponent n;
        try {
            takeFromMagazineFirst.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        n = magazineFirst.remove(0);
        if(magazineFirst.size() < 2){
            putIntoMagazineFirst.release();
        }
        return n;
    }

    public void putIntoMagazineSecond(SecondComponent secondComponent){
        try {
            putIntoMagazineSecond.acquire();
            carBusy.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        provider.comeToMagazine(2);
        while(magazineSecond.size() < 8) {
            magazineSecond.add(secondComponent);
            takeFromMagazineSecond.release();
            panel.repaint();
            try {
                sleep(110);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        provider.getAwayFromMagazine();
        carBusy.release();
    }

    public SecondComponent takeFromMagazineSecond(){
        SecondComponent n;
        try {
            takeFromMagazineSecond.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        n = magazineSecond.remove(0);
        if(magazineSecond.size() < 2){
            putIntoMagazineSecond.release();
        }
        return n;
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures/firstComponentMagazine.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}