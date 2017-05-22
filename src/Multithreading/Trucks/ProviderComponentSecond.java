package Multithreading.Trucks;

import Multithreading.Magazines.SecondComponentMagazine;
import Multithreading.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by Daniel on 22.05.2017.
 */
public class ProviderComponentSecond implements Runnable {
    private Image img;
    private SecondComponentMagazine secondComponentMagazine;
    private int truckX, truckY, truckWidth, truckHeight;
    MainPanel panel;

    public ProviderComponentSecond(SecondComponentMagazine secondComponentMagazine, MainPanel panel){
        this.panel = panel;
        this.img = loadImage();
        this.secondComponentMagazine = secondComponentMagazine;
        this.truckX = -1 - truckWidth;
        this.truckY = 400;
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
            return ImageIO.read(new File("textures\\secondComponentTruck.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void comeToMagazine(){
        while(truckX + truckWidth + 10 < secondComponentMagazine.getX()){
            truckX++;
            panel.repaint();
        }
    }

    @Override
    public void run() {
        while(true){
            comeToMagazine();
        }

    }
}
