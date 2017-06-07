package Multithreading.Trucks;

import Multithreading.Components.SecondComponent;
import Multithreading.Magazines.ComponentsMagazines;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by daniel on 27.05.17.
 */
public class ProviderSecond implements Runnable{
    private Image img;
    private ComponentsMagazines componentsMagazines;
    private int truckX, truckY, truckWidth, truckHeight;
    private JPanel panel;
    private ArrayList<SecondComponent> pack;

    public ProviderSecond(ComponentsMagazines componentsMagazines, JPanel panel, int y){
        this.panel = panel;
        this.img = loadImage();
        this.componentsMagazines = componentsMagazines;
        this.truckWidth = img.getWidth(panel);
        this.truckHeight = img.getHeight(panel);
        this.truckX = -1 - truckWidth;
        this.truckY = y;
        this.pack = new ArrayList<>();
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
        while(pack.size()<100){
            pack.add(new SecondComponent());
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
        while(true){
            prepareToDeliver();
            componentsMagazines.putIntoMagazineSecond(pack);
        }
    }
}
