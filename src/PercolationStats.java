import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;
    private double[] p;
    private double confidence95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("outside of prescribed range");
        else {
            p = new double[trials];
            for (int i = 0; i < p.length; i++) {
                Percolation grid = new Percolation(n);
                while (!grid.percolates()) {
                    int row = StdRandom.uniformInt(1, n+1);
                    int col = StdRandom.uniformInt(1, n+1);
                    grid.open(row, col);
                }
                p[i] = ((double) grid.numberOfOpenSites())/ (n*n);
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(p);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(p);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return  (mean() - (confidence95*stddev())/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (confidence95*stddev())/Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats main = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean = " + main.mean());
        System.out.println("stddev = " + main.stddev());
        System.out.println("95% confidence interval = " + "[" + main.confidenceLo() + ", " + main.confidenceHi() + "]");
    }
}