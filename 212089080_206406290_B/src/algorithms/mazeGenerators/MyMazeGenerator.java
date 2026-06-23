package algorithms.mazeGenerators;

import java.util.Stack;
import java.util.Random;
import java.util.ArrayList;

/**
 * A maze generator that uses a random-walk/binary tree style algorithm.
 */
public class MyMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze with a guaranteed path from start to end.
     * @param rows number of rows
     * @param cols number of columns
     * @return Maze object
     */
    @Override
    public Maze generate(int rows, int cols) {
        int[][] matrix = new int[rows][cols];

        // Initialize the maze with walls
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = 1;
            }
        }

        Random random = new Random();

        // Create passages using a grid-based pattern
        for (int r = 0; r < rows; r += 2) {
            for (int c = 0; c < cols; c += 2) {
                matrix[r][c] = 0;
                boolean canGoUp = (r >= 2);
                boolean canGoLeft = (c >= 2);

                // Randomly connect current cell to a neighbor to create paths
                if (canGoLeft && canGoUp) {
                    if (random.nextInt(2) == 0) {
                        matrix[r - 1][c] = 0;
                    } else {
                        matrix[r][c - 1] = 0;
                    }
                } else if (canGoUp) {
                    matrix[r - 1][c] = 0;
                } else if (canGoLeft) {
                    matrix[r][c - 1] = 0;
                }
            }
        }

        // Ensure the goal area is reachable
        matrix[rows - 1][cols - 1] = 0;
        if (rows > 1) matrix[rows - 2][cols - 1] = 0;
        if (cols > 1) matrix[rows - 1][cols - 2] = 0;

        Position start = new Position(0, 0);
        Position end = new Position(rows - 1, cols - 1);

        return new Maze(matrix, start, end);
    }
}