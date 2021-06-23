package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import edu.princeton.cs.algs4.Stack;

public class Solver {
    private Stack<WorldState> solution;
    private int moves;
    private class SearchNode implements Comparable<SearchNode> {
        private WorldState content; // WorldState stored in SearchNode
        private int moveTo; // Moves from initial to itself so far
        private int estimate; // Manhattan estimate
        private int priority; // estimate + moves
        private SearchNode parentNode;

        public SearchNode(WorldState worldState, int moves, SearchNode prevNode) {
            content = worldState;
            moveTo = moves;
            estimate = content.estimatedDistanceToGoal();
            parentNode = prevNode;
            priority = estimate + moveTo;
        }

        public int getMoveTo() {
            return moveTo;
        }
        public int getPriority() {
            return priority;
        }
        public boolean isParentContent(WorldState worldState) {
            if (parentNode != null && worldState.equals(parentNode.content)) {
                return true;
            }
            return false;
        }
        @Override
        public int compareTo(SearchNode o) {
            return getPriority() - o.getPriority();
        }
    }
    /** Constructor which solves the puzzle, computing
     everything necessary for moves() and solution() to
     not have to solve the problem again. Solves the
     puzzle using the A* algorithm. Assumes a solution exists. */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        SearchNode currNode = new SearchNode(initial, 0, null);
        minPQ.insert(currNode);

        currNode = doAStar(minPQ);
        solution = completeSolution(currNode);
        moves = solution.size() - 1;
    }

    private SearchNode doAStar(MinPQ<SearchNode> minPQ) {
        SearchNode currNode = null;
        while (!minPQ.isEmpty()) {
            currNode = minPQ.delMin();
            if (currNode.content.isGoal()) {
                break;
            }
            for (WorldState neighbor : currNode.content.neighbors()) {
                if (!currNode.isParentContent(neighbor)) {
                    SearchNode newNode = new SearchNode(neighbor, currNode.getMoveTo() + 1, currNode);
                    minPQ.insert(newNode);
                }
            }
        }
        return currNode;
    }

    private Stack<WorldState> completeSolution(SearchNode currNode) {
        Stack<WorldState> worldStatesStack = new Stack<>();
        while (currNode != null) {
            worldStatesStack.push(currNode.content);
            currNode = currNode.parentNode;
        }
        return worldStatesStack;
    }
    /**  Returns the minimum number of moves to solve the puzzle starting
     at the initial WorldState. */
    public int moves() {
        return moves;
    }
    /** Returns a sequence of WorldStates from the initial WorldState
     to the solution. */
    public Iterable<WorldState> solution() {
        return solution;
    }
}
