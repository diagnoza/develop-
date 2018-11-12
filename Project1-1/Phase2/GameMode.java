package com.tuanh;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class GameMode {
    private int chromaticNumber;
    private int[] userColoring;
    private ArrayList<LinkedList<Integer>> connections;
    private static int[] coloredGraph;

    public GameMode(int chromaticNumber, int[] userColoring, ArrayList<LinkedList<Integer>> connections) {
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

    public ArrayList<LinkedList<Integer>> getConnections() {
        return connections;
    }

    public void setConnections(ArrayList<LinkedList<Integer>> connections) {
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

    }

    public boolean isValid(int chosenVertex, int chosenColor){
        for (Integer checkNeighbors: connections.get(chosenVertex)) {
            if (userColoring[checkNeighbors] == chosenColor){
                return false;
            }
        }

        return true;
    }


}

