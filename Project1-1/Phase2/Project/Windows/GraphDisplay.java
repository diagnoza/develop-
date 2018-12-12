import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.lang.Math;
import java.util.ArrayList;
import javax.swing.*;
public class GraphDisplay extends JComponent{

	public static boolean isRed = false;
    public static int node1, node2;

    public GraphDisplay(){
        //creates new JComponent
        super();
        //creates new MouseListener that checks which node of the graph is clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                //go through all the nodes
                for(int i=0;i<GraphFrame.test.getVertices();i++){

                    //check if the point clicked by mouse is within the area of any of the nodes
                    if(GraphFrame.verticesGraphically[i].contains(e.getX(),e.getY())){

                        //sets the color of node "i" to the currently selected color in GraphFrame
                        GraphFrame.colorArray[i]=GraphFrame.colorIndex;
                        ArrayList<Integer> diffNum = new ArrayList<>();

                        for(int j=0; j<GraphFrame.colorArray.length; j++){
                            if(!diffNum.contains(GraphFrame.colorArray[j])){
                                diffNum.add(GraphFrame.colorArray[j]);
                            }
                        }

                        GraphFrame.colorsCounter = diffNum.size() - 1;


                        //generates and displays a new graph after making changes to the node
                        GraphFrame.componentGraph = new GraphDisplay();

                        //removing the old graph and displaying the new one
                        GraphFrame.graphWindow.repaint();
                        GraphFrame.graphWindow.revalidate();
                    }
                }
								boolean isColored = true;
								for(int i=0;i<GraphFrame.test2.length;i++){
									if(GraphFrame.colorArray[i]==0){
										isColored = false;
										break;
									}
								}
								if(isColored)
									GraphFrame.Hint2.setEnabled(false);
            }
        });
    }
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        double degree = 0;
        int vertices = GraphFrame.test.vertices;
        //creates all nodes of the graph
        for(int i=0;i<vertices;i++){
            GraphFrame.verticesGraphically[i]= new Ellipse2D.Double(275+200*Math.sin(degree),300+200*Math.cos(degree),50,50);

            degree+=(2*Math.PI/vertices);
        }
        g2.setStroke(new BasicStroke(2.0F));
        for(int i=0;i<vertices;i++){
            for(int j=0;j<vertices;j++){
                if(GraphFrame.test2[i][j]){
                    if(GraphFrame.colorArray[i]==GraphFrame.colorArray[j]&&GraphFrame.colorArray[i]!=0){
                    	node1 = i;
                    	node2 = j;
                    	isRed = true;
                    	GraphFrame.Hint2.setEnabled(true);
                    	g2.setColor(Color.RED);
                    }
                    else g2.setColor(Color.BLACK);
                    g2.draw(new Line2D.Double(GraphFrame.verticesGraphically[i].getX()+25,GraphFrame.verticesGraphically[i].getY()+25,GraphFrame.verticesGraphically[j].getX()+25,GraphFrame.verticesGraphically[j].getY()+25));
                }
            }
        }
        for(int i=0;i<vertices;i++){
            g2.setColor(GraphFrame.colors[GraphFrame.colorArray[i]].getBackground());
            g2.fill(GraphFrame.verticesGraphically[i]);
            g2.setStroke(new BasicStroke(4.5F));
            g2.setColor(Color.BLACK);
            g2.draw(GraphFrame.verticesGraphically[i]);
            g2.setStroke(new BasicStroke(4.0F));
        }
    }
}
