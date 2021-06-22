package hw4.puzzle;

import java.util.ArrayList;

public class Board implements WorldState {
    private Position BLANK;
    private int[][] tiles;
    private int size;
    private int hashCode;
    private int hammingEstimate;
    private int manhattanEstimate;
    private class Position {
        int x;
        int y;
        Position(int a, int b) {
            x = a;
            y = b;
        }
    }
    /** Constructs a board from an N-by-N array of tiles where
     tiles[i][j] = tile at row i, column j */
    public Board(int[][] tiles) {
        hammingEstimate = 0;
        manhattanEstimate = 0;
        hashCode = 0;
        size = tiles.length;
        this.tiles = copyNbyNGrid(tiles);
    }

    private void calHashCode(int tileVal) {
        hashCode += 31 * tileVal;
    }
    private void calHammingEstimate(int i, int j, int tileVal) {
        int goalVal = size * i + j + 1;
        if (tileVal != goalVal) {
            hammingEstimate += 1;
        }
    }
    private void calManhattanEstimate(int i, int j, int tileVal) {
        int goalRow = getGoalRow(tileVal);
        int goalCol = getGoalCol(tileVal);
        int distance = Math.abs(goalRow - i) + Math.abs(goalCol - j);
        manhattanEstimate += distance;
    }

    private int getGoalRow(int val) {
        return (val - 1) / size;
    }
    private int getGoalCol(int val) {
        return (val - 1) % size;
    }
    private int[][] copyNbyNGrid(int[][] grid) {
        int[][] array = new int[grid.length][grid.length];
        for (int i = 0; i < grid.length; i += 1) {
            for (int j = 0; j < grid.length; j += 1) {
                array[i][j] = grid[i][j];
                int tileVal = array[i][j];
                calHashCode(tileVal);
                if (tileVal == 0) {
                    BLANK = new Position(i, j);
                    continue;
                }
                calManhattanEstimate(i, j, tileVal);
                calHammingEstimate(i, j, tileVal);
            }
        }
        return array;
    }
    /** Returns value of tile at row i, column j (or 0 if blank) */
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }
    /** Returns the board size N */
    public int size() {
        return size;
    }
    /** Returns the neighbors of the current board */
    public Iterable<WorldState> neighbors() {
        ArrayList<WorldState> neighbors = new ArrayList<>();
        for (int i = BLANK.x - 1; i <= BLANK.x + 1; i += 1) {
            for (int j = BLANK.y - 1; j <= BLANK.y + 1; j += 1) {
                if (isNeighborPosition(i, j)) {
                    int[][] neighbor = swap(BLANK, new Position(i, j));
                    if (neighbor != null) {
                        neighbors.add(new Board(neighbor));
                    }
                }
            }
        }
        return neighbors;
    }
    private boolean isNeighborPosition(int x, int y) {
        return (x == BLANK.x && Math.abs(BLANK.y - y) == 1)
                || (y == BLANK.y && Math.abs(BLANK.x - x) == 1);
    }
    private int[][] swap(Position p1, Position p2) {
        if (isValidPosition(p1) && isValidPosition(p2)) {
            int[][] neighbor = copyNbyNGrid(tiles);
            int p1Value = neighbor[p1.x][p1.y];
            int p2Value = neighbor[p2.x][p2.y];
            neighbor[p2.x][p2.y] = p1Value;
            neighbor[p1.x][p1.y] = p2Value;
            return neighbor;
        }
        return null;
    }
    private boolean isValidPosition(Position p) {
        return p.x >= 0 && p.x < size && p.y >= 0 && p.y < size;
    }
    public int hamming() {
        return hammingEstimate;
    }
    public int manhattan() {
        return manhattanEstimate;
    }
    /**  Estimated distance to goal. This method should
     simply return the results of manhattan() when submitted to
     Gradescope. */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    /** Returns true if this board's tile values are the same
     position as y's */
    @Override
    public boolean equals(Object y) {
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        if (y == this) {
            return true;
        }
        Board o = (Board) y;
        return isTwoGridSame(this.tiles, o.tiles);
    }
    @Override
    public int hashCode() {
        return hashCode;
    }
    private boolean isTwoGridSame(int[][] g1, int[][] g2) {
        if (g1.length != g2.length || g1[0].length != g2[0].length) {
            return false;
        }
        int length = g1.length;
        for (int i = 0; i < length; i += 1) {
            for (int j = 0; j < length; j += 1) {
                if (g1[i][j] != g2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    /**Returns the string representation of the board. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }
}
