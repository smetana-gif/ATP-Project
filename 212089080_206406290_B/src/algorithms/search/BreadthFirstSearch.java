package algorithms.search;

import java.util.*;

/**
 * BreadthFirstSearch (BFS) algorithm implementation.
 * Explores neighbors level by level using a Queue.
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {
    private String name;

    public BreadthFirstSearch() {
        this.name = "Breadth First Search";
    }

    /**
     * Solves the problem using BFS strategy.
     * @param searchable the problem to solve
     * @return Solution path
     */
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null) {
            return null;
        }

        // FIFO Queue to manage the order of state exploration
        Queue<AState> openList = new LinkedList<>();
        HashSet<AState> visited = new HashSet<>();

        AState startState = searchable.getStartState();
        AState goalState = searchable.getGoalState();

        openList.add(startState);
        visited.add(startState);

        while (!openList.isEmpty()) {
            AState currentState = openList.poll();
            AStateCounter++;

            // Goal check
            if (currentState.equals(goalState)) {
                return backtrace(currentState);
            }

            ArrayList<AState> neighbors = searchable.getAllPossibleStates(currentState);
            for (AState neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    neighbor.setCameFrom(currentState);
                    openList.add(neighbor);
                }
            }
        }

        return new Solution();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return AStateCounter;
    }

    /**
     * Reconstructs the path from the goal state back to the start.
     */
    private Solution backtrace(AState state) {
        Solution solution = new Solution();
        ArrayList<AState> path = new ArrayList<>();
        AState current = state;
        while (current != null) {
            path.add(0, current);
            current = current.getCameFrom();
        }
        solution.setSolPath(path);
        return solution;
    }
}