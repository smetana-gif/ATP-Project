package algorithms.mazeGenerators;

/**
 * Generator that creates a maze with no walls.
 */
public class EmptyMazeGenerator extends AMazeGenerator {

    /**
     * Generates a maze where all cells are 0.
     * * @param rows number of rows
     * @param cols number of columns
     * @return Maze object
     */
    @Override
    public Maze generate(int rows, int cols) {
        int[][] matrix = new int[rows][cols];

        // Fill the matrix with zeros
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = 0;
            }
        }

        Position start = new Position(0, 0);
        Position goal = new Position(rows - 1, cols - 1);

        return new Maze(matrix, start, goal);
    }
}