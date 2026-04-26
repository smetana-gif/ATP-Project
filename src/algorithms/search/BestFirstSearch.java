package algorithms.search;

import java.util.*;

/**
 * BestFirstSearch algorithm implementation.
 * Uses a PriorityQueue to evaluate states based on their cost.
 */
public class BestFirstSearch extends ASearchingAlgorithm {
    String name;

    public BestFirstSearch() {
        this.name = "Best First Search";
    }

    /**
     * Solves the search problem using Best First Search.
     * @param searchable the problem to solve
     * @return Solution path from start to goal
     */
    @Override
    public Solution solve(ISearchable searchable) {
        if (searchable == null) {
            return null;
        }

        // PriorityQueue to always evaluate the state with the lowest cost first
        PriorityQueue<AState> openList = new PriorityQueue<>((s1, s2) -> Double.compare(s1.getCost(), s2.getCost()));
        HashSet<AState> closedList = new HashSet<>();

        AState startState = searchable.getStartState();
        AState goalState = searchable.getGoalState();

        openList.add(startState);

        while (!openList.isEmpty()) {
            AState currentState = openList.poll();
            AStateCounter++;

            // Check if we reached the goal
            if (currentState.equals(goalState)) {
                return backtrace(currentState);
            }

            closedList.add(currentState);

            ArrayList<AState> neighbors = searchable.getAllPossibleStates(currentState);
            for (AState neighbor : neighbors) {
                if (!closedList.contains(neighbor) && !openList.contains(neighbor)) {
                    neighbor.setCameFrom(currentState);
                    neighbor.setCost(currentState.getCost() + neighbor.getCost());
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

    /**
     * Reconstructs the solution path from the goal state back to the start.
     */
    private Solution backtrace(AState state) {
        Solution solution = new Solution();
        ArrayList<AState> path = new ArrayList<>();
        AState current = state;
        while (current != null) {
            path.add(0, current); // Add to the beginning to maintain correct order
            current = current.getCameFrom();
        }
        solution.setSolPath(path);
        return solution;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return AStateCounter;
    }
}