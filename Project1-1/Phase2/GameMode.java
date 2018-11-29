public abstract class GameMode {
    protected int chromaticNumber;
    protected int[] userColoring;
    protected boolean[][] connections;
    protected static int[] coloredGraph;

    public GameMode(int chromaticNumber, int[] userColoring, boolean[][] connections) {
        this.chromaticNumber = chromaticNumber;
        this.userColoring = userColoring;
        this.connections = connections;
    }

    public abstract void run();

    public abstract int[] getHint();

    public boolean forceEnd(){
        return true;
    }

    public boolean isValid(int chosenVertex, int chosenColor){
        for (int i = 0; i < connections.length; i++) {
            if (connections[i][chosenVertex] && userColoring[i] == chosenColor){
                return false;
            }
        }
        return true;
    }


}
