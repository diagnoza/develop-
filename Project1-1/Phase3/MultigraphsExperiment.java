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
        final int ATTEMPS_FOR_LOWERBOUND = 10;

        //ArrayList of linked lists, for each and every node contains its adjacent nodes
        ArrayList<LinkedList<Integer>> adjList = new ArrayList<LinkedList<Integer>>();

        for (int i = 0; i < n + 1; i++) adjList.add(new LinkedList<Integer>());

        for (int i = 0; i < m; i++) {
            adjList.get(e[i].u).add(e[i].v);
            adjList.get(e[i].v).add(e[i].u);
        }


        int upperbound = CalculateChromatic.getUpperboundGreedy(adjList, n, e);
        System.out.println("NEW BEST UPPER BOUND = " + upperbound);

        List<Graph> disconnectedSubGraphs = Decomposer.decompose(adjList, n, e);

        boolean hasAllChromatic = true;

        for (Graph disconnectedSubGraph : disconnectedSubGraphs) { //Try to calculate the chromatic number for every subgraph
            int subUpperbound = CalculateChromatic.getUpperboundGreedy(disconnectedSubGraph.getAdjList(), disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getEdges());
            disconnectedSubGraph.setUpperbound(subUpperbound);

            //Check special cases
            if (CalculateChromatic.hasNoVertex(disconnectedSubGraph.getNumberOfVertices())) {
                disconnectedSubGraph.setChromaticNumber(0);
                continue;
            }
            if (CalculateChromatic.hasNoEdge(disconnectedSubGraph.getNumberOfEdges())) {
                disconnectedSubGraph.setChromaticNumber(1);
                continue;
            }
            if (CalculateChromatic.isBipartite(disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getAdjList())) {
                disconnectedSubGraph.setChromaticNumber(2);
                continue;
            }

            //At this point, the lowerbound of the subgraph is 3, since we have covered all cases for
            //chromatic number = 0, 1, 2
            int subLowerbound = 3;
            disconnectedSubGraph.setLowerbound(subLowerbound);

            if (n % 2 == 1 && CalculateChromatic.isCycle(disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getNumberOfEdges(), disconnectedSubGraph.getAdjList())) {
                disconnectedSubGraph.setChromaticNumber(3);
                continue;
            }
            if (CalculateChromatic.isCompleteGraph(disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getAdjList())) {
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
            if (CalculateChromatic.isWheelGraph(disconnectedSubGraph.getNumberOfVertices(), disconnectedSubGraph.getEdges(), disconnectedSubGraph.getAdjList())) {
                if (disconnectedSubGraph.getNumberOfVertices() % 2 == 1) {
                    disconnectedSubGraph.setChromaticNumber(3);
                } else {
                    disconnectedSubGraph.setChromaticNumber(4);
                }
                continue;
            }

            //Use Brute-force if the number of vertices is smaller than the threshold
            if (disconnectedSubGraph.getNumberOfVertices() < THRESHOLD_FOR_BRUTEFORCE) {
                disconnectedSubGraph.setChromaticNumber(CalculateChromatic.BruteForce(disconnectedSubGraph.getNumberOfVertices(), subLowerbound, disconnectedSubGraph.getAdjList()));
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

        System.out.println("NEW BEST LOWER BOUND = " + CalculateChromatic.getLowerboundGreedy(adjList, n, ATTEMPS_FOR_LOWERBOUND));
        //TODO: Lowerbound method
        //TODO: Do genetic algorithm

    }
}