package Multithreading.ProductionLine;

import Multithreading.Components.FirstComponent;
import Multithreading.Components.SecondComponent;
import Multithreading.Magazines.ComponentsMagazines;

import static java.lang.Thread.sleep;

/**
 * Created by daniel on 27.05.17.
 */
public class ProductionLine implements Runnable {
    ComponentsMagazines componentsMagazines;
    FirstComponent firstComponent;
    SecondComponent secondComponent;
    int x, y;
    int percent;
    int number;

    public ProductionLine(ComponentsMagazines componentsMagazines, int x, int y){
        this.componentsMagazines = componentsMagazines;
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
            firstComponent = null;
            secondComponent = null;
        }
    }
}
