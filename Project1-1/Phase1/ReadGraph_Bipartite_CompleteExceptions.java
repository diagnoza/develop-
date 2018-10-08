import java.io.*;
import java.util.*;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class ReadGraph
		{
		
		public final static boolean DEBUG = true;
		
		public final static String COMMENT = "//";
		
		public static void main( String args[] )
			{
			if( args.length < 1 )
				{
				System.out.println("Error! No filename specified.");
				System.exit(0);
				}

				
			String inputfile = args[0];
			
			boolean seen[] = null;
			
			//! n is the number of vertices in the graph
			int n = -1;
			
			//! m is the number of edges in the graph
			int m = -1;
			
			//! e will contain the edges of the graph
			ColEdge e[] = null;
			
			try 	{ 
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();
					
					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
					//! These comments are only allowed at the top of the file.
					
					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}
	
					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );					
						if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];	
						
					record = br.readLine();
					
					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );					
						if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
						}

					e = new ColEdge[m];	
												
					for( int d=0; d<m; d++)
						{
						if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
								{
								System.out.println("Error! Malformed edge line: "+record);
								System.exit(0);
								}
						e[d] = new ColEdge();
						
						e[d].u = Integer.parseInt(data[0]);
						e[d].v = Integer.parseInt(data[1]);

						seen[ e[d].u ] = true;
						seen[ e[d].v ] = true;
						
						if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
				
						}
									
					String surplus = br.readLine();
					if( surplus != null )
						{
						if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
						}
					
					}
			catch (IOException ex)
				{ 
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
				}

			for( int x=1; x<=n; x++ )
				{
				if( seen[x] == false )
					{
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}

			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//! 
			//! there will be n vertices in the graph, numbered 1 to n

			//! INSERT YOUR CODE HERE!
			
			//***********************************************************************************************************

			// if (isCompleteGraph(n, e)) {
			// 	System.out.println("This is a complete graph.");
			// } else {
			// 	System.out.println("This is NOT a complete graph.");
			// }

			if (isBipartite(n, e)) {
				System.out.println("This is a bipartite.");
			} else {
				System.out.println("This is NOT a bipartite.");
			}
			
			
		}

		//A bipartite is a graph whose vertices can be divided into two disjoint and independent sets 
		//U and V such that every edge connects a vertex in U to one in V
		public static boolean isBipartite(int numberOfVertice, ColEdge e[]) {

			//Choose 1 fix vertex to find set U
			int fixVertex1 = e[0].u;
			//Create an array for set U - an array that store other vertices that are not linked to first fix vetex
			int[] setU = new int[numberOfVertice];
			int setULength = 0;

			//Check every vertex to see if it is linked to the first vertex, add it to set U if it is not linked.
			for (int i = 1; i <= numberOfVertice; i++) {
				boolean isLinked = false;
				for (int j = 0; j < e.length; j++) {
					if ((e[j].u == fixVertex1 && e[j].v == i) || (e[j].v == fixVertex1 && e[j].u == i)) {
						isLinked = true;
						break;
					}
				}

				if (!isLinked) {
					setU[setULength] = i;
					setULength++;
				}
			}

			//Choose 1 fix vertex (not in set U) to create set V
			int fixVertex2 = 0;
			boolean hasFound = false;
			int index = 1;
			//Check all vertices to see is there any vertex that is not in set U
			while(!hasFound && index <= numberOfVertice) {
				if (!findNumber(index, setU)) {
					hasFound = true;
					fixVertex2 = index;
				} else {
					index++;
				}
			}
			//If there is no other vetex that is not linked to the first fix vertex, then the grapth is Bipartite
			if (!hasFound) {
				return true;
			}
			//If there is a vertex that is not in set U...
			int[] setV = new int[numberOfVertice];
			int setVLength = 0;

			for (int i = 1; i <= numberOfVertice; i++) {
				boolean isLinked = false;
				for (int j = 0; j < e.length; j++) {
					if ((e[j].u == fixVertex2 && e[j].v == i) || (e[j].v == fixVertex2 && e[j].u == i)) {
						isLinked = true;
						break;
					}
				}

				if (!isLinked) {
					setV[setVLength] = i;
					setVLength++;
				}
			}


			boolean isBipartite = true;
			//If there is a vertex that is not in U and not in V, then the graph is not bypartite
			for (int i = 1; i <= numberOfVertice; i++) {
				if (!findNumber(i, setU) && !findNumber(i, setV)) {
					isBipartite = false;
				}
			}

			return isBipartite;
		}

		public static boolean findNumber(int number, int[] array){
			boolean findNumber = false;
			int index = 0;
			while (!findNumber && index < array.length) {
				if (array[index] == number) {
					findNumber = true;
				} else {
					index++;
				}
			}
			return findNumber;
		}

		//A complete graph is a graph in which every pair of distinct vertices is connected by a unique edge
		public static boolean isCompleteGraph(int numberOfVertice, ColEdge e[]) {
			boolean isCompleteGraph = true;

			//Check every vertex to see if it is linked to all other vertices
			for (int i = 1; i <= numberOfVertice; i++) {
				for (int j = i + 1; j <= numberOfVertice; j++) {
					boolean hasAllLinked = false;
					for (int k = 0; k < e.length; k++) {
						if ((e[k].u == i && e[k].v == j) || (e[k].v == i && e[k].u == j)) {
							hasAllLinked = true;
						}
					}
					if (!hasAllLinked) {
						isCompleteGraph = false;
						break;
					}
				}
			}

			return isCompleteGraph;
		}

}
