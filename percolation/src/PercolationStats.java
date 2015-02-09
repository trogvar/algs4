public class PercolationStats {
    private int gridSize;
    private int runsCount;
    private int cellCount;

    private double[] results;

    public PercolationStats(int N, int T) {
        if (N <= 0)
            throw new IllegalArgumentException("N is invalid: " + N);
        if (T <= 0)
            throw new IllegalArgumentException("T is invalid: " + T);

        gridSize = N;
        runsCount = T;
        results = new double[T];

        for (int i = 0; i < T; i++) {
            results[i] = runExperiment();
        }
    }

    /**
     * performs the experiment until grid percolates.
     * 
     * @return fraction of sites opened
     */
    private double runExperiment() {
        Percolation p = new Percolation(gridSize);
        int result = 0;
        cellCount = gridSize * gridSize;
        while (!p.percolates()) {
            int i = 1 + (int) (Math.random() * gridSize);
            int j = 1 + (int) (Math.random() * gridSize);
            if (!p.isOpen(i, j)) {
                p.open(i, j);
                result++;
            }
        }
        return (double) result / cellCount;
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return (double) (mean() - 1.96 * stddev() / Math.sqrt(runsCount));
    }

    public double confidenceHi() {
        return (double) (mean() + 1.96 * stddev() / Math.sqrt(runsCount));
    }

    public static void main(String[] args) {
        int N, T;
        N = StdIn.readInt();
        T = StdIn.readInt();
        PercolationStats ps = new PercolationStats(N, T);
        System.out.println(String.format("mean\t\t= %f", ps.mean()));
        System.out.println(String.format("stddev\t\t= %f", ps.stddev()));
        System.out.println("95% confidence interval\t= " + ps.confidenceLo()
                + ", " + ps.confidenceHi());
    }
}
