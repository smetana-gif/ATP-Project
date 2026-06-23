package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Represents a Maze with a grid, start position, and goal position.
 */
public class Maze implements Serializable {

    private static final long serialVersionUID = 1L;
    private int [][] Matrix;
    private Position StartPosition;
    private Position EndPosition;

    /**
     * Constructor for Maze.
     * @param Matrix the grid of the maze
     * @param start starting position
     * @param end goal position
     */
    public Maze(int [][] Matrix, Position start, Position end){
        this.Matrix = Matrix;
        this.StartPosition = start;
        this.EndPosition = end;
    }

    public int[][] getMatrix() {
        return Matrix;
    }

    public Position getStartPosition(){
        return StartPosition;
    }

    public Position getGoalPosition(){
        return EndPosition;
    }

    public int [][] GetMatrix(){
        return Matrix;
    }

    /**
     * Prints the maze to the console.
     * S represents start, E represents goal.
     */
    public void print() {
        for (int i = 0; i < Matrix.length; i++) {
            for (int j = 0; j < Matrix[i].length; j++) {
                // Check if current cell is start or end position
                if (i == StartPosition.getRowIndex() && j == StartPosition.getColumnIndex()) {
                    System.out.print("S ");
                } else if (i == EndPosition.getRowIndex() && j ==EndPosition.getColumnIndex()) {
                    System.out.print("E ");
                } else {
                    System.out.print(Matrix[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public byte[] toByteArray() {

        int rows = Matrix.length;
        int cols = Matrix[0].length;

        int totalSize = 12 + (rows * cols);
        byte[] mazeBytes = new byte[totalSize];

        mazeBytes[0] = (byte) (rows >> 8);
        mazeBytes[1] = (byte) (rows & 0xFF);

        mazeBytes[2] = (byte) (cols >> 8);
        mazeBytes[3] = (byte) (cols & 0xFF);

        mazeBytes[4] = (byte) (StartPosition.getRowIndex() >> 8);
        mazeBytes[5] = (byte) (StartPosition.getRowIndex() & 0xFF);

        mazeBytes[6] = (byte) (StartPosition.getColumnIndex() >> 8);
        mazeBytes[7] = (byte) (StartPosition.getColumnIndex() & 0xFF);

        mazeBytes[8] = (byte) (EndPosition.getRowIndex() >> 8);
        mazeBytes[9] = (byte) (EndPosition.getRowIndex() & 0xFF);

        mazeBytes[10] = (byte) (EndPosition.getColumnIndex() >> 8);
        mazeBytes[11] = (byte) (EndPosition.getColumnIndex() & 0xFF);

        int index = 12;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mazeBytes[index++] = (byte) Matrix[i][j];
            }
        }

        return mazeBytes;
    }

    public Maze(byte[] mazeBytes) {

        if (mazeBytes == null || mazeBytes.length < 12) {
            throw new IllegalArgumentException("Invalid maze byte array");
        }

        int rows = ((mazeBytes[0] & 0xFF) << 8) | (mazeBytes[1] & 0xFF);
        int cols = ((mazeBytes[2] & 0xFF) << 8) | (mazeBytes[3] & 0xFF);

        int startRow = ((mazeBytes[4] & 0xFF) << 8) | (mazeBytes[5] & 0xFF);
        int startCol = ((mazeBytes[6] & 0xFF) << 8) | (mazeBytes[7] & 0xFF);
        this.StartPosition = new Position(startRow, startCol);

        int endRow = ((mazeBytes[8] & 0xFF) << 8) | (mazeBytes[9] & 0xFF);
        int endCol = ((mazeBytes[10] & 0xFF) << 8) | (mazeBytes[11] & 0xFF);
        this.EndPosition = new Position(endRow, endCol);

        this.Matrix = new int[rows][cols];
        int index = 12;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.Matrix[i][j] = mazeBytes[index++];
            }
        }
    }

}