package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Saigo Jim
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> fringe = new ArrayDeque<>();
        fringe.add(s);
        marked[s] = true;
        announce();
        while (!fringe.isEmpty()) {
            int v = fringe.remove();
            if (v == t) {
                return;
            }
            for (int neighbor : maze.adj(v)) {
                if (!marked[neighbor]) {
                    marked[neighbor] = true;
                    announce();
                    distTo[neighbor] = distTo[v] + 1;
                    announce();
                    edgeTo[neighbor] = v;
                    announce();
                    if (neighbor == t) {
                        return;
                    }
                    fringe.add(neighbor);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

