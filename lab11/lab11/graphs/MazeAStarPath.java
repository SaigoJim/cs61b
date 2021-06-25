package lab11.graphs;


/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar() {
        ExtrinsicPQ<Integer> minPQ = new ArrayHeap<>();
        minPQ.insert(s, h(s));
        while (!minPQ.isEmpty()) {
            int v = minPQ.removeMin();
            marked[v] = true;
            announce();
            if (v == t) {
                return;
            }
            for (int neighbor : maze.adj(v)) {
                if (!marked[neighbor]) {
                    // RELAX
                    if (distTo[neighbor] == Integer.MAX_VALUE) {
                        distTo[neighbor] = distTo[v] + 1;
                        edgeTo[neighbor] = v;

                        minPQ.insert(neighbor, distTo[neighbor] + h(neighbor));
                    } else if (distTo[v] + 1 < distTo[neighbor]) {
                        minPQ.changePriority(neighbor, distTo[v] + 1);
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar();
    }

}

