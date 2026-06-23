package algorithms.mazeGenerators;

import java.util.Random;

/**
 * A simple maze generator that populates the grid randomly
 * and ensures a basic path exists.
 */
public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze with random walls and a guaranteed path along the edges.
     * @param rows number of rows
     * @param cols number of columns
     * @return Maze object
     */
    @Override
    public Maze generate(int rows, int cols) {
        int[][] matrix = new int[rows][cols];
        Random rand = new Random();

        // Fill the maze with random walls (1) and passages (0)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(2);
            }
        }

        Position start = new Position(0, 0);
        Position end = new Position(rows - 1, cols - 1);

        // Ensure at least one simple path exists from start to end
        for (int i = 0; i < rows; i++) {
            matrix[i][0] = 0;
        }
        for (int j = 0; j < cols; j++) {
            matrix[rows - 1][j] = 0;
        }

        return new Maze(matrix, start, end);
    }
}