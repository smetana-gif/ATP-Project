package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.util.Arrays;

public class ServerStrategySolveSearchProblem implements IServerStrategy {


    private static final String path = System.getProperty("java.io.tmpdir");

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream clientInput = new ObjectInputStream(inFromClient);
            ObjectOutputStream clientOutput = new ObjectOutputStream(outToClient);

            Maze maze = (Maze) clientInput.readObject();
            System.out.println("Received maze to solve.");

            String algorithmName = Configurations.getInstance().getMazeSearchingAlgorithm();
            ISearchingAlgorithm searcher;

            if (algorithmName.equalsIgnoreCase("BreadthFirstSearch")) {
                searcher = new BreadthFirstSearch();
            } else if (algorithmName.equalsIgnoreCase("DepthFirstSearch")) {
                searcher = new DepthFirstSearch();
            } else {
                searcher = new BestFirstSearch();
            }

            Solution ans = null;
            String mazeId = String.valueOf(Arrays.hashCode(maze.toByteArray()));
            File solutionFile = new File(path, "sol_" + mazeId);

            if (solutionFile.exists()) {
                System.out.println("Solution exist, retrieve from memory...");
                try (ObjectInputStream fIn = new ObjectInputStream(new FileInputStream(solutionFile))) {
                    ans = (Solution) fIn.readObject();
                }
            } else {
                System.out.println("Didn't saw before, Solving using " + searcher.getName() + "...");

                ISearchable searchable = new SearchableMaze(maze);
                ans = searcher.solve(searchable);

                synchronized (ServerStrategySolveSearchProblem.class) {
                    try (ObjectOutputStream fOut = new ObjectOutputStream(new FileOutputStream(solutionFile))) {
                        fOut.writeObject(ans);
                        fOut.flush();
                    }
                }
                System.out.println("The solution saved to memory.");
            }

            clientOutput.writeObject(ans);
            clientOutput.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}