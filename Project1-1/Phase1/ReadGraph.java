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
			
			//EXCEPTIONS GO HERE
			
			if (hasNoVertex(n)) {
				System.out.println("Chromatic number = 0.");
			} else if (hasNoEdge(m)){
				System.out.println("Chromatic number = 1.");
			} else if (isBipartite(n, e)){
				System.out.println("Chromatic number = 2.");
			} else if (isOddCycle(n, m, e)){
				System.out.println("Chromatic number = 3.");
			} else if (isCompleteGraph(n, e)) {
				//The chromatic number of a complete graph = the number of vertices
				System.out.println("Chromatic number = " + n + ".");
			} else {
				//At this point, the lower-bound of the graph is 3.
				//Apply Brooks' theorem to check the upper-bound
				//If lower-bound = upper-bound --> chromatic number 
			}


			//MAIN CODE
			int[] nodes = new int[n];
			//Loop that counts the number of times a node appears in the set of edges
			for(int i=0;i<m;i++){
				nodes[e[i].u-1]++;
				nodes[e[i].v-1]++;
			}
			//Maximum is set to the minimum number of edges possible, which is 0
			int maximum = 0;
			//Returns the number of edges for the node that has the highest number of them
			for(int i=0;i<n;i++){
				maximum = Math.max(maximum,nodes[i]);
			}
			System.out.println("The maximum is: "+maximum);
			
			//BRUTE-FORCE GOES HERE
			
			
			
				
			
		}

		//EXCEPTIONS - METHODS
		/**
		A bipartite is a graph whose vertices can be divided into two disjoint and independent sets 
		U and V such that every edge connects a vertex in U to one in V
		This method checks if a graph is bipartite
		*/
		public static boolean isBipartite(int numberOfVertice, ColEdge e[]) {

			//Choose 1 fixed vertex to find set U
			int fixVertex1 = e[0].u;
			//Create an array for set U - an array that stores other vertices that are not linked to the first fixed vetex
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

			//Choose 1 fixed vertex (not in set U) to create set V
			int fixVertex2 = 0;
			boolean hasFound = false;
			int index = 1;
			//Check all vertices to see if there is any vertex that is not in set U
			while(!hasFound && index <= numberOfVertice) {
				if (!findNumber(index, setU)) {
					hasFound = true;
					fixVertex2 = index;
				} else {
					index++;
				}
			}
			//If there is no other vetex that is not linked to the first fixed vertex, then the graph is Bipartite
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
			//If there is a vertex that is not in U and not in V, then the graph is not bipartite
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

		/**
		A complete graph is a graph in which every pair of distinct vertices is connected by a unique edge
		This method checks if a graph is a complete graph
		*/
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

		public static boolean hasNoVertex(int n)//n = vertices
		{	//if there is no vertices then chromatic number is 0 and graph does not exits.
			
			
			if(n == 0)
			{         
				
				//System.out.println("this graph doesnt exist");
				return true;
			}
			//System.out.println("this graph  exist");
	
				return false;
		}
	
	
		public static boolean hasNoEdge(int m)
		{
			//m == number of edges
			//if no edges then chromatic number is 1
			if(m==0)
			{
				return true;
			}
		
			return false;
		}
	
	 
		/**
		 An odd cycle is a graph with the number of vertices is odd
		 and the number of edges = number of vertices
		 and every vertex has 2 edge.
		 The chromatic number of oddCycle graph is 3.
		*/
	
		public static boolean isOddCycle(int n, int m, ColEdge e[]) {
			//n == no.of vertices ,, m == number of edges
			boolean cyclic = true;
			
			//if number of vertices is even return false;
			if(n%2==0) return false;
			
			if(m!=n)return false;
			
			 for(int i = 1;i<=n;i++)
			 {	
			 	 int count =0;
			 	 for(int j = 0;j<e.length;j++)
			 	 {	
			 	 	 
			 	 	 if(e[j].u==i||e[j].v==i)
			 	 	 {
			 	 	 	 count++;
			 	 	 }
			 	 }
			 	
			 	 if(count!=2)
			 	 {
			 	 	 cyclic = false;
			 	 }
			 }
			 return cyclic;
		}


}
