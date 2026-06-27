package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.io.File;
import java.util.Observer;

/**
 * This interface is the Model part of our MVVM.
 * It holds the logic for the maze game, like making the maze and solving it.
 */
public interface IModel {

    /**
     * Creates a new maze with the given rows and columns.
     */
    void generateMaze(int rows, int cols);

    /**
     * Finds the solution for the current maze.
     */
    void solveMaze();

    /**
     * Moves the character in the maze. 
     * The direction is based on the numpad keys (8 is up, 2 is down, etc).
     */
    void moveCharacter(int direction);

    /**
     * Saves the current maze to a file.
     */
    void saveMaze(File file);

    /**
     * Loads a maze from a given file.
     */
    void loadMaze(File file);

    Maze getMaze();
    Solution getSolution();

    int getCharacterRow();
    int getCharacterCol();

    void assignObserver(Observer o);
    void shutDownServers();
}