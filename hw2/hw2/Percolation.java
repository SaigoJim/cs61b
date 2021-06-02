package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private Site[][] grid;
    private int length;
    private int openSize;
    WeightedQuickUnionUF wqu;
    boolean percolated;
    private class Site {
        final boolean OPEN = true;
        final boolean BLOKED = false;
        boolean fulled;
        boolean state;

        Site(boolean state) {
            this.state = state;
            fulled = false;
        }

        public void setOPEN() {
            state = OPEN;
        }

        public void setFulled() {
            fulled = true;
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
                grid[i][j] = new Site(false);
            }
        }
        wqu = new WeightedQuickUnionUF(N*N);
        openSize = 0;
        percolated = false;
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
        unionNeighbors(row, col);
        updateConfig(row, col);
    }

    private void updateConfig(int row, int col) {

    }
    private void unionNeighbors(int row, int col) {
        int posInWQU = TwoDToOneD(row, col);
        int posOfOpenedNeighbor = findOpenedNeighbors(row, col);
        if (posOfOpenedNeighbor < length*length) {
            wqu.union(posOfOpenedNeighbor, posInWQU);
        }
    }

    private int findOpenedNeighbors(int row, int col) {
        try {
            // UP
            if (isOpen(row - 1, col)) {return TwoDToOneD(row - 1, col);}
        } catch (Exception e) {
        }
        try {
            // DOWN
            if (isOpen(row + 1, col)) {return TwoDToOneD(row + 1, col);}
        } catch (Exception e) {
        }
        try {
            // LEFT
            if (isOpen(row, col - 1)) {return TwoDToOneD(row, col - 1);}
        } catch (Exception e) {
        }
        try {
            // RIGHT
            if (isOpen(row, col + 1)) {return TwoDToOneD(row, col + 1);}
        } catch (Exception e) {
        }
        return length*length;
    }

    private int TwoDToOneD(int row, int col) {
        return length*row + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= length || col < 0 || col >= length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col].peekState();
    }
    public boolean isTwoConnected(int r1, int c1, int r2, int c2) {
        int pos1 = TwoDToOneD(r1, c1);
        int pos2 = TwoDToOneD(r2, c2);
        return wqu.connected(pos1, pos2);
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
        Percolation p = new Percolation(4);
        p.open(0, 0);
        p.open(0, 1);

        int size = p.numberOfOpenSites();
        boolean b = p.isTwoConnected(0, 0, 0, 1);
    }
}
