package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;

/**
 * Adapter class that makes a Maze searchable by search algorithms.
 * Implements the ISearchable interface.
 */
public class SearchableMaze implements ISearchable {
    private Maze maze;

    /**
     * Constructor for SearchableMaze.
     * @param maze the maze to search in
     */
    public SearchableMaze(Maze maze) {
        this.maze = maze;
    }

    @Override
    public AState getStartState() {
        return new MazeState(maze.getStartPosition());
    }

    @Override
    public AState getGoalState() {
        return new MazeState(maze.getGoalPosition());
    }

    /**
     * Finds all possible states (neighbors) reachable from the given state.
     * Includes straight and diagonal moves with different costs.
     * @param s current state
     * @return list of reachable states
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {
        if (s == null || !(s instanceof MazeState)) return new ArrayList<>();

        ArrayList<AState> neighbors = new ArrayList<>();
        MazeState mazeState = (MazeState) s;
        int r = mazeState.getPosition().getRowIndex();
        int c = mazeState.getPosition().getColumnIndex();

        // Check straight moves (cost 10)
        boolean up = addIfValid(neighbors, r - 1, c, 10);
        boolean down = addIfValid(neighbors, r + 1, c, 10);
        boolean right = addIfValid(neighbors, r, c + 1, 10);
        boolean left = addIfValid(neighbors, r, c - 1, 10);

        // Check diagonal moves (cost 15) - only if path is not blocked
        if (up || right) addIfValid(neighbors, r - 1, c + 1, 15);
        if (up || left) addIfValid(neighbors, r - 1, c - 1, 15);
        if (down || right) addIfValid(neighbors, r + 1, c + 1, 15);
        if (down || left) addIfValid(neighbors, r + 1, c - 1, 15);

        return neighbors;
    }

    /**
     * Helper method to validate coordinates and add a new state if it's a passage.
     */
    private boolean addIfValid(ArrayList<AState> neighbors, int r, int c, double cost) {
        int[][] matrix = maze.getMatrix();
        // Ensure coordinates are within bounds and the cell is not a wall (0)
        if (r >= 0 && r < matrix.length && c >= 0 && c < matrix[0].length && matrix[r][c] == 0) {
            MazeState newState = new MazeState(new Position(r, c));
            newState.setCost(cost);
            neighbors.add(newState);
            return true;
        }
        return false;
    }
}