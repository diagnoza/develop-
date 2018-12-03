package Windows;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.Math;
import javax.swing.*;
public class GraphDisplay extends JComponent{
    private boolean isFirst;
    private int node;
    private int color;
    public GraphDisplay(boolean isFirst){
        super();
        this.isFirst = isFirst;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                for(int i=0;i<5;i++){
                    if(verticesGraphically[i].contains(e.getX(),e.getY())){
                        GraphFrame.componentGraph = new GraphDisplay(i);
                        GraphFrame.graphWindow.revalidate();
                        GraphFrame.graphWindow.repaint();
                        GraphFrame.graphWindow.revalidate();
                    }
                }
            }
        });
    } 
    public GraphDisplay(int node){
        super();
        this.node = node;
        //this.color = color;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                for(int i=0;i<SecondFrame.z;i++){
                    if(verticesGraphically[i].contains(e.getX(),e.getY())){
                        GraphFrame.componentGraph = new GraphDisplay(i);
                        GraphFrame.graphWindow.revalidate();
                        GraphFrame.graphWindow.repaint();
                        GraphFrame.graphWindow.revalidate();
                    }
                }
            }
        });
    }
    public Ellipse2D[] verticesGraphically = new Ellipse2D[GraphFrame.test.edges];
    public static int[] colorArray = new int[20];
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        double degree = 0;
        int vertices = GraphFrame.test.vertices;
        for(int i=0;i<vertices;i++){
            verticesGraphically[i]= new Ellipse2D.Double(275+200*Math.sin(degree),300+200*Math.cos(degree),50,50);
            
            degree+=(2*Math.PI/vertices);
        }
        for(int i=0;i<vertices;i++){
            for(int j=0;j<vertices;j++){
                if(GraphFrame.test2[i][j]){
                    g2.draw(new Line2D.Double(verticesGraphically[i].getX()+25,verticesGraphically[i].getY()+25,verticesGraphically[j].getX()+25,verticesGraphically[j].getY()+25));
                }
            }
        }
        for(int i=0;i<vertices;i++){
            if(!isFirst) g2.setColor(GraphFrame.colors[colorArray[i]].getBackground());
            g2.draw(verticesGraphically[i]);
            g2.fill(verticesGraphically[i]);
        }
    }
}
