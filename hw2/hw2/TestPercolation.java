package hw2;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPercolation {
    @Test
    public void testPercolation() {
        Percolation p = new Percolation(5);
        p.open(0, 0);
        p.open(0, 2);
        p.open(0, 3);
        p.open(1, 2);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 1);

        int size = p.numberOfOpenSites();
        boolean percolated = p.percolates();
        assertFalse(percolated);

        boolean isFulled = p.isFull(1, 2);
        assertTrue(isFulled);
    }

    @Test
    public void testPercolationBackWash() {
        Percolation p = new Percolation(5);
        p.open(0, 0);

        p.open(0, 2);
        p.open(1, 2);
        p.open(2, 2);
        p.open(3, 2);
        p.open(4, 2);

        p.open(3, 4);
        p.open(4, 4);


        int size = p.numberOfOpenSites();
        boolean percolated = p.percolates();
        assertTrue(percolated);

        boolean isFulled1 = p.isFull(3, 4);
        assertFalse(isFulled1);

        boolean isFulled2 = p.isFull(2, 2);
        assertTrue(isFulled2);

    }

    @Test
    public void testPercolationStats() {
        PercolationStats ps = new PercolationStats(20, 10, new PercolationFactory());
        double mn = ps.mean();
        double sd = ps.stddev();
        double cl = ps.confidenceLow();
        double ch = ps.confidenceHigh();
    }
}
