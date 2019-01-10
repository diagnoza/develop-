public class GeneticAlgorithm {

    private final static int POPULATION_SIZE = Main.POPULATION;
    private static Individual[] PopulationContainer = new Individual[POPULATION_SIZE];
    private final static int UPPERBOUND = Main.upB;
    private static int numberOfNodes, numberOfEdges;
    private static ColEdge[] e;
    private static boolean[][] adjList;
    private static Individual[] parent1, parent2, children;
    private static double SELECTION_RATE, CROSSOVER_RATE;

    static {
        parent1 = new Individual[POPULATION_SIZE];
        parent2 = new Individual[POPULATION_SIZE];
        children = new Individual[POPULATION_SIZE];

        SELECTION_RATE = .5;
        CROSSOVER_RATE = 1;
    }


    public class Individual {
        protected double fitness;
        protected int[] NodesColors;

        public Individual() {
            fitness = 0;
            NodesColors = new int[numberOfNodes];
        }

        public double getFitness() {
            return fitness;
        }
    }

    public GeneticAlgorithm(int numberOfNodes, int numberOfEdges, ColEdge[] e) {
        GeneticAlgorithm.numberOfNodes = numberOfNodes;
        GeneticAlgorithm.numberOfEdges = numberOfEdges;
        GeneticAlgorithm.e = e;
        for(Individual individual : children)
            individual = new Individual();

        CreateAdjList();
        Initialize();
        CalculateFitness();
        HeapSort.sort(PopulationContainer);
        Selection();
        Crossover();
        System.out.println(parent1[0].NodesColors);
        System.out.println(parent2[0].NodesColors);
        System.out.println(children[0].NodesColors);
    }


    /*Creating a generation*/
    private void Initialize() {
        /*
        DO NOT use foreach loop when you wish to alter something in the process of iterating - DOESN'T WORK

        for (Individual individual : PopulationContainer) {
            individual = new Individual();
            for (int node : individual.NodesColors)
                node = (int) (Math.random() * UPPERBOUND);

        }
        */

        /*The ugly, but eh, working way of doing this*/
        for (int i = 0; i < PopulationContainer.length; i++) {
            PopulationContainer[i] = new Individual();
            for (int j = 0; j < PopulationContainer[i].NodesColors.length; j++)
                PopulationContainer[i].NodesColors[j] = (int) (Math.random() * UPPERBOUND);
        }

    }

    private void Selection() {

        double secG = POPULATION_SIZE * SELECTION_RATE;

        for (int i = 0; i < POPULATION_SIZE; i++) {
            int k = (int) (Math.random() * (secG));
            parent1[i] = PopulationContainer[k];
        }

        for (int i = 0; i < POPULATION_SIZE; i++) {
            int k = (int) (Math.random() * (secG));
            parent2[i] = PopulationContainer[k];
        }

    }

    private void Crossover() {
        for (int p = 0; p < POPULATION_SIZE; p++) {
            int split = (int) (Math.random() * numberOfNodes);

            for (int i = 0; i < split; i++)
                children[p].NodesColors[i] = parent1[p].NodesColors[i];
            for (int i = split; i < numberOfNodes; i++)
                children[p].NodesColors[i] = parent2[p].NodesColors[i];
        }

    }

    private static void CalculateFitness() {
        double redLines;

        for (Individual individual : PopulationContainer) {
            redLines = 0;
            for (int i = 0; i < individual.NodesColors.length; i++)
                for (int j = 0; j < individual.NodesColors.length; j++)
                    if (adjList[i][j] && individual.NodesColors[i] == individual.NodesColors[j]) redLines++;

            if (redLines == 0) individual.fitness = 1;
            else if (redLines == 1) individual.fitness = .99;
            else individual.fitness = 1 / redLines;
        }

    }


    private static void CreateAdjList() {
        adjList = new boolean[numberOfNodes][numberOfNodes];
        for (ColEdge edge : e)
            adjList[edge.u - 1][edge.v - 1] = true;
    }


}
