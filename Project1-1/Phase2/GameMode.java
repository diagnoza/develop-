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

    public int getChromaticNumber() {
        return chromaticNumber;
    }

    public void setChromaticNumber(int chromaticNumber) {
        this.chromaticNumber = chromaticNumber;
    }

    public int[] getUserColoring() {
        return userColoring;
    }

    public void setUserColoring(int[] userColoring) {
        this.userColoring = userColoring;
    }

    public boolean[][] getConnections() {
        return connections;
    }

    public void setConnections(boolean[][] connections) {
        this.connections = connections;
    }

    public static int[] getColoredGraph() {
        return coloredGraph;
    }

    public static void setColoredGraph(int[] coloredGraph) {
        GameMode.coloredGraph = coloredGraph;
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

