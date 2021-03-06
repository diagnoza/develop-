import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Decomposer {
    /**
     * This method decompose a graph into disconnected graphs
     *
     * @param adjList
     * @param numberOfVertices
     * @param e
     * @return list of disconnected graphs
     */
    public static List<Graph> decompose(ArrayList<LinkedList<Integer>> adjList, int numberOfVertices, ColEdge[] e) {
        List<Graph> graphs = new ArrayList<>(); //List of all disconnected graphs from the original graph

        List<Integer> originalVertices = new ArrayList<>(); //List of all vertices from the original graph
        for (int i = 1; i <= numberOfVertices; i++) {
            originalVertices.add(i);
        }

        while (originalVertices.size() != 0) { //This loop stops when all vertices are classified

            //List of all vertices from the small graph
            List<Integer> smallGraphVertices = new ArrayList<>();

            //List of all vertices whose neighbors are not yet added to the small graph
            List<Integer> uncheckedVertices = new ArrayList<>();

            //Add the current original vertex to the small graph and the unchecked list,
            //then remove it from the original graph
            int currentOriginalVertex = originalVertices.get(0);
            originalVertices.remove(0);
            smallGraphVertices.add(currentOriginalVertex);
            uncheckedVertices.add(currentOriginalVertex);

            //Add all neighbors of the vertices in unchecked list, and their neighbors' neighbors... to the small graph
            while (uncheckedVertices.size() != 0) {
                int checkingVertex = uncheckedVertices.get(0);
                uncheckedVertices.remove(0);

                for (Integer neighbor : adjList.get(checkingVertex)) {
                    if (!smallGraphVertices.contains(neighbor)) {
                        smallGraphVertices.add(neighbor);
                        uncheckedVertices.add(neighbor);
                    }
                    originalVertices.remove(neighbor);

                }

            }

            //Construct the small graph
            List<ColEdge> smallGraphEdges = new ArrayList<>();
            for (int i = 0; i < e.length; i++){
                if (smallGraphVertices.contains(e[i].u) && smallGraphVertices.contains(e[i].v)){
                    smallGraphEdges.add(e[i]);
                } else if ((smallGraphVertices.contains(e[i].u) && !smallGraphVertices.contains(e[i].v))
                        || (!smallGraphVertices.contains(e[i].u) && smallGraphVertices.contains(e[i].v))){
                    throw new RuntimeException("Something is wrong with decomposing the graph");
                }
            }
            Graph smallGraph = new Graph(smallGraphVertices, smallGraphEdges.toArray(new ColEdge[0]));
            //The Graph constructor will change the format of the graph to the standard format,
            //where the index of vertices are successive (vertex 1, vertex 2 and so on..)

            graphs.add(smallGraph);
        }
//        System.out.println("Number of disconnected graphs: " + graphs.size());
//        for (Graph graph: graphs){
//            System.out.print(graph.getNumberOfVertices() + " ");
//        }
        return graphs;
    }
}
