package algorithms.search;

import java.util.*;

/**
 * DepthFirstSearch (DFS) algorithm implementation.
 * Explores as far as possible along each branch before backtracking using a Stack.
 */
public class DepthFirstSearch extends ASearchingAlgorithm {
    private String name;

    public DepthFirstSearch() {
        this.name = "Depth First Search";
    }

    /**
     * Solves the problem using DFS strategy.
     * @param searchable the problem to solve
     * @return Solution path
     */
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null) {
            return null;
        }

        // LIFO Stack to manage the depth-first exploration
        Stack<AState> stack = new Stack<>();
        HashSet<AState> visited = new HashSet<>();

        AState startState = searchable.getStartState();
        AState goalState = searchable.getGoalState();

        stack.push(startState);

        while (!stack.isEmpty()) {
            AState currentState = stack.pop();

            if (!visited.contains(currentState)) {
                visited.add(currentState);
                AStateCounter++;

                // Check if goal reached
                if (currentState.equals(goalState)) {
                    return backtrace(currentState);
                }

                ArrayList<AState> neighbors = searchable.getAllPossibleStates(currentState);
                for (AState neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        neighbor.setCameFrom(currentState);
                        stack.push(neighbor);
                    }
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
     * Reconstructs the path from the goal back to the start.
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