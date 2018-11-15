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

    public abstract boolean isEnd();

    public abstract void run();

    public boolean forceEnd(){
        return true;
    }

//    public boolean isValid(int chosenVertex, int chosenColor){
//        for (Integer checkNeighbors: connections.get(chosenVertex)) {
//            if (userColoring[checkNeighbors] == chosenColor){
//                return false;
//            }
//        }
//
//        return true;
//    }


}

