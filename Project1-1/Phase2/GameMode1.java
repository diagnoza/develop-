import java.util.Scanner;

public class GameMode1 extends GameMode{

    public GameMode1(int chromaticNumber, int[] userColoring, boolean[][] connections) {
        super(chromaticNumber, userColoring, connections);
    }

    public boolean isEnd(){
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
        return (numberOfColorsUsed == super.chromaticNumber);
    }

    public void run(){
        Scanner in = new Scanner(System.in);

        do {
            System.out.println("Choose node: ");
            int node = in.nextInt();

            System.out.println("Choose color (!=0): ");
            int color = in.nextInt();

            //if (isValid)
            super.userColoring[node] = color;

        } while (this.isEnd());
    }

}
