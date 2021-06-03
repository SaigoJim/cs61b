package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int gridLen;
    private int times;
    private double[] thresholds;
    private double mean;
    private double stddev;
    private double confiLow;
    private double confiHigh;
    PercolationFactory maker;
    /** perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        gridLen = N;
        times = T;
        maker = pf;
        thresholds = new double[T];
        doTExperiments();
    }
    private void doTExperiments() {
        for (int i = 0; i < times; i += 1) {
            thresholds[i] = doSingleExperiment();
        }
        doStats();
    }
    private double doSingleExperiment() {
        Percolation p = maker.make(gridLen);
        while (!p.percolates()) {
            int row = StdRandom.uniform(gridLen);
            int col = StdRandom.uniform(gridLen);
            p.open(row, col);
        }
        int numOfOpenSites = p.numberOfOpenSites();
        double t = (double) numOfOpenSites / (double) (gridLen * gridLen);
        return t;
        //return p.numberOfOpenSites() / (gridLen*gridLen);
    }
    private void doStats() {
        mean = StdStats.mean(thresholds);
        stddev = StdStats.stddev(thresholds);
        confiLow = mean - ((1.96 * stddev) / Math.sqrt(times));
        confiHigh = mean + ((1.96 * stddev) / Math.sqrt(times));
    }
    /** sample mean of percolation threshold */
    public double mean() {
        return mean;
    }

    /** sample standard deviation of percolation threshold */
    public double stddev() {
        return stddev;
    }

    /** low endpoint of 95% confidence interval */
    public double confidenceLow() {
        return confiLow;
    }

    /** high endpoint of 95% confidence interval */
    public double confidenceHigh() {
        return confiHigh;
    }
}
