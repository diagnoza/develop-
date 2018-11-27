import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameMode1 extends GameMode {

    public GameMode1(int chromaticNumber, int[] userColoring, boolean[][] connections) {
        super(chromaticNumber, userColoring, connections);
    }

    public boolean isEnd() {
        int numberOfColorsUsed = 0;
        for (int i = 0; i < super.userColoring.length; i++) {
            boolean used = false;
            if (super.userColoring[i] != 0) {
                for (int j = 0; j < i; j++) {
                    if (super.userColoring[j] == super.userColoring[i]) {
                        used = true;
                        break;
                    }
                }

                if (!used)
                    numberOfColorsUsed++;
            }
        }
        return (numberOfColorsUsed == super.chromaticNumber && CalculateChromatic.isValid(connections, userColoring, userColoring.length));
    }

    public void run() {
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Choose node: ");
            int node = in.nextInt();

            System.out.println("Choose color (!=0): ");
            int color = in.nextInt();

            //if (isValid)
            super.userColoring[node] = color;

        } while (!this.isEnd());
    }

    public int[] getHint() {
        int[] arr = new int[2];
        //arr[0] is the vertex, a[1] is the color
        int max = 0;
        for (int i = 0; i < userColoring.length; i++) if (userColoring[i] > max) max = userColoring[i];

        List<Integer> uncoloredVertices = new ArrayList<>();

        for (int i = 0; i < userColoring.length; i++) {
            if (userColoring[i] == 0) uncoloredVertices.add(i);
        }

        for (Integer vertex: uncoloredVertices) {
            //Loop through all available colors
            for (int i = 1; i <= max; i++) {
                boolean condition = true;
                for (int j = 0; j < connections.length; j++)
                    if (connections[vertex][j] && userColoring[j] == i) {
                        condition = false;
                        break;
                    }

                if (condition) {
                    arr[0] = vertex;
                    arr[1] = i;
                    return arr;
                }
            }
        }

        arr[0] = uncoloredVertices.get(0);
        arr[1] = max + 1;
        return arr;

    }

}