import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private int[] dx = {1, -1, 0, 0};
    private int[] dy = {0, 0, 1, -1};

    // Array representing indexes of all sites ( either it's open or blocked)
    private boolean[] sites;

    // Length of the square grid "gridLength * gridLength"
    private int gridLength;

    // Number of open sites
    private int numberOfOpenSites;

    private WeightedQuickUnionUF UDS;

    // mark the parents of cells in the top row
    private boolean[] connectedToTopRow;

    // mark the parents of cells in the bottom row
    private boolean[] connectedToBottomRow;

    private boolean isPercolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("grid dimensions must be greater than 0");
        }
        this.gridLength = n;
        int gridSize = ((n + 1) * (n + 1));
        this.sites = new boolean[gridSize];
        this.UDS = new WeightedQuickUnionUF(gridSize);
        this.connectedToTopRow = new boolean[gridSize + 1];
        this.connectedToBottomRow = new boolean[gridSize + 1];
        this.numberOfOpenSites = 0;
        this.isPercolates = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIndex(row, col);
        int index = this.getIndexFromRowAndCol(row, col);
        if (this.sites[index]) {
            return;
        }
        this.numberOfOpenSites++;
        this.sites[index] = true;
        int parent = UDS.find(index);

        // check if this cell is in the bottom row
        if (this.gridLength == row) {
            this.connectedToBottomRow[parent] = true;
        }
        // check if this cell is in the top row
        if (row == 1) {
            // mark its parent as a top row parent
            this.connectedToTopRow[parent] = true;
        }

        for (int dir = 0; dir < 4; dir++) {
            int newRow = row + dx[dir];
            int newCol = col + dy[dir];
            int newIndex = this.getIndexFromRowAndCol(newRow, newCol);
            if (this.isValid(newRow, newCol) && this.isOpen(newRow, newCol)) {
                int newParent = UDS.find(newIndex);
                this.connectParents(parent, newParent);
                this.connectParents(newParent, parent);
                UDS.union(index, newIndex);
            }

            parent = UDS.find(index);
        }
        this.isConnectedWithTopAndBottom(parent);

    }

    private void connectParents(int parent1, int parent2) {
        this.connectedToTopRow[parent1] |= this.connectedToTopRow[parent2];
        this.connectedToBottomRow[parent1] |= this.connectedToBottomRow[parent2];
        this.isConnectedWithTopAndBottom(parent1);
    }
    private void isConnectedWithTopAndBottom(int index){
        if (this.connectedToTopRow[index] && this.connectedToBottomRow[index]) {
            this.isPercolates = true;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        int index = this.getIndexFromRowAndCol(row, col);
        return this.sites[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIndex(row, col);
        int index = this.getIndexFromRowAndCol(row, col);
        int parent = UDS.find(index);
        return this.connectedToTopRow[parent] || row == 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.isPercolates;
    }

    private void checkIndex(int row, int col) {
        if (!this.isValid(row, col)) {
            throw new IllegalArgumentException("invalid index (row and col)");
        }
    }

    private boolean isValid(int row, int col) {
        return row > 0 && col > 0 && row <= this.gridLength && col <= this.gridLength;
    }

    private int getIndexFromRowAndCol(int row, int col) {
        return row * this.gridLength + col;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
