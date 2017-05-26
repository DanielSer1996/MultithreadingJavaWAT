import Multithreading.MainPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Daniel on 22.05.2017.
 */
public class Main {
    public static void main(String[]args){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame window = new JFrame("Multithreading");
        MainPanel mainPanel = new MainPanel();
        window.add(mainPanel);
        window.setBounds((int)(screenSize.getWidth()/4),(int)(screenSize.getHeight()/10),800,600);
        window.setResizable(false);
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
