public class Percolation {
    private static final byte SITE_BLOCKED = 0;
    private static final byte SITE_OPEN = 1;
    private static final byte SITE_CONNECTED_TO_TOP = 2;
    private static final byte SITE_CONNECTED_TO_BOTTOM = 4;

    private byte[][] grid;

    private int N;

    private WeightedQuickUnionUF uf;
    private int ufSize;
    
    private boolean percolates;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        
        this.N = N;
        grid = new byte[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                grid[i][j] = SITE_BLOCKED;

        ufSize = N * N;
        uf = new WeightedQuickUnionUF(ufSize);

        for (int i = 0; i < N; i++) {
            grid_connectSiteToTop(0, i); // connect all bottom cells to the bottom
            grid_connectRootToBottom(N-1, i); // connect all bottom cells to the bottom
        }
        
        grid_connectSiteToTop(0, 0); // need to connect only 1st cell in the grid to top, because the whole first row is connected
        
        percolates = false;
    }
    
    private Tuple<Integer, Integer> grid_getRoot(int i, int j) {
        int root = uf.find(xyTo1D(i, j));
        
        int rootI, rootJ;
        rootI = root / N;
        rootJ = root % N;
        return new Tuple<Integer, Integer>(rootI, rootJ);
    }
    
    private void grid_connectRootToTop(int rootI, int rootJ) {
        grid[rootI][rootJ] |= SITE_CONNECTED_TO_TOP;
    }
    
    private void grid_connectSiteToTop(int i, int j) {
        Tuple<Integer, Integer> root = grid_getRoot(i, j);
        grid_connectRootToTop(root.x, root.y);
    }
    
    private void grid_connectRootToBottom(int rootI, int rootJ) {
        grid[rootI][rootJ] |= SITE_CONNECTED_TO_BOTTOM;
    }
    
    private void grid_connectSiteToBottom(int i, int j) {
        Tuple<Integer, Integer> root = grid_getRoot(i, j);
        grid_connectRootToBottom(root.x, root.y);
    }
    
    private void grid_openSite(int i, int j) {
        grid[i][j] |= SITE_OPEN;
    }
    
    private boolean grid_isRootConnectedToTop(int rootI, int rootJ) {
        return (grid[rootI][rootJ] & SITE_CONNECTED_TO_TOP) == SITE_CONNECTED_TO_TOP;
    }
    
    private boolean grid_isSiteConnectedToTop(int i, int j) {
        Tuple<Integer, Integer> root = grid_getRoot(i, j);
        return grid_isRootConnectedToTop(root.x, root.y);
    }
    
    private boolean grid_isRootConnectedToBottom(int rootI, int rootJ) {
        return (grid[rootI][rootJ] & SITE_CONNECTED_TO_BOTTOM) == SITE_CONNECTED_TO_BOTTOM;
    }
    
    private boolean grid_isSiteConnectedToBottom(int i, int j) {
        Tuple<Integer, Integer> root = grid_getRoot(i, j);
        return grid_isRootConnectedToBottom(root.x, root.y);
    }    
    
    private boolean grid_isSiteOpen(int i, int j) {
        return (grid[i][j] & SITE_OPEN) == SITE_OPEN;
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
        if (grid_isSiteOpen(i, j)) return; //do nothing if site is already open
        
        grid_openSite(i, j);
        int p, q;
        boolean connect2top, connect2bottom;
        
        p = xyTo1D(i, j);
        Tuple<Integer, Integer> rootP, rootQ;
        rootP = grid_getRoot(i, j);
        
        connect2top = connect2bottom = false;
        if (j > 0) {
            if (grid_isSiteOpen(i, j-1)) {
                q = xyTo1D(i, j - 1);
                rootQ = grid_getRoot(i, j-1);
                if (grid_isRootConnectedToTop(rootQ.x, rootQ.y))
                    connect2top = true;
                if (grid_isRootConnectedToBottom(rootQ.x, rootQ.y))
                    connect2bottom = true;
                uf.union(p, q);
            }
        }

        if (j < N - 1) {
            if (grid_isSiteOpen(i, j+1)) {
                q = xyTo1D(i, j + 1);
                rootQ = grid_getRoot(i, j+1);
                if (grid_isRootConnectedToTop(rootQ.x, rootQ.y))
                    connect2top = true;
                if (grid_isRootConnectedToBottom(rootQ.x, rootQ.y))
                    connect2bottom = true;
                uf.union(p, q);
            }
        }

        if (i > 0) {
            if (grid_isSiteOpen(i-1, j)) {
                q = xyTo1D(i-1, j);
                rootQ = grid_getRoot(i-1, j);                
                if (grid_isRootConnectedToTop(rootQ.x, rootQ.y))
                    connect2top = true;
                if (grid_isRootConnectedToBottom(rootQ.x, rootQ.y))
                    connect2bottom = true;
                uf.union(p, q);
            }
        }

        if (i < N - 1) {
            if (grid_isSiteOpen(i+1, j)) {
                q = xyTo1D(i + 1, j);
                rootQ = grid_getRoot(i+1, j);
                if (grid_isRootConnectedToTop(rootQ.x, rootQ.y))
                    connect2top = true;
                if (grid_isRootConnectedToBottom(rootQ.x, rootQ.y))
                    connect2bottom = true;
                uf.union(p, q);
            }
        }
        
        rootQ = grid_getRoot(i, j);
        if (connect2top || grid_isRootConnectedToTop(rootP.x, rootP.y))
            grid_connectRootToTop(rootQ.x, rootQ.y);
        if (connect2bottom || grid_isRootConnectedToBottom(rootP.x, rootP.y))
            grid_connectRootToBottom(rootQ.x, rootQ.y);
        
        if (grid_isRootConnectedToTop(rootQ.x, rootQ.y) && grid_isRootConnectedToBottom(rootQ.x, rootQ.y))
            percolates = true;
        
    }

    private int xyTo1D(int i, int j) {
        return i * N + j;
    }
    
    public boolean isOpen(int i, int j) {
        checkBounds("isOpen", i - 1, j - 1);
        return grid_isSiteOpen(i - 1, j - 1);
    }

    public boolean isFull(int i, int j) {
        checkBounds("isFull", i - 1, j - 1);
        return isOpen(i, j) && grid_isSiteConnectedToTop(i-1, j-1);
    }

    public boolean percolates() {
        return this.percolates;
    }
    
    private class Tuple<X, Y> { 
        public final X x; 
        public final Y y; 
        public Tuple(X x, Y y) { 
          this.x = x; 
          this.y = y; 
        } 
    }
}