package Windows;
import java.awt.*;
import java.awt.geom.*;
import java.lang.Math;
import javax.swing.*;
public class GraphDisplay extends JComponent{
    public void paintComponent(Graphics g){
        RandomOrder test = new RandomOrder(SecondFrame.z,SecondFrame.y);
        boolean[][] test2 = test.createGraph();
        Graphics2D g2 = (Graphics2D) g;
        double degree = 0;
        int vertices = test.vertices;
        Ellipse2D[] verticesGraphically = new Ellipse2D[test.edges];
        for(int i=0;i<vertices;i++){
            verticesGraphically[i]= new Ellipse2D.Double(500+200*Math.sin(degree),300+200*Math.cos(degree),50,50);
            
            degree+=(2*Math.PI/vertices);
        }
        for(int i=0;i<vertices;i++){
            for(int j=0;j<vertices;j++){
                if(test2[i][j]){
                    g2.draw(new Line2D.Double(verticesGraphically[i].getX()+25,verticesGraphically[i].getY()+25,verticesGraphically[j].getX()+25,verticesGraphically[j].getY()+25));
                }
            }
        }
        for(int i=0;i<vertices;i++){
            g2.draw(verticesGraphically[i]);
            g2.fill(verticesGraphically[i]);
        }
    }
}
