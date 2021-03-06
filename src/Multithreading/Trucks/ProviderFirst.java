package Multithreading.Trucks;

import Multithreading.Components.FirstComponent;
import Multithreading.Magazines.ComponentsMagazines;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by Daniel on 22.05.2017.
 */
public class ProviderFirst implements Runnable{
    private Image img;
    private ComponentsMagazines componentsMagazines;
    private int truckX, truckY, truckWidth, truckHeight;
    private JPanel panel;
    private ArrayList<FirstComponent> pack;

    public ProviderFirst(ComponentsMagazines componentsMagazines, JPanel panel, int y){
        this.panel = panel;
        this.img = loadImage();
        this.componentsMagazines = componentsMagazines;
        this.truckWidth = img.getWidth(panel);
        this.truckHeight = img.getHeight(panel);
        this.truckX = -1 - truckWidth;
        this.truckY = y;
        pack = new ArrayList<>();
    }

    public Image getImg() {
        return img;
    }

    public int getTruckX() {
        return truckX;
    }

    public int getTruckY() {
        return truckY;
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures/firstComponentTruck.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void prepareToDeliver(){
        while(pack.size()<15){
            pack.add(new FirstComponent());
        }
    }

    public void comeToMagazine() {
        while (truckX + truckWidth + 10 < componentsMagazines.getxFirst()) {
            truckX++;
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAwayFromMagazine(){
        while(truckX + truckWidth + 1 > 0) {
            truckX--;
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
            prepareToDeliver();
            componentsMagazines.putIntoMagazineFirst(pack);
        }
    }
}
