
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private boolean[] grid;
    private int size;
    private int numOfOpen;
    private WeightedQuickUnionUF uf;
    private int top;
    private int bottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Please enter a positive number");
        }
        else {
            // Initialize grid and all value are false by default
            grid = new boolean[n * n + 2];
            size = n;
            numOfOpen = 0;
            uf = new WeightedQuickUnionUF(n * n + 2);
            top = 0;
            bottom = n*n-1;
        }
    }

    private boolean isValid(int row, int col) {
        if (row >= 1 && col >= 1 && row <= size && col <= size)
            return true;
        else
            return false;
    }

    private int getUfIndex(int row, int col)
    {
        return (row - 1)*size + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isValid(row, col)) {
            if (!isOpen(row, col)) {
                // index corresponding to [row, col] in the union find
                int index = getUfIndex(row, col);
                grid[index] = true;
                numOfOpen++;
                if (row == 1) {
                    uf.union(top, index);
                }
                if (row == size) {
                    uf.union(bottom, index);
                }
                // union adjacent opened site
                for (int i = -1; i <= 1; i += 2) {
                    if (isValid(row + i, col) && isOpen(row + i, col)) {
                        uf.union(index, getUfIndex(row + i, col));
                    }
                    if (isValid(row, col + i) && isOpen(row, col + i)) {
                        uf.union(index, getUfIndex(row, col + i));
                    }
                }
            }
        }
        else {
            throw new IllegalArgumentException("Indexes of row and col are invalid");
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (isValid(row, col)) {
            return grid[getUfIndex(row, col)];
        }
        else {
            throw new IllegalArgumentException("Indexes of row and col are invalid");
        }

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (isValid(row, col)) {
            if (isOpen(row, col))
            {
                return uf.find(top) == uf.find(getUfIndex(row, col));
            }
            else
            {
                return false;
            }

        }
        else {
            throw new IllegalArgumentException("Indexes of row and col are invalid");
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOfOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
