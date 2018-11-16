import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;
import java.lang.*;


public class GameMode2 extends GameMode {
    final int time = 10;
    boolean isEnd = false;


    public GameMode2(int chromaticNumber, int[] userColoring, boolean[][] connections) {
        super(chromaticNumber, userColoring, connections);
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        CountDown listener = new CountDown(time);
        final int DELAY = 1000;
        Timer t = new Timer(DELAY, listener);
        t.start();

        String c;

        while (!isEnd) {


            System.out.println("Choose node: ");
            int node = in.nextInt();

            System.out.println("Choose color (!=0): ");
            int color = in.nextInt();

            super.userColoring[node] = color;

            if(CalculateChromatic.isValid(connections, userColoring, userColoring.length)){
                System.out.println("Coloring done and is valid. Do you want to continue?");
                c = in.nextLine();
                if(c.equals('n')) break;
            }

        }


    }

    class CountDown implements ActionListener {

        public CountDown(int initialCount) {
            count = initialCount;
        }

        public void actionPerformed(ActionEvent event) {

            isEnd = true;
        }

        private int count;
    }
}









