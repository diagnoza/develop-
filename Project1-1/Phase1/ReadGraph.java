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
			} else if (Brooks(n, m, e) == 3){
				System.out.println("Chromatic number = 3.");
				//At this point, the lower-bound of the graph is 3.
				//Apply Brooks' theorem to check the upper-bound
				//If lower-bound = upper-bound --> chromatic number 
			}
			System.out.println(Brooks(n ,m ,e));


		//BROOKS THEOREM METHOD
		/**
		We use the Brooks theorem to determine the maximum number of edges a vertex has.
		*/
		public static int Brooks(int n, int m, ColEdge e[]){
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
			/*System.out.println("The maximum is: "+maximum);*/
			return maximum;
		}		
			
		}

		//EXCEPTIONS - METHODS
		/**
		A bipartite is a graph whose vertices can be divided into two disjoint and independent sets 
		U and V such that every edge connects a vertex in U to one in V
		This method checks if a graph is bipartite
		*/
		public static boolean isBipartite(int numberOfVertice, ColEdge e[]) {
			//Create an array that stores the colors of each vertex.
			//There are two colors 1 and -1
			//Value 0 of this array means the vextex has not been assigned any color
			int[] color = new int[numberOfVertice];

			//Assign the color 1 to the first vertex
			color[0] = 1;

			//Create an array list that stores the vetices that need to be checked
			ArrayList<Integer> checkingVertices = new ArrayList<Integer>();

			//Add the first vertex to the checking list
			checkingVertices.add(1);

			while (checkingVertices.size() != 0){
				//Check the first vertex of the list and remove it from the list
				int checkingVertex = checkingVertices.get(0);
				checkingVertices.remove(0);

				//Find other vertices that are linked to the checking vertex
				for (int i = 1; i <= numberOfVertice; i++) {
					for (int j = 0; j < e.length; j++) {
						if ((e[j].u == checkingVertex && e[j].v == i) || (e[j].v == checkingVertex && e[j].u == i)) {
							if (color[i-1] == 0) {
								//If the vertex has not been assigned a color, then assign a color to it
								//The color assigned must be different from the checking vertex's color
								color[i-1] = -color[checkingVertex-1];
								checkingVertices.add(i);
							} else if(color[i-1] == color[checkingVertex-1]){
								//If the vertex has the same color as the checking vertex, then it is not valid
								//So the graph cannot be assigned with 2 colors
								//So the graph is not a bipartite
								return false;
							}
						}
					}
				}
			}
			//All the vertices have been assigned with the color 1 or -1
			//So the graph is Bipartite
			return true;
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
					boolean hasLinked = false;
					for (int k = 0; k < e.length; k++) {
						if ((e[k].u == i && e[k].v == j) || (e[k].v == i && e[k].u == j)) {
							hasLinked = true;
						}
					}
					if (!hasLinked) {
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
