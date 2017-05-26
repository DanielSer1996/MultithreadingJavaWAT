package Multithreading;

import Multithreading.Magazines.ComponentsMagazines;
import Multithreading.Trucks.Provider;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Daniel on 22.05.2017.
 */
public class MainPanel extends JPanel{
    private ComponentsMagazines componentsMagazines;
    private Provider provider;

    public MainPanel(){
        this.setBounds(0,100,1000,590);
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.componentsMagazines = new ComponentsMagazines(this);
        this.provider = new Provider(componentsMagazines,this, componentsMagazines.getyFirst()+30);
        componentsMagazines.setProvider(provider);
        startThreads();
    }

    private void startThreads(){
        new Thread(provider).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync();
        super.paintComponent(g);
        g.drawImage(componentsMagazines.getImgFirst(), componentsMagazines.getxFirst(), componentsMagazines.getyFirst(),this);
        g.drawImage(componentsMagazines.getImgSecond(), componentsMagazines.getxSecond(),componentsMagazines.getySecond(),this);
        g.drawImage(provider.getImg(), provider.getTruckX(), provider.getTruckY(),this);
        for(int i = 0; i < componentsMagazines.getMagazineFirst().size() && i < 5; i++){
            g.drawImage(componentsMagazines.getMagazineFirst().get(i).getImgFirst(),componentsMagazines.getxFirst()+8+i*35,componentsMagazines.getyFirst()+8,this);
        }
        for(int i = 5; i < componentsMagazines.getMagazineFirst().size(); i++){
            g.drawImage(componentsMagazines.getMagazineFirst().get(i).getImgFirst(),componentsMagazines.getxFirst()+8+(i-5)*35,componentsMagazines.getyFirst()+48,this);

        }

        for(int i = 0; i < componentsMagazines.getMagazineSecond().size() && i < 4; i++){
            g.drawImage(componentsMagazines.getMagazineSecond().get(i).getImgSecond(),componentsMagazines.getxSecond()+8+i*50,componentsMagazines.getySecond()+8,this);
        }
        for(int i = 4; i < componentsMagazines.getMagazineSecond().size(); i++){
            g.drawImage(componentsMagazines.getMagazineSecond().get(i).getImgSecond(),componentsMagazines.getxSecond()+8+(i-4)*50,componentsMagazines.getySecond()+58,this);
        }
    }
}
