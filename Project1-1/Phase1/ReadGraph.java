import java.io.*;
import java.util.*;

class ColEdge implements Comparable<ColEdge>{
    int u; //some vertex 1
    int colorU = -1;
    int v; //some vertex 2
    int colorV = -1;

    public int compareTo(ColEdge other) {
        return Integer.compare(this.u, other.u);
    }
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

			
			if (hasNoVertex(n)) {
				System.out.println("Chromatic number = 0.");
				return;
			} else if (hasNoEdge(m)){
				System.out.println("Chromatic number = 1.");
				return;
			} else if (isBipartite(n, e)){
				System.out.println("Chromatic number = 2.");
				return;
			} else if (isOddCycle(n, m, e)){
				System.out.println("Chromatic number = 3.");
				return;
			} else if (isCompleteGraph(n, e)) {
				//The chromatic number of a complete graph = the number of vertices
				System.out.println("Chromatic number = " + n + ".");
				return;
			} else if (Brooks(calculateDegreeArray(n,m,e),n) == 3){
				System.out.println("Chromatic number = 3.");
				return;
				//At this point, the lower-bound of the graph is 3.
				//Apply Brooks' theorem to check the upper-bound
				//If lower-bound = upper-bound --> chromatic number 
			} else {
				System.out.println("The upperbound is: " + Brooks(calculateDegreeArray(n ,m ,e),n));
				System.out.println("The lowerbound is: 3");

			}

			int[] nodes = new int[n];
        	int[] vertexDegree = new int[n];

        	vertexDegree = calculateDegreeArray(n, m, e);
        	for (int i = 0; i < n; i++) 
				nodes[i] = i;

		ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();

        for (int i = 0; i < n + 1; i++) adjList.add(new LinkedList<Integer>());

        for (int i = 0; i < m; i++){
            adjList.get(e[i].u).add(e[i].v);
            adjList.get(e[i].v).add(e[i].u);
            //if(e[i].v == i) adjList.get(i).add(e[i].u);
        }
		//for (int i = 1; i < n + 1; i++) System.out.println("Node " + i + " connects to: " + adjList.get(i));

        //System.out.println(adjList.get(1).size());
        System.out.println("Chromatic number = " + BruteForce(n, adjList)); 
        //quicksort(0, n - 1, nodes, vertexDegree);
    }
		
		public static int[] calculateDegreeArray(int n, int m, ColEdge e[]){
        int[] nodes = new int[n];
        //Loop that counts the number of times a node appears in the set of edges
        for(int i=0;i<m;i++){
            nodes[e[i].u-1]++;
            nodes[e[i].v-1]++;
        }
        /*System.out.println("The maximum is: "+maximum);*/
        return nodes;
    	}
		//BROOKS THEOREM METHOD
		/*
		We use the Brooks theorem to determine the upperbound by the maximum number of edges a vertex has.
		*/
		public static int Brooks(int[] nodes, int n){
			//Maximum is set to the minimum number of edges possible, which is 0
			int maximum = 0;
			//Returns the number of edges for the node that has the highest number of them
			for(int i=0;i<n;i++)
				maximum = Math.max(maximum,nodes[i]);

			return maximum;
		}	

		//QUICKSORT ALGORITHM
		public static void quicksort(int left, int right, int[] nodes, int[] vertexDegree){

            int i,j,pivot;

            i = (left + right) / 2;
            pivot = vertexDegree[i]; vertexDegree[i] = vertexDegree[right];
            for(j = i = left; i < right; i++)
                if(vertexDegree[i] < pivot) {

                    int temp = vertexDegree[i];
                    vertexDegree[i] = vertexDegree[j];
                    vertexDegree[j] = temp;

                    temp = nodes[i];
                    nodes[i] = nodes[j];
                    nodes[j] = temp;

                    j++;
                }
            vertexDegree[right] = vertexDegree[j]; vertexDegree[j] = pivot;
            if(left < j - 1)  quicksort(left, j - 1, nodes, vertexDegree);
            if(j + 1 < right) quicksort(j + 1, right, nodes, vertexDegree);

    	}	
		public static boolean isValid (ArrayList<LinkedList<Integer>> adjList, int[] colors, int numberOfVertices) {
        //CHECK IF THE COLORING IS VALID

       		for (int i = 0; i < numberOfVertices; i++)
            	for (int j = 0; j < adjList.get(i + 1).size(); j++)
                	if(colors[i] == colors[adjList.get(i + 1).get(j) - 1]) return false;

    		return true;
    	}

		//BRUTE FORCE METHOD
		public static int BruteForce(int n, ArrayList<LinkedList<Integer>> adjList) {
        	int[] colors = new int[n];
        	int counter = 0; //attempts counter
        	int chromatic = 2;
        	int i;
        	int chromaticOld = 0;

        	for (i = 0; i < n; i++) colors[i] = 0;

        	while (true) {
            	if (chromaticOld != 0){
                	//System.out.println ("Attempt " + counter++ + "...");
                	if(isValid(adjList, colors, n)) return chromatic;
            }

            while (true) {
                for (i = 0; i < n; i++) {
                    colors[i]++;
                    if (colors[i] == chromatic - 1) chromaticOld++;
                    if (colors[i] < chromatic) break;
                    colors[i] = 0;
                    chromaticOld--;
                }

                if (i < n) break;
                chromatic++;

            }
        }
    }
		//EXCEPTIONS - METHODS
		/*
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

		/*
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
	
	 
		/*
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
