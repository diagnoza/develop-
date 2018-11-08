import java.io.*;
import java.util.*;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class ReadGraph
		{
		
		public static void main( String args[] )
			{

            Scanner in = new Scanner(System.in);
            RandomOrder randomGameMode = new RandomOrder(in.nextInt(),in.nextInt());
            //ColEdge e[] = new ColEdge[randomGameMode.edges];
            boolean[][] randomGraph = randomGameMode.createGraph();
            int[] colouredGraph = new int[randomGameMode.vertices];
            int userColor = null;
            int currentVertex = 0;
            boolean used = false;
            while(currentVertex<randomGameMode.vertices){
                userColor = in.nextInt();
                for(int i=0;i<randomGameMode.vertices;i++)
                    if(colouredGraph[i]==userColor&&randomGraph[currentVertex][i]==true){
                        System.out.println("Sorry, this colour cannot be used! Try another one.");
                        used = true;
                    }
                if(!used){
                    colouredGraph[current]=userColor;
                    currentVertex++;
                } 
            }
            /*int edgeNumber=0;
            for(int i=0;i<randomGameMode.vertices;i++)
                for(int j=0;j<randomGameMode.vertices;i++)
                    if(randomGraph[i][j]){
                        e[edgeNumber].u = i;
                        e[edgeNumber].v = j;
                        edgeNumber++;
                    }*/
		}

}