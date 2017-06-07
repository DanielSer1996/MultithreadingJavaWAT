package Multithreading.Magazines;

import Multithreading.Components.FirstComponent;
import Multithreading.Components.SecondComponent;
import Multithreading.MainPanel;
import Multithreading.Trucks.ProviderFirst;
import Multithreading.Trucks.ProviderSecond;

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
    private Semaphore protectSingleComponentFirst, protectSingleComponentSecond;
    private Semaphore carBusy;
    private Semaphore firstDeliverIsNow, secondDeliverIsNow;
    private Semaphore magazineFirstDeliver, magazineSecondDeliver;
    private int xFirst, yFirst, widthFirst, heightFirst;
    private int xSecond, ySecond, widthSecond, heightSecond;
    private Image imgFirst;
    private Image imgSecond;
    private MainPanel panel;
    private ProviderFirst providerFirst;
    private ProviderSecond providerSecond;
    private boolean wantComponentFirst, wantComponentSecond;

    public ComponentsMagazines(MainPanel panel){
        this.panel = panel;
        this.magazineFirst = new ArrayList<>();
        this.magazineSecond = new ArrayList<>();
        this.takeFromMagazineFirst = new Semaphore(0,true);
        this.putIntoMagazineFirst = new Semaphore(1);
        this.takeFromMagazineSecond = new Semaphore(0,true);
        this.putIntoMagazineSecond = new Semaphore(1);
        this.protectSingleComponentFirst = new Semaphore(1);
        this.protectSingleComponentSecond = new Semaphore(1);
        this.firstDeliverIsNow = new Semaphore(1);
        this.secondDeliverIsNow = new Semaphore(1);
        this.carBusy = new Semaphore(1);
        this.imgFirst = loadImage();
        this.imgSecond = loadImage();
        this.xFirst = 130;
        this.yFirst = 300;
        this.xSecond = 130;
        this.ySecond = 400;
        this.widthFirst = imgFirst.getWidth(panel);
        this.heightFirst = imgFirst.getHeight(panel);
        this.widthSecond = imgSecond.getWidth(panel);
        this.heightSecond = imgSecond.getHeight(panel);
        this.wantComponentFirst = false;
        this.wantComponentSecond = false;
    }

    public void setProviderFirst(ProviderFirst providerFirst){
        this.providerFirst = providerFirst;
    }

    public void setProviderSecond(ProviderSecond providerSecond){
        this.providerSecond = providerSecond;
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

    public void putIntoMagazineFirst(ArrayList<FirstComponent> firstComponent){
        try {
            putIntoMagazineFirst.acquire();
            carBusy.acquire();
            firstDeliverIsNow.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        providerFirst.comeToMagazine();
        while(magazineFirst.size() < 15 && firstComponent.size()>0) {
            magazineFirst.add(firstComponent.remove(0));
            takeFromMagazineFirst.release();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wantComponentFirst = true;
        firstDeliverIsNow.release();
        providerFirst.getAwayFromMagazine();
        carBusy.release();
    }

    public FirstComponent takeFromMagazineFirst(){
        FirstComponent n;
        try {
            takeFromMagazineFirst.acquire();
            firstDeliverIsNow.acquire();
            protectSingleComponentFirst.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        n = magazineFirst.remove(0);
        protectSingleComponentFirst.release();
        if(wantComponentFirst && magazineFirst.size() < 2){
            putIntoMagazineFirst.release();
            wantComponentFirst = false;
        }
        firstDeliverIsNow.release();
        return n;
    }

    public void putIntoMagazineSecond(ArrayList<SecondComponent> secondComponent){
        try {
            putIntoMagazineSecond.acquire();
            carBusy.acquire();
            secondDeliverIsNow.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        providerSecond.comeToMagazine();
        while(magazineSecond.size() < 8 && secondComponent.size()>0) {
            magazineSecond.add(secondComponent.remove(0));
            takeFromMagazineSecond.release();
            try {
                sleep(110);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        wantComponentSecond = true;
        secondDeliverIsNow.release();
        providerSecond.getAwayFromMagazine();
        carBusy.release();
    }

    public SecondComponent takeFromMagazineSecond(){
        SecondComponent n;
        try {
            takeFromMagazineSecond.acquire();
            secondDeliverIsNow.acquire();
            protectSingleComponentSecond.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        n = magazineSecond.remove(0);
        protectSingleComponentSecond.release();
        if(wantComponentSecond && magazineSecond.size() < 2){
            putIntoMagazineSecond.release();
            wantComponentSecond = false;
        }
        secondDeliverIsNow.release();
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
