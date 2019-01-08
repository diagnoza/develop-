import java.io.*;
import java.util.*;

class ColEdge{
    int u;
    int v;
}

public class Phase3 {


    public final static boolean DEBUG = true;

    public final static String COMMENT = "//";
    
    public final static int POPULATION = 30;

    public static void main( String args[] ) {

        if( args.length < 1 ) {

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

        try {
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
                //if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
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

                //if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);

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
        
        ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();
        	for (int i = 0; i < n + 1; i++){
        		adjList.add(new LinkedList<Integer>());
        	}
        	for (int i = 0; i < m; i++){
        		adjList.get(e[i].u).add(e[i].v);
        		adjList.get(e[i].v).add(e[i].u);
        	}
	        	int upB = Brooks(calculateDegreeArray(n, m, e),n);
	        	System.out.println("Upperbound is: "+upB);
	        	//System.out.println("Chromatic number is: "+BruteForce(n, 2, adjList));
	        	
	        	int[][] population = new int[POPULATION][n];
	        	double [] fitness = new double[POPULATION];
	        	for(int i=0;i<POPULATION;i++){
	        		for(int j=0;j<n;j++){
	        			population[i][j] = (int)(Math.random() * upB);
	        			//System.out.println(population[i][j]);
	        		}
	        		fitness[i] = (double)(redLineCounter(adjList,population,n,i)/(double)m);
	        		System.out.println(fitness[i]);
	        	}
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


    public static int Brooks(int[] nodes, int n){

        //Maximum is set to the minimum number of edges possible, which is 0
        int maximum = 0;
        //Returns the number of edges for the node that has the highest number of them
        for(int i=0;i<n;i++){
            maximum = Math.max(maximum,nodes[i]);
        }

        return maximum;
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




    public static int BruteForce(int n, int chromaticBegins, ArrayList<LinkedList<Integer>> adjList) {
        int[] colors = new int[n];
        int counter = 0; //attempts counter
        int chromatic = 4;
        if(chromaticBegins != 0) chromatic = chromaticBegins;
        int i;
        int chromaticOld = 0;

        for (i = 0; i < n; i++) colors[i] = 0;

        while (true) {
            if (chromaticOld != 0){
                counter++;
                if(isValid(adjList, colors, n)){
                    System.out.println("Algorithm succeds after " + counter + " attempts");
                    return chromatic;
                }
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


    public static boolean isValid(ArrayList<LinkedList<Integer>> adjList, int[] colors, int numberOfVertices) {
        //CHECK IF THE COLORING IS VALID

        for (int i = 0; i < numberOfVertices; i++)
            for (int j = 0; j < adjList.get(i + 1).size(); j++)
                if(colors[i] == colors[adjList.get(i + 1).get(j) - 1]) return false;

        return true;
    }
    
    public static int redLineCounter(ArrayList<LinkedList<Integer>> adjList, int[][] colors, int numberOfVertices, int individual) {
        //CHECK IF THE COLORING IS VALID
        
        int rCounter = 0;

        for (int i = 0; i < numberOfVertices; i++)
            for (int j = 0; j < adjList.get(i + 1).size(); j++)
                if(colors[individual][i] == colors[individual][adjList.get(i + 1).get(j) - 1]) rCounter++;

        return rCounter/2;
    }


    //Initial approach to the brute force problem could benefit from sorting the vertexDegree array,
    //but later on we have changed our minds, as so this method remains unused.
    //It is correct however, and might still prove useful later on.


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

}