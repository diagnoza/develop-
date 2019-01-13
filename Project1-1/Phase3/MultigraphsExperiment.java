import java.io.*;
import java.util.*;


public class MultigraphsExperiment {
    public static void main(String[] args) {

        for (int i = 1; i <= 20; i++){
            System.out.println("Graph no. " + i);
            //Check the test set from phase 3
            if (i < 10){
                calculateChromatic("allBlock3Graphs2018/block3_2018_graph0" + i + ".txt");
            } else {
                calculateChromatic("allBlock3Graphs2018/block3_2018_graph" + i + ".txt");
            }

            //Check the test set from phase 1
//            if (i < 10){
//                calculateChromatic("graphsPhase1/graph0" + i + ".txt");
//            } else {
//                calculateChromatic("graphsPhase1/graph" + i + ".txt");
//            }
        }
    }




    public final static boolean DEBUG = false;

    public final static String COMMENT = "//";

    public static void calculateChromatic(String inputfile) {

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
            while ((record = br.readLine()) != null) {
                if (record.startsWith("//")) continue;
                break; // Saw a line that did not start with a comment -- time to start reading the data in!
            }

            if (record.startsWith("VERTICES = ")) {
                n = Integer.parseInt(record.substring(11));
                if (DEBUG) System.out.println(COMMENT + " Number of vertices = " + n);
            }

            seen = new boolean[n + 1];

            record = br.readLine();

            if (record.startsWith("EDGES = ")) {
                m = Integer.parseInt(record.substring(8));
                if (DEBUG) System.out.println(COMMENT + " Expected number of edges = " + m);
            }

            e = new ColEdge[m];

            for (int d = 0; d < m; d++) {
                if (DEBUG) System.out.println(COMMENT + " Reading edge " + (d + 1));
                record = br.readLine();
                String data[] = record.split(" ");
                if (data.length != 2) {
                    System.out.println("Error! Malformed edge line: " + record);
                    System.exit(0);
                }
                e[d] = new ColEdge();

                e[d].u = Integer.parseInt(data[0]);
                e[d].v = Integer.parseInt(data[1]);

                seen[e[d].u] = true;
                seen[e[d].v] = true;

                if (DEBUG) System.out.println(COMMENT + " Edge: " + e[d].u + " " + e[d].v);

            }

            String surplus = br.readLine();
            if (surplus != null) {
                if (surplus.length() >= 2) if (DEBUG)
                    System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '" + surplus + "'");
            }

        } catch (IOException ex) {
            // catch possible io errors from readLine()
            System.out.println("Error! Problem reading file " + inputfile);
            System.exit(0);
        }

        for (int x = 1; x <= n; x++) {
            if (seen[x] == false) {
                if (DEBUG)
                    System.out.println(COMMENT + " Warning: vertex " + x + " didn't appear in any edge : it will be considered a disconnected vertex on its own.");
            }
        }


        //**********************************************************************************************************************************************************

        final int THRESHOLD_FOR_BRUTEFORCE = 20;

        //ArrayList of linked lists, for each and every node contains its adjacent nodes
        ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();

        for (int i = 0; i < n + 1; i++) adjList.add(new LinkedList<Integer>());

        for (int i = 0; i < m; i++) {
            adjList.get(e[i].u).add(e[i].v);
            adjList.get(e[i].v).add(e[i].u);
        }


        int upperbound = getUpperboundGreedy(adjList, n, e);
        System.out.println("NEW BEST UPPER BOUND = " + upperbound);

        List<Graph> disconnectedSubGraphs = Decomposer.decompose(adjList, n, e);

        boolean hasAllChromatic = true;

        for (Graph disconnectedSubGraph : disconnectedSubGraphs) { //Try to calculate the chromatic number for every subgraph
            int subUpperbound = getUpperboundGreedy(disconnectedSubGraph.getAdjList(), disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getEdges());
            disconnectedSubGraph.setUpperbound(subUpperbound);

            //Check special cases
            if (hasNoVertex(disconnectedSubGraph.getNumberOfVertices())) {
                disconnectedSubGraph.setChromaticNumber(0);
                continue;
            }
            if (hasNoEdge(disconnectedSubGraph.getNumberOfEdges())) {
                disconnectedSubGraph.setChromaticNumber(1);
                continue;
            }
            if (isBipartite(disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getAdjList())) {
                disconnectedSubGraph.setChromaticNumber(2);
                continue;
            }

            //At this point, the lowerbound of the subgraph is 3, since we have covered all cases for
            //chromatic number = 0, 1, 2
            int subLowerbound = 3;
            disconnectedSubGraph.setLowerbound(subLowerbound);

            if (n % 2 == 1 && isCycle(disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getNumberOfEdges(), disconnectedSubGraph.getAdjList())) {
                disconnectedSubGraph.setChromaticNumber(3);
                continue;
            }
            if (isCompleteGraph(disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getAdjList())) {
                //The chromatic number of a complete graph = the number of vertices
                disconnectedSubGraph.setChromaticNumber(disconnectedSubGraph.getNumberOfVertices());
                continue;
            }
            if (subUpperbound == subLowerbound) {
                //At this point, the lower-bound of the graph is 3.
                //If lower-bound = upper-bound --> chromatic number
                disconnectedSubGraph.setChromaticNumber(3);
                continue;
            }
            if (isWheelGraph(disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getEdges(), disconnectedSubGraph.getAdjList())) {
                if (disconnectedSubGraph.getNumberOfVertices() % 2 == 1) {
                    disconnectedSubGraph.setChromaticNumber(3);
                } else {
                    disconnectedSubGraph.setChromaticNumber(4);
                }
                continue;
            }

            //Use Brute-force if the number of vertices is smaller than the threshold
            if (disconnectedSubGraph.getNumberOfVertices() < THRESHOLD_FOR_BRUTEFORCE) {
                disconnectedSubGraph.setChromaticNumber(BruteForce(disconnectedSubGraph.getNumberOfVertices(), subLowerbound, disconnectedSubGraph.getAdjList()));
                continue;
            }


            //At this point, the chromatic for this subgraph cannot be calculated
            hasAllChromatic = false;
        }

        //Store the biggest sub-chromatic number, the biggest sub-upperbound and the biggest sub-lowerbound
        int maxSubChromatic = -1;
        int maxSubUpperbound = -1;
        int maxLowerbound = -1;
        for (Graph disconnectedSubGraph : disconnectedSubGraphs) {
            if (disconnectedSubGraph.getChromaticNumber() > maxSubChromatic) {
                maxSubChromatic = disconnectedSubGraph.getChromaticNumber();
            }

            if (disconnectedSubGraph.getUpperbound() > maxSubUpperbound) {
                maxSubUpperbound = disconnectedSubGraph.getUpperbound();
            }

            if (disconnectedSubGraph.getLowerbound() > maxLowerbound) {
                maxLowerbound = disconnectedSubGraph.getLowerbound();
            }
        }

        if (maxSubUpperbound < upperbound) {
            //Greedy algorithm always gives the upperbound for every graph,
            // so we can conclude the original graph's upperbound is maxSubUpperbound

            //if the new upperbound is better, the update it
            upperbound = maxSubUpperbound;
            System.out.println("NEW BEST UPPER BOUND = " + upperbound);

        }

        if (hasAllChromatic) {
            //If we know the chromatic numbers of all subgraphs, then the chromatic number of the original graph
            //is the bigger sub-chromatic number
            System.out.println("CHROMATIC NUMBER = " + maxSubChromatic);

            //Stop the program
            return;
        } else {
            //if not, the lowerbound of the original graph is the bigger number between
            // maxLowerbound and maxSubChromatic
            if (maxLowerbound > maxSubChromatic) {
                System.out.println("NEW BEST LOWER BOUND = " + maxLowerbound);
            } else {
                System.out.println("NEW BEST LOWER BOUND = " + maxSubChromatic);
            }
        }


        //TODO: Lowerbound method
        //TODO: Do genetic algorithm

    }


    public static int calculateLowerBound(int upper, ArrayList<LinkedList<Integer>> adjList) {

        int lower = upper;
        int counter = 1;

        while (true) {

            for (int i = 1; i < adjList.size(); i++) {
                for (int j = 1; j < adjList.size(); j++) {

                    List<Integer> common = new ArrayList<Integer>(adjList.get(i));
                    common.retainAll(adjList.get(j));

                    //System.out.println(common.size());
                    if (common.size() == lower) counter++;

                }

                if (counter == lower) return lower;
                counter = 1;
            }


            lower--;
            if (lower == 2) return 0;
        }
    }


    public static int[] calculateDegreeArray(int n, int m, ColEdge e[]) {
        int[] nodes = new int[n];
        //Loop that counts the number of times a node appears in the set of edges
        for (int i = 0; i < m; i++) {
            nodes[e[i].u - 1]++;
            nodes[e[i].v - 1]++;
        }
        /*System.out.println("The maximum is: "+maximum);*/
        return nodes;
    }


    public static int Brooks(int[] nodes, int n) {

        //Maximum is set to the minimum number of edges possible, which is 0
        int maximum = 0;
        //Returns the number of edges for the node that has the highest number of them
        for (int i = 0; i < n; i++) {
            maximum = Math.max(maximum, nodes[i]);
        }

        return maximum;
    }


    public static boolean hasNoVertex(int n)//n = vertices
    {    //if there is no vertices then chromatic number is 0 and graph does not exits.


        if (n == 0) {

            //System.out.println("this graph doesnt exist");
            return true;
        }
        //System.out.println("this graph  exist");

        return false;
    }


    public static boolean hasNoEdge(int m) {
        //m == number of edges
        //if no edges then chromatic number is 1
        if (m == 0) {
            return true;
        }

        return false;
    }


    public static boolean isCycle(int n, int m, ArrayList<LinkedList<Integer>> adjList) {
        //n == no.of vertices ,, m == number of edges
        boolean cyclic = true;

        //The number of vertices and edges in a cycle must be equal
        if (m != n) return false;

        //Each vertex must have 2 edges
        for (int i = 1; i <= n; i++) {
            if (adjList.get(i).size() != 2) {
                cyclic = false;
            }
        }
        return cyclic;
    }


    public static boolean isCompleteGraph(int numberOfVertice, ArrayList<LinkedList<Integer>> adjList) {
        boolean isCompleteGraph = true;

        //Check every vertex to see if it is linked to all other vertices
        for (int i = 1; i <= numberOfVertice; i++) {
            if (adjList.get(i).size() != numberOfVertice - 1){
                isCompleteGraph = false;
                break;
            }
        }

        return isCompleteGraph;
    }


    //A bipartite is a graph whose vertices can be divided into two disjoint and independent sets
    //U and V such that every edge connects a vertex in U to one in V
    public static boolean isBipartite(int numberOfVertice, ArrayList<LinkedList<Integer>> adjList) {
        //Create an array that stores the colors of each vertex.
        //There are two colors 1 and -1
        //Value 0 of this array means the vextex has not been assigned any color
        int[] color = new int[numberOfVertice + 1];

        //Assign the color 1 to the first vertex
        color[1] = 1;

        //Create an array list that stores the vetices that need to be checked
        ArrayList<Integer> checkingVertices = new ArrayList<>();

        //Add the first vertex to the checking list
        checkingVertices.add(1);

        boolean hasDone = false;

        while (!hasDone) {
            while (checkingVertices.size() != 0) {
                //Check the first vertex of the list and remove it from the list
                int checkingVertex = checkingVertices.get(0);
                checkingVertices.remove(0);

                //Find other vertices that are linked to the checking vertex
                for (Integer neighbor: adjList.get(checkingVertex)) {
                    if (color[neighbor] == 0) {
                        //If the vertex has not been assigned a color, then assign a color to it
                        //The color assigned must be different from the checking vertex's color
                        color[neighbor] = -color[checkingVertex];
                        checkingVertices.add(neighbor);
                    } else if (color[neighbor] == color[checkingVertex]) {
                        //If the vertex has the same color as the checking vertex, then it is not valid
                        //So the graph cannot be assigned with 2 colors
                        //So the graph is not a bipartite
                        return false;
                    }


                }
            }

            boolean foundUncolored = false;
            int i = 1;

            while (!foundUncolored && i < color.length) {
                if (color[i] == 0) {
                    color[i] = 1;
                    checkingVertices.add(i);
                    foundUncolored = true;
                } else {
                    i++;
                }
            }
            if (!foundUncolored) {
                hasDone = true;
            }
        }

        //All the vertices have been assigned with the color 1 or -1
        //So the graph is Bipartite
        return true;
    }


    public static int BruteForce(int n, int chromaticBegins, ArrayList<LinkedList<Integer>> adjList) {
        int[] colors = new int[n];
        int counter = 0; //attempts counter
        int chromatic = 4;
        if (chromaticBegins != 0) chromatic = chromaticBegins;
        int i;
        int chromaticOld = 0;

        for (i = 0; i < n; i++) colors[i] = 0;

        while (true) {
            if (chromaticOld != 0) {
                counter++;
                if (isValid(adjList, colors, n)) {
//                    System.out.println("Algorithm succeds after " + counter + " attempts");
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
                if (colors[i] == colors[adjList.get(i + 1).get(j) - 1]) return false;

        return true;
    }


    //Initial approach to the brute force problem could benefit from sorting the vertexDegree array,
    //but later on we have changed our minds, as so this method remains unused.
    //It is correct however, and might still prove useful later on.


    public static void quicksort(int left, int right, int[] nodes, int[] vertexDegree) {

        int i, j, pivot;

        i = (left + right) / 2;
        pivot = vertexDegree[i];
        vertexDegree[i] = vertexDegree[right];
        for (j = i = left; i < right; i++)
            if (vertexDegree[i] < pivot) {

                int temp = vertexDegree[i];
                vertexDegree[i] = vertexDegree[j];
                vertexDegree[j] = temp;

                temp = nodes[i];
                nodes[i] = nodes[j];
                nodes[j] = temp;

                j++;
            }
        vertexDegree[right] = vertexDegree[j];
        vertexDegree[j] = pivot;
        if (left < j - 1) quicksort(left, j - 1, nodes, vertexDegree);
        if (j + 1 < right) quicksort(j + 1, right, nodes, vertexDegree);

    }

    public static boolean isWheelGraph(int numberOfVertices, ColEdge[] e, ArrayList<LinkedList<Integer>> adjList) {
        //Find the center of the wheel
        int center = 0;
        for (int i = 1; i <= numberOfVertices; i++) {
            boolean isCenter = true;
            //The center must connect to every other vertices
            for (int j = 1; j <= numberOfVertices; j++) {
                if (i != j && !adjList.get(i).contains(j)) {
                    isCenter = false;
                }
            }
            if (isCenter) {
                center = i;
                break;
            }
        }

        if (center == 0) {
            return false;
        }

        //Remove the center from the graph
        ArrayList<Integer> surroundingVertices = new ArrayList<>();
        for (int i = 1; i <= numberOfVertices; i++) {
            if (i != center) {
                surroundingVertices.add(i);
            }
        }
        ArrayList<ColEdge> surroundingEdges = new ArrayList<>();
        for (int i = 0; i < e.length; i++) {
            if (e[i].u != center && e[i].v != center) {
                ColEdge newEdge = new ColEdge();
                newEdge.u = e[i].u;
                newEdge.v = e[i].v;
                surroundingEdges.add(newEdge);
            }
        }
        Graph surrouding = new Graph(surroundingVertices, surroundingEdges.toArray(new ColEdge[0]));

        //Check if the surrounding is a cycle
        if (isCycle(surrouding.getNumberOfVertices(), surrouding.getNumberOfEdges(), surrouding.getAdjList())) {
            return true;
        }

        return false;
    }

    /**
     * This method use greedy algorithm to find the upperbound
     *
     * @return upperbound
     */
    public static int getUpperboundGreedy(ArrayList<LinkedList<Integer>> adjList, int numberOfVertices, ColEdge e[]) {
        //Create an array that stores the colors of each vertex. The index in the array is the vertex,
        // the value is the color.
        //Value 0 of this array means the vertex has not been assigned any color
        int[] colors = new int[numberOfVertices + 1];

        ArrayList<Integer> usedColors = new ArrayList<>();
        int[] vertices = new int[numberOfVertices];
        for (int i = 0; i < numberOfVertices; i++) {
            vertices[i] = i + 1;
        }

        int[] vertexDegrees = calculateDegreeArray(numberOfVertices, e.length, e);

        //Arrange vertices in non-increasing order of degree
        for (int i = 0; i < numberOfVertices - 1; i++) {
            if (vertexDegrees[i] < vertexDegrees[i + 1]) {
                int tmp = vertexDegrees[i];
                vertexDegrees[i] = vertexDegrees[i + 1];
                vertexDegrees[i + 1] = tmp;

                int tmp2 = vertices[i];
                vertices[i] = vertices[i + 1];
                vertices[i + 1] = tmp2;
            }
        }

        for (int i = 0; i < numberOfVertices; i++) {
            //Go through every vertex in the vertices array
            boolean success = false;
            for (Integer color : usedColors) {
                //Try to color the vertex with available color
                boolean isValid = true;
                for (Integer neighbor : adjList.get(vertices[i])) {
                    if (colors[neighbor] == color) {
                        isValid = false;
                    }
                }
                if (isValid) {
                    colors[vertices[i]] = color;
                    success = true;
                    break;
                }
            }
            if (!success) {
                //Use a new color for the vertex
                if (usedColors.size() == 0) {
                    colors[vertices[i]] = 1;
                } else {
                    colors[vertices[i]] = usedColors.get(usedColors.size() - 1) + 1;
                }
                usedColors.add(colors[vertices[i]]);
            }
        }

        return usedColors.size();
    }
}
