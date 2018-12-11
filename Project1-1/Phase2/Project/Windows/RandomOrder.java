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
    
    public int getVertices(){
        return vertices;
    }

    public boolean[][] createGraph() {
        boolean[][] graph = new boolean[vertices][vertices];
        int randomParameter;
        int randomParameter2;
        int edgesCounter = 0;
        if(edges<2*vertices){
            for (int i = 0; i < vertices; i++) {
                do {
                    randomParameter = rand.nextInt(vertices);
                } while (randomParameter == i);
	        if(!graph[i][randomParameter]) edgesCounter++;
                graph[i][randomParameter] = true;
                graph[randomParameter][i] = true;
            }
            while (edgesCounter < edges) {
                randomParameter = rand.nextInt(vertices);
                randomParameter2 = rand.nextInt(vertices);
                if (randomParameter2 != randomParameter && graph[randomParameter][randomParameter2] != true) {
                    graph[randomParameter][randomParameter2] = true;
                    graph[randomParameter2][randomParameter] = true;
            edgesCounter++;
                }
            }
        }
        else{
            edgesCounter = vertices*(vertices-1)/2;
            for(int i=0;i<vertices;i++)
                for(int j=0;j<vertices;j++)
                    if(i!=j) graph[i][j]=true;
            while (edgesCounter > edges) {
                randomParameter = rand.nextInt(vertices);
                randomParameter2 = rand.nextInt(vertices);
                if (graph[randomParameter][randomParameter2] != false) {
                    graph[randomParameter][randomParameter2] = false;
                    graph[randomParameter2][randomParameter] = false;
            edgesCounter--;
                }
            }
        }
        return graph;
    }
}