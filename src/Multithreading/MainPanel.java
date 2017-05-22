package Multithreading;

import Multithreading.Magazines.FirstComponentMagazine;
import Multithreading.Magazines.SecondComponentMagazine;
import Multithreading.Trucks.ProviderComponentFirst;
import Multithreading.Trucks.ProviderComponentSecond;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Daniel on 22.05.2017.
 */
public class MainPanel extends JPanel{
    private FirstComponentMagazine firstComponentMagazine;
    private SecondComponentMagazine secondComponentMagazine;
    private ProviderComponentFirst providerComponentFirst;
    private ProviderComponentSecond providerComponentSecond;

    public MainPanel(){
        this.setBounds(0,100,1000,590);
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.firstComponentMagazine = new FirstComponentMagazine(this);
        this.secondComponentMagazine = new SecondComponentMagazine(this);
        this.providerComponentFirst = new ProviderComponentFirst(firstComponentMagazine,this);
        this.providerComponentSecond = new ProviderComponentSecond(secondComponentMagazine,this);
        startThreads();
    }

    private void startThreads(){
        new Thread(providerComponentFirst);
        new Thread(providerComponentSecond);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(firstComponentMagazine.getImg(),firstComponentMagazine.getX(),firstComponentMagazine.getY(),this);
        g.drawImage(secondComponentMagazine.getImg(),secondComponentMagazine.getX(),secondComponentMagazine.getY(),this);
        g.drawImage(providerComponentFirst.getImg(),providerComponentFirst.getTruckX(),providerComponentFirst.getTruckY(),this);
        g.drawImage(providerComponentSecond.getImg(),providerComponentSecond.getTruckX(),providerComponentSecond.getTruckY(),this);
    }
}
