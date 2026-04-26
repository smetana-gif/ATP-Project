package algorithms.mazeGenerators;

import java.util.Objects;

/**
 * Represents a specific cell position in a maze using row and column indices.
 */
public class Position {

    private int row;
    private int column;

    /**
     * Constructor for Position.
     * @param row row index
     * @param column column index
     */
    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex(){
        return column;
    }

    @Override
    public String toString(){
        return "{" + row + "," + column + "}";
    }

    /**
     * Checks if two positions are equal based on their coordinates.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        // Generate a unique hash based on row and column
        return Objects.hash(row, column);
    }
}