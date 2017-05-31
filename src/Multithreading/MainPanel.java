package Multithreading;

import Multithreading.Magazines.ComponentsMagazines;
import Multithreading.Magazines.ProductMagazine;
import Multithreading.ProductionLine.ProductionLine;
import Multithreading.Trucks.ProviderFirst;
import Multithreading.Trucks.ProviderSecond;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Daniel on 22.05.2017.
 */
public class MainPanel extends JPanel implements ActionListener{
    private ComponentsMagazines componentsMagazines;
    private ProviderFirst providerFirst;
    private ProviderSecond providerSecond;
    private ArrayList<ProductionLine> productionLines;
    private int numberOfProductionLines;
    private Timer timer;
    private ProductMagazine productMagazine;
    private Image backgroundImage;

    public MainPanel(){
        this.backgroundImage = loadImage();
        this.setBounds(0,100,1000,590);
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.componentsMagazines = new ComponentsMagazines(this);
        this.providerFirst = new ProviderFirst(componentsMagazines,this, componentsMagazines.getyFirst()+30);
        this.providerSecond = new ProviderSecond(componentsMagazines,this,componentsMagazines.getySecond()+30);
        this.productMagazine = new ProductMagazine(this);
        componentsMagazines.setProviderFirst(providerFirst);
        componentsMagazines.setProviderSecond(providerSecond);
        numberOfProductionLines = 2;
        productionLines = new ArrayList<>();
        addProductionLines();
        timer = new Timer(10,this);
        timer.start();
        startThreads();
    }

    private void addProductionLines(){
        for(int i = 0; i < numberOfProductionLines; i++){
            productionLines.add(new ProductionLine(componentsMagazines,productMagazine, componentsMagazines.getxFirst() + componentsMagazines.getWidthFirst() + 50,20+i*150));
        }
    }

    private void startThreads(){
        new Thread(providerFirst).start();
        new Thread(providerSecond).start();
        for(ProductionLine pl: productionLines){
            new Thread(pl).start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Toolkit.getDefaultToolkit().sync();
        super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,this);
        g.drawImage(componentsMagazines.getImgFirst(), componentsMagazines.getxFirst(), componentsMagazines.getyFirst(),this);
        g.drawImage(componentsMagazines.getImgSecond(), componentsMagazines.getxSecond(),componentsMagazines.getySecond(),this);
        g.drawImage(providerFirst.getImg(), providerFirst.getTruckX(), providerFirst.getTruckY(),this);
        g.drawImage(providerSecond.getImg(),providerSecond.getTruckX(),providerSecond.getTruckY(),this);
        for(int i = 0; i < componentsMagazines.getMagazineFirst().size() && i < 5; i++){
            g.drawImage(componentsMagazines.getMagazineFirst().get(i).getImgFirst(),componentsMagazines.getxFirst()+7+i*38,componentsMagazines.getyFirst()+8,this);
        }
        for(int i = 5; i < componentsMagazines.getMagazineFirst().size(); i++){
            g.drawImage(componentsMagazines.getMagazineFirst().get(i).getImgFirst(),componentsMagazines.getxFirst()+7+(i-5)*38,componentsMagazines.getyFirst()+48,this);

        }
        for(int i = 0; i < componentsMagazines.getMagazineSecond().size(); i++){
            g.drawImage(componentsMagazines.getMagazineSecond().get(i).getImgSecond(),componentsMagazines.getxSecond()+12+i*23,componentsMagazines.getySecond()+8,this);
        }
        for(ProductionLine pl: productionLines) {
            if (pl.getFirstComponent() != null){
                g.drawImage(pl.getFirstComponent().getImgFirst(), pl.getFirstComponent().getX(), pl.getFirstComponent().getY(), this);
            }
            if (pl.getSecondComponent() != null){
                g.drawImage(pl.getSecondComponent().getImgSecond(), pl.getSecondComponent().getX(), pl.getSecondComponent().getY(), this);
            }
        }
        g.drawImage(productMagazine.getImg(),productMagazine.getX(),productMagazine.getY(),this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        this.repaint();
    }

    private Image loadImage(){
        try {
            return ImageIO.read(new File("textures/factoryBackground.png"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
