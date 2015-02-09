public class Percolation {
    private static final byte SITE_BLOCKED = 0;
    private static final byte SITE_OPEN = 1;

    private int[][] grid;

    private int N;

    private WeightedQuickUnionUF uf;
    private int ufSize;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        
        this.N = N;
        grid = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                grid[i][j] = SITE_BLOCKED;

        // initialized WeightedQuickUnionUF with N^2+2 cells. top cell to link
        // the first N cells, and bottom cell to link last N cells
        ufSize = N * N + 2;
        uf = new WeightedQuickUnionUF(ufSize);

        for (int i = 0; i < N; i++) {
            uf.union(0, i + 1);
            uf.union(ufSize - 1, ufSize - 1 - 1 - i);
        }
    }

    private void checkBounds(String methodName, int i, int j) {
        if ((i < 0) || (i >= N))
            throw new IndexOutOfBoundsException(
                    "IndexOutOfBoundsException while calling method ["
                            + methodName + "] with i=" + i);
        if ((j < 0) || (j >= N))
            throw new IndexOutOfBoundsException(
                    "IndexOutOfBoundsException while calling method ["
                            + methodName + "] with j=" + j);

    }

    public void open(int inputI, int inputJ) {
        int i = inputI - 1;
        int j = inputJ - 1;
        checkBounds("open", i, j);
        grid[i][j] = SITE_OPEN;
        int p, q;

        p = xyTo1D(i, j);
        if (j > 0) {
            q = xyTo1D(i, j - 1);
            if (grid[i][j - 1] == SITE_OPEN && !uf.connected(p, q)) {
                uf.union(p, q);
            }
        }

        if (j < N - 1) {
            q = xyTo1D(i, j + 1);
            if (grid[i][j + 1] == SITE_OPEN && !uf.connected(p, q)) {
                uf.union(p, q);
            }
        }

        if (i > 0) {
            q = xyTo1D(i - 1, j);
            if (grid[i - 1][j] == SITE_OPEN && !uf.connected(p, q)) {
                uf.union(p, q);
            }
        }

        if (i < N - 1) {
            q = xyTo1D(i + 1, j);
            if (grid[i + 1][j] == SITE_OPEN && !uf.connected(p, q)) {
                uf.union(p, q);
            }
        }
    }

    private int xyTo1D(int i, int j) {
        return 1 + i * N + j;
    }

    public boolean isOpen(int i, int j) {
        checkBounds("isOpen", i - 1, j - 1);
        return grid[i - 1][j - 1] == SITE_OPEN;
    }

    public boolean isFull(int i, int j) {
        checkBounds("isFull", i - 1, j - 1);
        return isOpen(i, j) && uf.connected(0, xyTo1D(i - 1, j - 1));
    }

    public boolean percolates() {
        boolean result = uf.connected(0, ufSize - 1); 
        if (N == 1) result &= isFull(1, 1);
        return result;
    }
}