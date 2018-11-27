import java.util.Scanner;

public class GameMode3 extends GameMode {

    public GameMode3(int chromaticNumber, int[] userColoring, boolean[][] connections) {
        super(chromaticNumber, userColoring, connections);
    }


    public void run() {
        Scanner in = new Scanner(System.in);
//        RandomOrder randomGameMode = new RandomOrder(in.nextInt(), in.nextInt());
//        //ColEdge e[] = new ColEdge[randomGameMode.edges];
//        boolean[][] randomGraph = randomGameMode.createGraph();

        int userColor;
//        int currentVertex = 0;
        boolean used;
        while (currentVertex < userColoring.length) {
            used = false;
            userColor = in.nextInt();
            for (int i = 0; i < userColoring.length; i++)
                if (userColoring[i] == userColor && super.connections[currentVertex][i] == true) {
                    System.out.println("Sorry, this colour cannot be used! Try another one.");
                    used = true;
                }
            if (!used) {
                userColoring[currentVertex] = userColor;
                currentVertex++;
            }
        }
    }

    public int[] getHint(){
        int[] arr = new int[2];
        arr[0] = currentVertex;
        int max = 0;
        for (int i = 0; i < userColoring.length; i++) if (userColoring[i] > max) max = userColoring[i];

        //Loop through all available colors
        for (int i = 1; i <= max; i++) {
            boolean condition = true;
            for (int j = 0; j < connections.length; j++)
                if (connections[currentVertex][j] && userColoring[j] == i) {
                    condition = false;
                    break;
                }

            if (condition) {
                arr[1] = i;
                return arr;
            }
        }

        arr[1] = max + 1;
        return arr;
    }
    private int currentVertex = 0;
}