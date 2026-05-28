package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import java.io.*;


public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            int[] mazeSize = (int[]) fromClient.readObject();
            int rows = mazeSize[0];
            int cols = mazeSize[1];
            System.out.println("Strategy: Received request for maze of size " + rows + "x" + cols);
            IMazeGenerator generator = new MyMazeGenerator();
            Maze maze = generator.generate(rows, cols);
            byte[] mazeBytes = maze.toByteArray();
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            MyCompressorOutputStream compressor = new MyCompressorOutputStream(byteOut);
            compressor.write(mazeBytes);
            compressor.flush();
            compressor.close();
            byte[] compressedMazeBytes = byteOut.toByteArray();
            toClient.writeObject(compressedMazeBytes);
            toClient.flush();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


