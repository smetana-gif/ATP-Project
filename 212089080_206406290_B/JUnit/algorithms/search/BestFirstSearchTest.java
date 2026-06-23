package algorithms.search;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    @Test
    void testSolveWithNull() {
        BestFirstSearch bestFirstSearch = new BestFirstSearch();
        Solution result = bestFirstSearch.solve(null);
        assertNull(result);

    }

    @Test
    void testAlgorithmName() {
        BestFirstSearch bestFirstSearch = new BestFirstSearch();
        assertEquals("Best First Search", bestFirstSearch.getName());
    }

    @Test
    void testValidMazeHasSolution() {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(10, 10);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        BestFirstSearch bestFirstSearch = new BestFirstSearch();
        Solution solution = bestFirstSearch.solve(searchableMaze);

        assertNotNull(solution);
        assertNotNull(solution.getSolutionPath());
        assertTrue(solution.getSolutionPath().size() > 0);
    }
}