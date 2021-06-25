package lab11.graphs;

import edu.princeton.cs.algs4.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        dfs();
    }

    // Helper methods go here
    private void dfs() {
        int source = maze.xyTo1D(1, 1);
        int[] edgeToFake = new int[maze.V()];
        edgeToFake[source] = source;
        Stack<Integer> fringe = new Stack<>();
        fringe.push(source);
        while (!fringe.isEmpty()) {
            int top = fringe.peek();
            marked[top] = true;
            announce();
            boolean isEnd = true;
            for (int neighbor : maze.adj(top)) {
                if (marked[neighbor] && neighbor != edgeToFake[top]) {
                    int cycleEnd = neighbor;
                    edgeTo[cycleEnd] = top;
                    while (top != cycleEnd) {
                        edgeTo[top] = edgeToFake[top];
                        top = edgeToFake[top];
                    }
                    announce();
                    return;
                }
                if (!marked[neighbor]) {
                    edgeToFake[neighbor] = top;
                    fringe.push(neighbor);
                    isEnd = false;
                    break;
                }
            }
            if (isEnd) {
                fringe.pop();
            }
        }
    }
}

