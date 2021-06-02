package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;

public class Percolation {
    private Site[][] grid;
    private int length;
    private int openSize;
    WeightedQuickUnionUF wqu;
    boolean percolated;
    ArrayList<Integer> openedTopSites;
    private class Site {
        final boolean OPEN = true;
        final boolean BLOCKED = false;
        boolean fulled;
        boolean state;
        int lengthOfGrid;
        int row;
        int col;
        int pos;
        boolean isTop = false;
        boolean isBottem = false;
        Site(int length, int row, int col) {
            lengthOfGrid = length;
            this.row = row;
            this.col = col;
            fulled = false;
            setBLOCKED();
            pos = TwoDToOneD(row, col);
            if (row == 0) {
                isTop = true;
            } else if (row == length - 1) {
                isBottem = true;
            }
        }

        private int TwoDToOneD(int row, int col) {
            return length*row + col;
        }
        public void setBLOCKED() {
            state = BLOCKED;
        }
        public void setOPEN() {
            state = OPEN;
        }
        public void setFulled() {
            fulled = true;
        }
        public void setFulled(WeightedQuickUnionUF wqu, int top) {
            if (wqu.connected(pos, top)) {
                fulled = true;
            }
        }
        public boolean peekState() {
            return state;
        }
        public boolean isFulled() {
            return fulled;
        }
    }

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N){
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        grid = new Site[N][N];
        length = N;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                grid[i][j] = new Site(length, i, j);
            }
        }
        wqu = new WeightedQuickUnionUF(N*N);
        openSize = 0;
        percolated = false;
        openedTopSites = new ArrayList<>();
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row >= length || col >= length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col].setOPEN();
        openSize += 1;
        unionNeighbors(grid[row][col]);
        updateStates(grid[row][col]);
    }
    private void updateStates(Site s) {
        if (s.isTop) {
            if (!alreadyConnectedToTop(s.pos)) {
                openedTopSites.add(s.pos);
            }
        } else if (s.isBottem) {
            if (alreadyConnectedToTop(s.pos)) {
                s.setFulled();
                percolated = true;
            }
        } else {
            if (alreadyConnectedToTop(s.pos)) {
                s.setFulled();
            }
        }
    }

    private boolean alreadyConnectedToTop(int pos) {
        boolean alreadyConnected = false;
        for (int topPos : openedTopSites) {
            if (wqu.connected(topPos, pos)) {
                alreadyConnected = true;
            }
        }
        return alreadyConnected;
    }
    private void unionNeighbors(Site s) {
        Site neighbor;
        // UP
        if (s.row - 1 > 0) {
            neighbor = grid[s.row - 1][s.col];
            wqu.union(neighbor.pos, s.pos);
        }
        // DOWN
        if (s.row + 1 < length) {
            neighbor = grid[s.row + 1][s.col];
            wqu.union(neighbor.pos, s.pos);
        }
        // LEFT
        if (s.col - 1 > 0) {
            neighbor = grid[s.row][s.col - 1];
            wqu.union(neighbor.pos, s.pos);
        }
        // RIGHT
        if (s.col + 1 < length) {
            neighbor = grid[s.row][s.col + 1];
            wqu.union(neighbor.pos, s.pos);
        }
    }

    private Site findOpenedNeighbors(int row, int col) {
        try {
            // UP
            if (isOpen(row - 1, col)) {return grid[row - 1][col];}
        } catch (Exception e) {
        }
        try {
            // DOWN
            if (isOpen(row + 1, col)) {return grid[row + 1][col];}
        } catch (Exception e) {
        }
        try {
            // LEFT
            if (isOpen(row, col - 1)) {return grid[row][col - 1];}
        } catch (Exception e) {
        }
        try {
            // RIGHT
            if (isOpen(row, col + 1)) {return grid[row][col + 1];}
        } catch (Exception e) {
        }
        return null;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= length || col < 0 || col >= length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col].peekState();
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= length || col < 0 || col >= length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col].isFulled();
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        if (openSize < 1) {
            return false;
        } else if (openSize == length*length) {
            return true;
        }
        return percolated;
    }

    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.open(0, 0);
        p.open(0, 2);
        p.open(0, 3);
        p.open(1, 2);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 1);

        int size = p.numberOfOpenSites();
        boolean a = p.percolates();
    }
}
