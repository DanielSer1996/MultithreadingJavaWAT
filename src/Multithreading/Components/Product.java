package Multithreading.Components;

/**
 * Created by daniel on 29.05.17.
 */
public class Product {
    private FirstComponent firstComponent;
    private SecondComponent secondComponent;
    private int height;

    public Product(FirstComponent firstComponent, SecondComponent secondComponent){
        this.firstComponent = firstComponent;
        this.secondComponent = secondComponent;
        this.height = firstComponent.getHeight() + secondComponent.getHeight();
    }

    public int getHeight() {
        return height;
    }

    public FirstComponent getFirstComponent() {
        return firstComponent;
    }

    public SecondComponent getSecondComponent() {
        return secondComponent;
    }
}
