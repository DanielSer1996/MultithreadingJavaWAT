package Multithreading.Trucks;

import Multithreading.Components.FirstComponent;
import Multithreading.Components.SecondComponent;
import Multithreading.Magazines.ComponentsMagazines;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static java.lang.Thread.sleep;

/**
 * Created by Daniel on 22.05.2017.
 */
public class Provider implements Runnable{
    private Image img;
    private ComponentsMagazines componentsMagazines;
    private int truckX, truckY, truckWidth, truckHeight;
    private JPanel panel;

    public Provider(ComponentsMagazines componentsMagazines, JPanel panel, int y){
        this.panel = panel;
        this.img = loadImage();
        this.componentsMagazines = componentsMagazines;
        this.truckWidth = img.getWidth(panel);
        this.truckHeight = img.getHeight(panel);
        this.truckX = -1 - truckWidth;
        this.truckY = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public int getTruckX() {
        return truckX;
    }

    public void setTruckX(int truckX) {
        this.truckX = truckX;
    }

    public int getTruckY() {
        return truckY;
    }

    public void setTruckY(int truckY) {
        this.truckY = truckY;
    }

    public int getTruckWidth() {
        return truckWidth;
    }

    public void setTruckWidth(int truckWidth) {
        this.truckWidth = truckWidth;
    }

    public int getTruckHeight() {
        return truckHeight;
    }

    public void setTruckHeight(int truckHeight) {
        this.truckHeight = truckHeight;
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures/firstComponentTruck.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void comeToMagazine(int n){
        if(n == 1) {
            truckY = componentsMagazines.getyFirst() + 30;
            while (truckX + truckWidth + 10 < componentsMagazines.getxFirst()) {
                truckX++;
                panel.repaint();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else if(n == 2){
            truckY = componentsMagazines.getySecond() + 30;
            while (truckX + truckWidth + 10 < componentsMagazines.getxSecond()) {
                truckX++;
                panel.repaint();
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void getAwayFromMagazine(){
        while(truckX + truckWidth + 1 > 0) {
            truckX--;
            panel.repaint();
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
            componentsMagazines.putIntoMagazineFirst(new FirstComponent());
            componentsMagazines.putIntoMagazineSecond(new SecondComponent());
        }
    }
}
