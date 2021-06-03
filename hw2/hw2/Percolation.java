package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int LENGTH;
    private int openSize;
    private boolean OPENED = true;
    private boolean BLOCKED = false;
    private Site[][] grid;
    private int topSites;
    private int bottomSites;
    private WeightedQuickUnionUF percolationWQU;
    private WeightedQuickUnionUF fullWQU;

    private class Site {
        boolean state;
        int pos;
        Site(int pos) {
            this.pos = pos;
            state = BLOCKED;
        }

        public void setOPENED() {
            state = OPENED;
        }
    }
    /** create N-by-N grid, with all sites initially blocked */
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        LENGTH = N;
        openSize = 0;
        grid = new Site[LENGTH][LENGTH];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                int pos = twoToOne(i, j);
                grid[i][j] = new Site(pos);
            }
        }
        percolationWQU = new WeightedQuickUnionUF(LENGTH * LENGTH + 2);
        fullWQU = new WeightedQuickUnionUF(LENGTH * LENGTH + 1);
        topSites = LENGTH * LENGTH;
        bottomSites = LENGTH * LENGTH + 1;
    }
    private int twoToOne(int row, int col) {
        return row * LENGTH + col;
    }

    /** open the site (row, col) if it is not open already */
    public void open(int row, int col) {
        if (row < 0 || row >= LENGTH || col < 0 || col >= LENGTH) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (isOpen(row, col)) {
            return;
        }
        grid[row][col].setOPENED();
        openSize += 1;
        unionNeighbors(row, col);
    }
    private void unionNeighbors(int row, int col) {
        if (row == 0) {
            percolationWQU.union(grid[row][col].pos, topSites);
            fullWQU.union(grid[row][col].pos, topSites);
        } else if (row == LENGTH - 1) {
            percolationWQU.union(grid[row][col].pos, bottomSites);
        }

        // Neighbors
        // UP
        if (row - 1 > -1) {
            if (isOpen(row - 1, col)) {
                percolationWQU.union(grid[row][col].pos, grid[row - 1][col].pos);
                fullWQU.union(grid[row][col].pos, grid[row - 1][col].pos);
            }
        }
        // DOWN
        if (row + 1 < LENGTH) {
            if (isOpen(row + 1, col)) {
                percolationWQU.union(grid[row][col].pos, grid[row + 1][col].pos);
                fullWQU.union(grid[row][col].pos, grid[row + 1][col].pos);
            }
        }
        // LEFT
        if (col - 1 > -1) {
            if (isOpen(row, col - 1)) {
                percolationWQU.union(grid[row][col].pos, grid[row][col - 1].pos);
                fullWQU.union(grid[row][col].pos, grid[row][col - 1].pos);
            }
        }
        // DOWN
        if (col + 1 < LENGTH) {
            if (isOpen(row, col + 1)) {
                percolationWQU.union(grid[row][col].pos, grid[row][col + 1].pos);
                fullWQU.union(grid[row][col].pos, grid[row][col + 1].pos);
            }
        }
    }
    /** is the site (row, col) open? */
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= LENGTH || col < 0 || col >= LENGTH) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return grid[row][col].state;
    }

    /** is the site (row, col) full? */
    public boolean isFull(int row, int col) {
        if (row < 0 || row >= LENGTH || col < 0 || col >= LENGTH) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return fullWQU.connected(grid[row][col].pos, topSites);
    }

    /** number of open sites */
    public int numberOfOpenSites() {
        return openSize;
    }

    /** does the system percolate? */
    public boolean percolates() {
        return percolationWQU.connected(topSites, bottomSites);
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
