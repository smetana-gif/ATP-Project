package algorithms.search;

import algorithms.mazeGenerators.Position;

/**
 * Represents a state in a maze search problem, wrapping a Position.
 */
public class MazeState extends AState {
    private Position position;

    /**
     * Constructor for MazeState.
     * @param position the physical position in the maze
     */
    public MazeState(Position position) {
        // Use the position's string representation as the state identifier
        super(position != null ? position.toString() : "");
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    /**
     * Checks equality based on the underlying Position object.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MazeState mazeState = (MazeState) o;
        return position != null ? position.equals(mazeState.position) : mazeState.position == null;
    }

    @Override
    public int hashCode() {
        return position != null ? position.hashCode() : 0;
    }

    @Override
    public String toString() {
        return position.toString();
    }
}