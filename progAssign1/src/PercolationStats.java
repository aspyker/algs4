public class PercolationStats {
    private double[] percolationThreshHoldPercentage;
    private int t;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        percolationThreshHoldPercentage = new double[T];
        t = T;
    }

    // sample mean of percolation threshold
    // (referred to as u in formulas)
    public double mean() {
        return StdStats.mean(percolationThreshHoldPercentage);
    }

    // sample standard deviation of percolation threshold
    // (referred to as o and o^2 in formulas)
    public double stddev() {
        return StdStats.stddev(percolationThreshHoldPercentage);
    }

    // returns lower bound of the 95% confidence interval
    // formulas say u  - (1.96o) / sqrt(t)
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(t));
    }

    // returns upper bound of the 95% confidence interval
    // formulas say u  - (1.96o) / sqrt(t)
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(t));
    }

    // test client, described below
    public static void main(String[] args) {
        int t = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[0]);
        
        doTest(n, t);
    }
    
    private static void doTest(int n, int t) {
        PercolationStats ps = new PercolationStats(n, t);
        
        Stopwatch watch = new Stopwatch();
        Percolation p = null;
        for (int ii = 0; ii < t; ii++) {
            p = new Percolation(n);
            int openSites = 0;
            // open a new site
            while (!p.percolates()) {
                while (true) {
                    int i = StdRandom.uniform(n) + 1;
                    int j = StdRandom.uniform(n) + 1;
                    if (!(p.isOpen(i, j))) {
                        p.open(i, j);
                        openSites++;
                        break; // opened a new site
                    }
                    // tried to open a site that was already open
                }
            }
            double percentage = (double) openSites/((double) (n * n));
            
            ps.percolationThreshHoldPercentage[ii] = percentage;
        }
        double elapsedTime = watch.elapsedTime();
        System.out.println(String.format("mean = %f", ps.mean()));
        System.out.println(String.format("stddev = %f", ps.stddev()));
        System.out.println(
                String.format("95%% confidence interval = %f, %f",
                ps.confidenceLo(), ps.confidenceHi()));
        System.out.println(
                String.format("running time in seconds = %f", elapsedTime));
    }
}
