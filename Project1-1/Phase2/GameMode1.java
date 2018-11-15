public class GameMode1 extends GameMode{

    public GameMode1(int chromaticNumber, int[] userColoring, boolean[][] connections) {
        super(chromaticNumber, userColoring, connections);
    }

    public boolean isEnd(){
        int maxColor = 0;
        for (int i = 0; i < super.userColoring.length; i++) {
            if (super.userColoring[i] > maxColor) {
                maxColor = super.userColoring[i];
            }
        }
        return (maxColor == super.chromaticNumber);
    }

    public void run(){
        while (true){

            if (isEnd())
                break;
        }
    }

}
