import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] site;
    private int n;
    private int countOpen;
    private WeightedQuickUnionUF d;
    private WeightedQuickUnionUF d2;
    private int top;
    private int bottom;
    private int siteId;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("Wrong value of N");
        else {
            d = new WeightedQuickUnionUF(n*n + 2);
            d2 = new WeightedQuickUnionUF(n*n + 1);
            site = new boolean[n+2][n+2];
            countOpen = 0;
            this.n = n;
            top = n*n;
            bottom = n*n+1;
        }
    }

    // opens the site (row, col) if it is not open already
    private boolean checkIndex(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) return false;
        else return true;
    }
    public void open(int row, int col) {
        if (!checkIndex(row, col)) {
            throw new IllegalArgumentException("outside of prescribed range");
        } else {
            siteId = (row - 1)*n + col - 1;
            if (!site[row][col]) {
                site[row][col] = true;
                if (row == 1) {
                    d.union(siteId, top);
                    d2.union(siteId, top);
                }
                if (row == n) d.union(siteId, bottom);
                countOpen++;
            }
            if (col < n && site[row][col+1]) {
                d.union(siteId, siteId + 1);
                d2.union(siteId, siteId + 1);
            }
            if (col > 1 && site[row][col - 1]) {
                d.union(siteId, siteId - 1);
                d2.union(siteId, siteId - 1);
            }
            if (row < n && site[row + 1][col]) {
                d.union(siteId, siteId + n);
                d2.union(siteId, siteId + n);
            }
            if (row > 1 && site[row - 1][col]) {
                d.union(siteId, siteId - n);
                d2.union(siteId, siteId - n);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!checkIndex(row, col))
            throw new IllegalArgumentException("outside of prescribed range");
        else return site[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!checkIndex(row, col))
            throw new IllegalArgumentException("outside of prescribed range");
        else if (isOpen(row, col))
            return d2.find((row-1)*n+col-1) == d2.find(n * n);
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return d.find(top) == d.find(bottom);
    }

    public static void main(String[] args) {
    }
}