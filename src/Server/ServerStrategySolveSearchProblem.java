package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private static final String path = System.getProperty("java.io.tmpdir");

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            ObjectInputStream clientInput = new ObjectInputStream(inFromClient); //clientInput from
            ObjectOutputStream clientOutput = new ObjectOutputStream(outToClient);//clientOutput to
            Maze my_maze = (Maze) clientInput.readObject();
            System.out.println("Received maze to solve.");
            Solution ans = null;
            String mazeId = String.valueOf(Arrays.hashCode(my_maze.toByteArray()));
            File solutionFile = new File(path, "sol_" + mazeId);
            if (solutionFile.exists()) {
                System.out.println("Solution exist, retrieve from memory...");
                try (ObjectInputStream fIn = new ObjectInputStream(new FileInputStream(solutionFile))) {
                    ans = (Solution) fIn.readObject();
                }
            }else {
                System.out.println("Didn't saw before, Solving using Best First Search...");
                ISearchable searchable = new SearchableMaze(my_maze);
                ISearchingAlgorithm algo = new BestFirstSearch();
                ans = algo.solve(searchable);
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

