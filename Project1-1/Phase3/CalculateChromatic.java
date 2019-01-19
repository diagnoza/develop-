import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CalculateChromatic {
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
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < numberOfVertices - 1; i++) {
                if (vertexDegrees[i] < vertexDegrees[i + 1]) {
                    swapped = true;

                    int tmp = vertexDegrees[i];
                    vertexDegrees[i] = vertexDegrees[i + 1];
                    vertexDegrees[i + 1] = tmp;

                    int tmp2 = vertices[i];
                    vertices[i] = vertices[i + 1];
                    vertices[i + 1] = tmp2;
                }
            }
        } while (swapped);

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

    public static int getLowerboundGreedy(ArrayList<LinkedList<Integer>> adjList, int numberOfVertices){
        final int THRESHOLD = 1100;
        List<Integer> startingVertices = new ArrayList<>();

        if (numberOfVertices > THRESHOLD){
            //Ramdomly choose THRESHOLD-number of vertices to find their cliques if the graph is too large
            while (startingVertices.size() < THRESHOLD){
                int rand = (int) (Math.random() * numberOfVertices) + 1;
                if (!startingVertices.contains(rand)){
                    startingVertices.add(rand);
                }
            }
        } else {
            for (int i = 1; i <= numberOfVertices; i++){
                startingVertices.add(i);
            }
        }

        int maxClique = -1;
        for (Integer startingVertex: startingVertices){
            //For each vertex in the graph, find a clique that contains the vertex

            List<Integer> clique = new ArrayList<>();
            clique.add(startingVertex);

            for (int i = 1; i <= numberOfVertices; i++){
                if (i != startingVertex){
                    boolean isInClique = true;

                    //Check if vertex i is connected to every vertex in the current clique
                    for (Integer cliqueElement: clique){
                        if (!adjList.get(i).contains(cliqueElement)){
                            isInClique = false;
                        }
                    }
                    if (isInClique) clique.add(i);
                }
            }
            if (clique.size() > maxClique){
                maxClique = clique.size();
            }
        }
        return maxClique; //Return the size of the biggest clique found
    }
}
