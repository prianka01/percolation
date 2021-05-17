import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int no;
    private int opensites = 0;
    private final WeightedQuickUnionUF obj;

    public Percolation(int n) {
        no = n;
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
        grid = new boolean[n][n];
        obj = new WeightedQuickUnionUF(no * no + 2);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }

    private int value(int row, int col) {
        return no * (row - 1) + col;
    }

    public void open(int row, int col) {
        if (row > no || row < 1 || col > no || col < 1)
            throw new IllegalArgumentException("n should be greater than 0");
        int val = value(row, col);
        if (val >= 1 && val <= no)
            obj.union(val, 0);
        if (val >= (no - 1) * no + 1 && val <= (no * no))
            obj.union(val, no * no + 1);
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            opensites += 1;
        }
        row -= 1;
        col -= 1;
        if (row < no - 1 && grid[row + 1][col])
            obj.union(val, val + no);
        if (col < no - 1 && grid[row][col + 1])
            obj.union(val, val + 1);
        if (row >= 1 && grid[row - 1][col])
            obj.union(val, val - no);
        if (col >= 1 && grid[row][col - 1])
            obj.union(val, val - 1);

    }

    public boolean isOpen(int row, int col) {
        if (row > no || row < 1 || col > no || col < 1)
            throw new IllegalArgumentException("n should be greater than 0");
        return (grid[row - 1][col - 1]);

    }

    public boolean isFull(int row, int col) {
        if (row > no || row < 1 || col > no || col < 1)
            throw new IllegalArgumentException("n should be greater than 0");
        int val = value(row, col);
        int p = obj.find(val);
        int q = obj.find(0);
        return (p == q);
    }

    public int numberOfOpenSites() {
        return opensites;
    }

    public boolean percolates() {
        int p = obj.find(0);
        int q = obj.find(no * no + 1);
        return (p == q);
    }

    public static void main(String[] args) {
        int n;
        n = StdIn.readInt();
        Percolation obj = new Percolation(n);
//        obj.open(1,1);
//        obj.open(2,1);
//        obj.open(3,1);
//        obj.open(2,3);
//        obj.open(1,3);
//        System.out.println(obj.isFull(2, 3) + " " + obj.isOpen(2,3));
        while (!obj.percolates()) {
            int k = (int) (Math.random() * (n * n - 1)) + 1;
            int row = (k - 1) / n + 1;
            int col = k - n * (row - 1);
            if (!obj.isOpen(row, col))
                obj.open(row, col);
        }
        System.out.println("Open sites=" + obj.numberOfOpenSites()
        );
    }
}
