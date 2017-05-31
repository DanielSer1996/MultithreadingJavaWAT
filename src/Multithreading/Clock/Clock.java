package Multithreading.Clock;

import static java.lang.Thread.sleep;

/**
 * Created by daniel on 29.05.17.
 */
public class Clock implements Runnable {
    private int seconds;

    public int getSeconds(){
        return this.seconds;
    }

    public void setSeconds(int seconds){
        this.seconds = seconds;
    }

    @Override
    public void run() {
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        seconds++;
    }
}
