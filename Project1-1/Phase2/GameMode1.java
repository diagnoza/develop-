public class GameMode1 extends GameMode{

    public GameMode1(int chromaticNumber, int[] userColoring, boolean[][] connections) {
        super(chromaticNumber, userColoring, connections);
    }

    public boolean isEnd(){
        int maxColor = 0;
        for (int i = 0; i < super.getUserColoring().length; i++) {
            if (super.getUserColoring()[i] > maxColor) {
                maxColor = super.getUserColoring()[i];
            }
        }
        return (maxColor == super.getChromaticNumber());
    }

    public void run(){
        while (true){

            if (isEnd())
                break;
        }
    }

}
