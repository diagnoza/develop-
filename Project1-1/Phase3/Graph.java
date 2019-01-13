import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private List<Integer> standardVertices;
    private ColEdge[] standardEdges;
    private ArrayList<LinkedList<Integer>> adjList;
    private int chromaticNumber;
    private int upperbound;
    private int lowerbound;

    //The constructor will change the format of the input graph to the standard format,
    //where the index of vertices are successive (vertex 1, vertex 2 and so on..)
    public Graph(List<Integer> inputVertices, ColEdge[] inputEdges) {
        standardVertices = new ArrayList<>();
        for (int i = 1; i <= inputVertices.size(); i++){
            standardVertices.add(i);
        }

        standardEdges = new ColEdge[inputEdges.length];
        for (int i = 0; i < inputEdges.length; i++){
            ColEdge newStandardEdge = new ColEdge();
            //Change the vertex in the input edges to the corresponding vertex in the standardVertices list
            newStandardEdge.u = standardVertices.get(inputVertices.indexOf(inputEdges[i].u));
            newStandardEdge.v = standardVertices.get(inputVertices.indexOf(inputEdges[i].v));
            standardEdges[i] = newStandardEdge;
        }

        //The value -1 means that no corresponding value has been found for this graph
        chromaticNumber = -1;
        upperbound = -1;
        lowerbound = -1;
    }

    public int getNumberOfVertices() {
        return standardVertices.size();
    }

    public int getNumberOfEdges() {
        return standardEdges.length;
    }

    public List<Integer> getVertices() {
        return standardVertices;
    }

    public ColEdge[] getEdges() {
        return standardEdges;
    }

    public ArrayList<LinkedList<Integer>> getAdjList() {
        adjList = new ArrayList<>();

        for (int i = 0; i < this.getNumberOfVertices() + 1; i++) adjList.add(new LinkedList<>());

        for (int i = 0; i < this.getNumberOfEdges(); i++){
            adjList.get(standardEdges[i].u).add(standardEdges[i].v);
            adjList.get(standardEdges[i].v).add(standardEdges[i].u);
        }
        return adjList;
    }

    public int getChromaticNumber() {
        return chromaticNumber;
    }

    public int getUpperbound() {
        return upperbound;
    }

    public void setUpperbound(int upperbound) {
        this.upperbound = upperbound;
    }

    public int getLowerbound() {
        return lowerbound;
    }

    public void setLowerbound(int lowerbound) {
        this.lowerbound = lowerbound;
    }

    public void setChromaticNumber(int chromaticNumber) {
        this.chromaticNumber = chromaticNumber;
    }

    //Use this method to test
    public String toString(){
        String graph = "Graph: ";
        for (int i = 0; i < standardEdges.length; i++){
            graph += "[" + standardEdges[i].u + " " + standardEdges[i].v + "]   ";
        }
        return graph;
    }
}
