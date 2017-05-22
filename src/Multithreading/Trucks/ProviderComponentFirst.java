package Multithreading.Trucks;

import Multithreading.Magazines.FirstComponentMagazine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Daniel on 22.05.2017.
 */
public class ProviderComponentFirst implements Runnable{
    private Image img;
    private FirstComponentMagazine firstComponentMagazine;
    private int truckX, truckY, truckWidth, truckHeight;
    private JPanel panel;

    public ProviderComponentFirst(FirstComponentMagazine firstComponentMagazine, JPanel panel){
        this.panel = panel;
        this.img = loadImage();
        this.firstComponentMagazine = firstComponentMagazine;
        this.truckX = -1 - truckWidth;
        this.truckY = 40;
        this.truckWidth = img.getWidth(panel);
        this.truckHeight = img.getHeight(panel);
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
            return ImageIO.read(new File("textures\\firstComponentTruck.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void comeToMagazine(){
        while(truckX + truckWidth + 10 < firstComponentMagazine.getX()){
            System.out.println("ruszam się");
            truckX++;
            panel.repaint();
        }
    }

    @Override
    public void run() {
        while(true) {
            comeToMagazine();
        }
    }
}
