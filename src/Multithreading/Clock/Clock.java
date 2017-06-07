package Multithreading.Clock;

import Multithreading.Trucks.Customer;

import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * Created by daniel on 29.05.17.
 */
public class Clock implements Runnable {
    private int seconds;
    private int randomTime;
    private Customer customer;

    public Clock(Customer customer){
        this.customer = customer;
        this.seconds = 0;
        this.randomTime = new Random().nextInt(10)+5;
    }
    @Override
    public void run() {
        while(true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds++;
            if(seconds == randomTime){
                customer.semaphoreUp();
                this.randomTime = new Random().nextInt(10)+5;
                seconds = 0;
            }
        }
    }
}
