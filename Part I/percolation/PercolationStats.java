import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] thresholdValue;
    private int trialTimes;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invaild Input");
        }
        else {
            thresholdValue = new double[trials];
            trialTimes = trials;
            for (int i = 0; i < trials; i++) {
                Percolation testMode = new Percolation(n);
                while (!testMode.percolates()) {
                    int row = StdRandom.uniform(1, n + 1);
                    int col = StdRandom.uniform(1, n + 1);
                    if (!testMode.isOpen(row, col)) {
                        testMode.open(row, col);
                    }
                }
                double p = (double) testMode.numberOfOpenSites() / (n * n);
                thresholdValue[i] = p;
            }
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholdValue);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholdValue);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double res = mean() - 1.96 * stddev() / Math.sqrt(trialTimes);
        return res;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double res = mean() + 1.96 * stddev() / Math.sqrt(trialTimes);
        return res;
    }

    // test client (see below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int trailTimes = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, trailTimes);
        System.out.println("mean\t\t=" + test.mean());
        System.out.println("stddev\t\t=" + test.stddev());
        double lo = test.confidenceLo();
        double hi = test.confidenceHi();
        System.out.println("95% confidence Interval\t=[ " + lo + ", " + hi + "]");
    }
}
