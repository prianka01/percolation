import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private double mean = 0;
    private double stdev = 0;
    private final double[] opensites;


    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
        opensites = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation obj1 = new Percolation(n);
            while (!obj1.percolates()) {
                int k = StdRandom.uniform(1, n * n + 1);
                int row = (k - 1) / n + 1;
                int col = k - n * (row - 1);
                obj1.open(row, col);

            }
            opensites[i] = (obj1.numberOfOpenSites()) / (Math.pow(n, 2));
        }
    }

    public double mean() {
        mean = StdStats.mean(opensites);
        return mean;
    }

    public double stddev() {
        stdev = StdStats.stddev(opensites);
        return stdev;
    }

    public double confidenceLo() {
        double low = 0.0;
        double t = Math.sqrt(opensites.length);
        low = mean - CONFIDENCE * (stdev / t);
        return low;
    }

    public double confidenceHi() {
        double high = 0.0;
        double t = Math.sqrt(opensites.length);
        high = mean + CONFIDENCE * (stdev / t);
        return high;
    }

    public static void main(String[] args) {

        int n, t;
        n = StdIn.readInt();
        t = StdIn.readInt();
        PercolationStats obj = new PercolationStats(n, t);
        double lowlimit = obj.confidenceLo();
        double highlimit = obj.confidenceHi();
        System.out.println("MEAN=" + obj.mean());
        System.out.println("DEVIATION=" + obj.stddev());
        System.out.println("95% confidence interval=[" + lowlimit + "," + highlimit + "]");
    }

}