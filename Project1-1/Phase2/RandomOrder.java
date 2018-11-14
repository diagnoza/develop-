import java.util.Random;

public class RandomOrder {
    Random rand = new Random();
    public int vertices;
    public int edges;

    public RandomOrder(int inputVertices, int inputEdges) {
        vertices = inputVertices;
        edges = inputEdges;
    }

    public boolean isCorrect() {
        if (vertices > edges) return false;
        else return true;
    }

    public boolean[][] createGraph() {
        boolean[][] graph = new boolean[vertices][vertices];
        int randomParameter;
        int randomParameter2;
        for (int i = 0; i < vertices; i++) {
            do {
                randomParameter = rand.nextInt(vertices);
            } while (randomParameter == i);
            graph[i][randomParameter] = true;
            graph[randomParameter][i] = true;
        }
        int edgesCounter = vertices;
        while (edgesCounter < edges) {
            randomParameter = rand.nextInt(vertices);
            randomParameter2 = rand.nextInt(vertices);
            if (randomParameter2 != randomParameter && graph[randomParameter][randomParameter2] != true) {
                graph[randomParameter][randomParameter2] = true;
                graph[randomParameter2][randomParameter] = true;
                edgesCounter++;
            }
        }
        return graph;
    }
}