package algorithms.mazeGenerators;

/**
 * Represents a Maze with a grid, start position, and goal position.
 */
public class Maze {
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
}