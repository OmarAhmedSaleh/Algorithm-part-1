import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // number of independent experiments on an n-by-n grid
    private int trials;

    // Store all threshold results
    private double[] thresholdList;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1) {
            throw new IllegalArgumentException("Grid must have at least one row and column");
        }

        if (trials < 1) {
            throw new IllegalArgumentException("You must run percolation at least once");
        }
        this.trials = trials;
        thresholdList = new double[trials];

        for(int i = 0;i < trials ; i++){
            Percolation percolation = new Percolation(n);

            while(!percolation.percolates()){
                int row = StdRandom.uniform(1 , n + 1);
                int column = StdRandom.uniform(1 , n + 1);

                percolation.open(row , column);
            }
            thresholdList[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholdList);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdList);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {

        int[] gridLength = new int[]{100, 200, 2, 55,  100};
        int[] trials = new int[]{200, 100, 2, 1, 100};

        for(int i = 0; i < gridLength.length ; i++){
            PercolationStats stats = new PercolationStats(gridLength[i], trials[i]);

            StdOut.println("mean = "+ stats.mean());
            StdOut.println("stddev = "+ stats.stddev());
            StdOut.println("95% confidence interval = "+ stats.confidenceLo() + ", " + stats.confidenceHi());
        }
    }

}
