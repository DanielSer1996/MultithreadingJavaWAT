package Multithreading.ProductionLine;

import Multithreading.Components.FirstComponent;
import Multithreading.Components.Product;
import Multithreading.Components.SecondComponent;
import Multithreading.Magazines.ComponentsMagazines;
import Multithreading.Magazines.ProductMagazine;
import Multithreading.MainPanel;

import static java.lang.Thread.sleep;

/**
 * Created by daniel on 27.05.17.
 */
public class ProductionLine implements Runnable {
    private ComponentsMagazines componentsMagazines;
    private FirstComponent firstComponent;
    private SecondComponent secondComponent;
    private ProductMagazine productMagazine;
    private int x, y;
    private double percent;
    private Product product;
    private MainPanel panel;
    private int startingUpperY, startingLowerY;

    public ProductionLine(MainPanel panel,ComponentsMagazines componentsMagazines,ProductMagazine productMagazine, int x, int y){
        this.panel = panel;
        this.componentsMagazines = componentsMagazines;
        this.productMagazine = productMagazine;
        this.x = x;
        this.y = y;
        this.startingUpperY = this.y + 60;
        this.startingLowerY = this.y + 100;
    }

    public FirstComponent getFirstComponent() {
        return firstComponent;
    }

    public SecondComponent getSecondComponent() {
        return secondComponent;
    }

    public double getPercent(){
        return this.percent;
    }

    private void calculatePercentage(){
        this.percent = (double)((((this.startingLowerY - this.startingUpperY)-(this.firstComponent.getY()-this.secondComponent.getY()+this.secondComponent.getHeight())/(this.startingLowerY - this.startingUpperY)))*100);
    }

    private void produce(){
        while(secondComponent.getY()+secondComponent.getHeight() < firstComponent.getY()){
            firstComponent.setY(firstComponent.getY() - 1);
            secondComponent.setY(secondComponent.getY() + 1);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            calculatePercentage();
            System.out.println(percent);
        }
        this.product = new Product(firstComponent,secondComponent);
    }

    @Override
    public void run() {
        while (true) {
            firstComponent = componentsMagazines.takeFromMagazineFirst();
            firstComponent.setX(this.x);
            firstComponent.setY(this.y + 100);
            secondComponent = componentsMagazines.takeFromMagazineSecond();
            secondComponent.setX(this.x+10);
            secondComponent.setY(this.y);
            produce();
            productMagazine.putInto(product);
            panel.incrementNumberOfProducts();
            firstComponent = null;
            secondComponent = null;
            product = null;
        }
    }
}
