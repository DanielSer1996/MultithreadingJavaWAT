package Multithreading.Components;

/**
 * Created by daniel on 29.05.17.
 */
public class Product {
    private FirstComponent firstComponent;
    private SecondComponent secondComponent;
    private int x, y, width, height;

    public Product(FirstComponent firstComponent, SecondComponent secondComponent){
        this.firstComponent = firstComponent;
        this.secondComponent = secondComponent;
        this.width = firstComponent.getWidth();
        this.height = firstComponent.getHeight() + secondComponent.getHeight();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public FirstComponent getFirstComponent() {
        return firstComponent;
    }

    public SecondComponent getSecondComponent() {
        return secondComponent;
    }
}
