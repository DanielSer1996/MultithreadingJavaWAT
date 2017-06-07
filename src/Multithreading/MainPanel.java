package Multithreading;

import Multithreading.Magazines.ComponentsMagazines;
import Multithreading.Magazines.ProductMagazine;
import Multithreading.ProductionLine.ProductionLine;
import Multithreading.Trucks.Customer;
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
import java.util.concurrent.Semaphore;

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
    private Customer customer;
    private Image backgroundImage;
    private int numberOfProducts;
    private Semaphore protectNumberOfProducts;

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
        this.componentsMagazines.setProviderFirst(providerFirst);
        this.componentsMagazines.setProviderSecond(providerSecond);
        this.numberOfProductionLines = 3;
        this.numberOfProducts = 0;
        this.protectNumberOfProducts = new Semaphore(1);
        this.productionLines = new ArrayList<>();
        addProductionLines();
        this.timer = new Timer(10,this);
        this.customer = new Customer(this,productMagazine);
        startThreads();
        this.timer.start();
    }

    public void incrementNumberOfProducts(){
        try{
            protectNumberOfProducts.acquire();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        numberOfProducts++;
        protectNumberOfProducts.release();
    }

    private void addProductionLines(){
        for(int i = 0; i < numberOfProductionLines; i++){
            productionLines.add(new ProductionLine(this,componentsMagazines,productMagazine, componentsMagazines.getxFirst() + componentsMagazines.getWidthFirst() + 50,20+i*150));
        }
    }

    private void startThreads(){
        new Thread(providerFirst).start();
        new Thread(providerSecond).start();
        for(ProductionLine pl: productionLines){
            new Thread(pl).start();
        }
        new Thread(customer).start();
    }

    private void paintMagazines(Graphics g){
        g.drawImage(componentsMagazines.getImgFirst(), componentsMagazines.getxFirst(), componentsMagazines.getyFirst(), this);
        g.drawImage(componentsMagazines.getImgSecond(), componentsMagazines.getxSecond(), componentsMagazines.getySecond(), this);
        g.drawImage(productMagazine.getImg(), productMagazine.getX(), productMagazine.getY(), this);
    }

    private void paintProviders(Graphics g){
        g.drawImage(providerFirst.getImg(), providerFirst.getTruckX(), providerFirst.getTruckY(), this);
        g.drawImage(providerSecond.getImg(), providerSecond.getTruckX(), providerSecond.getTruckY(), this);
    }

    private void paintFirstComponent(Graphics g){
        int row = 0;
        for (int i = 0; i < componentsMagazines.getMagazineFirst().size(); i++) {
            if(i % 5 == 0 && i != 0) row += 25;
            g.drawImage(componentsMagazines.getMagazineFirst().get(i).getImgFirst(), componentsMagazines.getxFirst() + 7 + (i % 5) * 38, componentsMagazines.getyFirst() + 8 + row, this);
        }
    }

    private void paintSecondComponent(Graphics g){
        int row = 0;
        for (int i = 0; i < componentsMagazines.getMagazineSecond().size(); i++) {
            if(i % 8 == 0 && i != 0) row += 70;
            g.drawImage(componentsMagazines.getMagazineSecond().get(i).getImgSecond(), componentsMagazines.getxSecond() + 12 + (i % 8) * 23, componentsMagazines.getySecond() + 8 + row, this);
        }
    }

    private void paintProductionLines(Graphics g){
        for (ProductionLine pl : productionLines) {
            if (pl.getFirstComponent() != null) {
                g.drawImage(pl.getFirstComponent().getImgFirst(), pl.getFirstComponent().getX(), pl.getFirstComponent().getY(), this);
                g.setColor(Color.white);
                g.setFont(new Font("Bold", 10, 20));
                g.drawString((int) pl.getPercent() + "%", pl.getFirstComponent().getX(), pl.getFirstComponent().getY() + pl.getFirstComponent().getHeight() + 20);
            }
            if (pl.getSecondComponent() != null) {
                g.drawImage(pl.getSecondComponent().getImgSecond(), pl.getSecondComponent().getX(), pl.getSecondComponent().getY(), this);
            }
        }
    }

    private void paintProducts(Graphics g){
        int row = 0;
        for (int i = 0; i < productMagazine.getProducts().size(); i++) {
            if (i % 3 == 0 && i != 0)
                row += productMagazine.getProducts().get(i).getFirstComponent().getHeight() + productMagazine.getProducts().get(i).getSecondComponent().getHeight() + 10;

            g.drawImage(productMagazine.getProducts().get(i).getSecondComponent().getImgSecond(), productMagazine.getX() + 25 + (i % 3) * 40, productMagazine.getY() + 15 + row, this);
        }
        row = 0;
        for (int i = 0; i < productMagazine.getProducts().size(); i++) {
            if (i % 3 == 0 && i != 0)
                row += productMagazine.getProducts().get(i).getFirstComponent().getHeight() + productMagazine.getProducts().get(i).getSecondComponent().getHeight() + 10;
            g.drawImage(productMagazine.getProducts().get(i).getFirstComponent().getImgFirst(), productMagazine.getX() + 15 + (i % 3) * 40, productMagazine.getY() + 15 + productMagazine.getProducts().get(i).getSecondComponent().getHeight() + row, this);
        }
    }

    private void paintCustomer(Graphics g){
        g.drawImage(customer.getImg(), customer.getX(), customer.getY(), this);
    }

    private void printManufacturedHummers(Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Bold", 10, 20));
        g.drawString("Manufactured hummers: " + numberOfProducts, 30, 30);
    }

    @Override
    public void paintComponent(Graphics g) {
        try {
            Toolkit.getDefaultToolkit().sync();
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, this);
            paintMagazines(g);
            paintProviders(g);
            paintFirstComponent(g);
            paintSecondComponent(g);
            paintProductionLines(g);
            paintProducts(g);
            paintCustomer(g);
            printManufacturedHummers(g);
        }catch(NullPointerException e){

        }catch (IndexOutOfBoundsException e){

        }
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
