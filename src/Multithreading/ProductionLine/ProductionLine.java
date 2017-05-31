package Multithreading.ProductionLine;

import Multithreading.Components.FirstComponent;
import Multithreading.Components.Product;
import Multithreading.Components.SecondComponent;
import Multithreading.Magazines.ComponentsMagazines;
import Multithreading.Magazines.ProductMagazine;

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
    private int percent;
    private Product product;

    public ProductionLine(ComponentsMagazines componentsMagazines,ProductMagazine productMagazine, int x, int y){
        this.componentsMagazines = componentsMagazines;
        this.productMagazine = productMagazine;
        this.x = x;
        this.y = y;
    }

    public FirstComponent getFirstComponent() {
        return firstComponent;
    }

    public SecondComponent getSecondComponent() {
        return secondComponent;
    }

    public int getPercent(){
        return this.percent;
    }

    private void calculatePercentage(){

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
            firstComponent = null;
            secondComponent = null;
            product = null;
        }
    }
}
