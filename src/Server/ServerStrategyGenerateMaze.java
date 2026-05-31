package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;


// Strategy for generating and sending a compressed maze to the client
public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            // Read the maze dimensions requested by the client [rows, cols]
            int[] mazeSize = (int[]) fromClient.readObject();
            int rows = mazeSize[0];
            int cols = mazeSize[1];
            System.out.println("Strategy: Received request for maze of size " + rows + "x" + cols);
            // Get the compressed maze bytes and send them back
            byte[] compressedMazeBytes = getBytes(rows, cols);
            toClient.writeObject(compressedMazeBytes);
            toClient.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Helper method to handle maze creation and compression logic (Supports SOLID-single responsibility)
    private static byte[] getBytes(int rows, int cols) throws IOException {
        // Choose generation algorithm based on config file
        String algorithm = Configurations.getInstance().getMazeGeneratingAlgorithm();
        IMazeGenerator mg;
        if (algorithm.equalsIgnoreCase("SimpleMazeGenerator")) {
            mg = new SimpleMazeGenerator();
        } else {
            mg = new MyMazeGenerator();
        }
        // Generate maze and get its uncompressed byte array
        Maze maze = mg.generate(rows, cols);
        byte[] mazeBytes = maze.toByteArray();

        // Compress the maze bytes using our custom compressor stream
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteOut);
        compressor.write(mazeBytes);
        compressor.flush();
        compressor.close();

        return byteOut.toByteArray();
    }
}