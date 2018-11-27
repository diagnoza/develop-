import javax.swing.*;
public class GraphDisplayTest{
public static void main(String[] args){
    JFrame displayedGraphFrame = new JFrame("Try displaying graph");
    displayedGraphFrame.setSize(1000,600);
    displayedGraphFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GraphDisplay componentGraph = new GraphDisplay();
    displayedGraphFrame.add(componentGraph);
    displayedGraphFrame.setResizable(false);
    displayedGraphFrame.setVisible(true);
}
}