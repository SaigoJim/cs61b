package hw4.puzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
public class Solver {
    int moves;
    int enqueuedTimes;
    boolean solved;
    SearchNode curNode;
    ExtrinsicPQ<SearchNode> minPQ = new ArrayHeap<>();
    List<WorldState> solution = new ArrayList<>();
    HashMap<WorldState, SearchNode> hashTableSearchNode =  new HashMap<>();;

    private class SearchNode {
        WorldState content;
        int moveTo;
        int heuristic;
        boolean onQueue;
        SearchNode parentNode;

        SearchNode(WorldState ws, int mv, int h, SearchNode pN) {
            onQueue = false;
            content = ws;
            moveTo = mv;
            parentNode = pN;
            heuristic = h;
        }
        public int priority() {
            return moveTo + heuristic;
        }
        public boolean isParent(SearchNode other) {
            if (!other.equals(parentNode)) {
                return false;
            }
            return parentNode.isParent(other);
        }
        @Override
        public boolean equals(Object other) {
            if (other == null || other.getClass() != this.getClass()) {
                return false;
            }
            if (other == this) {
                return true;
            }
            SearchNode o = (SearchNode) other;
            if (isContentEqual(o)) {
                return true;
            }
            return false;
        }
        @Override
        public int hashCode() {
            return content.hashCode();
        }
        private boolean isContentEqual(SearchNode other) {
            return content.equals(other.content);
        }
    }

    public Solver(WorldState initial) {
        moves = 0;
        enqueuedTimes = 0;
        solved = false;
        curNode = new SearchNode(initial, 0, initial.estimatedDistanceToGoal(), null);

        hashTableSearchNode.put(initial, curNode);
        minPQ.insert(curNode, 0);
        curNode.onQueue = true;
        enqueuedTimes += 1;

        if (initial.isGoal()) {
            solved = true;
            curNode = minPQ.removeMin();
            curNode.onQueue = false;
            completeSolution();
        } else {
            doAStar();
        }
    }
    private void doAStar() {
        while (!minPQ.isEmpty()) {
            processMin();
            if (solved) {
                break;
            }
        }
        completeSolution();
    }
    private void completeSolution() {
        while (curNode != null) {
            solution.add(curNode.content);
            curNode = curNode.parentNode;
        }
        Collections.reverse(solution);
    }
    private void creatANewNode(WorldState worldState) {
        SearchNode neoNode = new SearchNode(worldState, curNode.moveTo + 1,
                worldState.estimatedDistanceToGoal(), curNode);
        hashTableSearchNode.put(worldState, neoNode);
        minPQ.insert(neoNode, neoNode.priority());
        neoNode.onQueue = true;
        enqueuedTimes += 1;
    }
    private void processMin() {
        curNode = minPQ.removeMin();
        curNode.onQueue = false;
        for (WorldState neighbor : curNode.content.neighbors()) {
            if (neighbor.isGoal()) {
                curNode = new SearchNode(neighbor, curNode.moveTo + 1, 0, curNode);
                solved = true;
                moves = curNode.moveTo;
                break;
            }
            if (!hashTableSearchNode.containsKey(neighbor)) {
                creatANewNode(neighbor);
                continue;
            }
            SearchNode oldNode = hashTableSearchNode.get(neighbor);
            reconsiderNode(oldNode);
        }
    }

    private void reconsiderNode(SearchNode oldNode) {
        if (isValidParentNode(oldNode)) {
            return;
        }
        if (needChangeDegree(oldNode)) {
            oldNode.moveTo = curNode.moveTo + 1;
            oldNode.parentNode = curNode;
            if (!oldNode.onQueue) {
                minPQ.insert(oldNode, oldNode.priority());
                oldNode.onQueue = true;
                enqueuedTimes += 1;
            } else {
                minPQ.changePriority(oldNode, oldNode.priority());
            }
        }
    }
    private boolean isValidParentNode(SearchNode oldNode) {
        return !oldNode.onQueue && curNode.isParent(oldNode);
    }
    private boolean needChangeDegree(SearchNode oldNode) {
        return oldNode.moveTo > curNode.moveTo + 1;
    }
    public int moves() {
        return moves;
    }
    public Iterable<WorldState> solution() {
        return solution;
    }

    public int getEnqueuedTimes() {
        return enqueuedTimes;
    }
}
