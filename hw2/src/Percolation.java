import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    // TODO: Add any necessary instance variables.
    private WeightedQuickUnionUF unionSet;
    private boolean[][] nDiagram;

    private int n;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        n = N;
        nDiagram = new boolean[n][n];
        unionSet = new WeightedQuickUnionUF(n * n);

        for (int i = 1; i < N; i += 1) {
            unionSet.union(0, i);
            unionSet.union(n * (n - 1), n * (n - 1) + i);
        }
    }

    public int xyTo1D(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        return row * n + col;
    }

    public boolean validXY(int row, int col) {
        return row >= 0 && row < n && col >= 0 && col < n;
    }

    public void open(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        nDiagram[row][col] = true;
        int[][] dir = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int i = 0; i < 4; i += 1) {
            int newRow = row + dir[i][0];
            int newCol = col + dir[i][1];
            if (validXY(newRow, newCol) && isOpen(newRow, newCol)) {
                unionSet.union(xyTo1D(row + dir[i][0], col + dir[i][1]), xyTo1D(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        return nDiagram[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n) {
            throw new IndexOutOfBoundsException();
        }
        return isOpen(row, col) && unionSet.connected(0, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        int count = unionSet.count();
        if (!nDiagram[0][0]) {
            count -= 1;
        } else if (!nDiagram[n - 1][n - 1]) {
            count -= 1;
        }
        return count;
    }

    public boolean percolates() {
        boolean percolate = false;
        for (int i = 0; i < n; i += 1) {
            percolate |= isFull(n - 1, i);
        }
        return percolate;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).


}
