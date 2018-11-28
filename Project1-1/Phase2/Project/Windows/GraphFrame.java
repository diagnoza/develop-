package Windows;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*; 

public class GraphFrame{
    public static void createWindow(){
        JFrame graphWindow = new JFrame("Display graph");
        graphWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphWindow.setSize(1000,600);
        GraphDisplay componentGraph = new GraphDisplay();
        graphWindow.add(componentGraph);
        graphWindow.setResizable(false);
        graphWindow.setVisible(true);
    }
}