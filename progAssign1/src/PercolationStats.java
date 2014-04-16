public class PercolationStats {
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
    }

    // sample mean of percolation threshold
    public double mean() {
        return 0.0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0.0;
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return 0.0;
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return 0.0;
    }

    // test client, described below
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("usage:  PercolationStats N T");
            System.exit(1);
        }
        int t = Integer.parseInt(args[1]);
        int n = Integer.parseInt(args[0]);
        
        double runningAverage = 0.0;
        for (int ii = 0; ii < t; ii++) {
            Percolation p = new Percolation(n);
            int openSites = 0;
            // open a new site
            while (!p.percolates()) {
                while (true) {
                    int i = StdRandom.uniform(n);
                    int j = StdRandom.uniform(n);
                    if (!(p.isOpen(i, j))) {
                        p.open(i, j);
                        openSites++;
                        break; // opened a new site
                    }
                    // tried to open a site that was already open
                }
            }
            //p.printGrid();
            double percentage = (double)openSites/((double)(n * n));
           
            runningAverage = (runningAverage == 0.0) ? percentage : (runningAverage + percentage) / 2.0; 
            System.out.println(String.format("openSites = %d, percentage = %f", openSites, percentage));
        }
        System.out.println(String.format("average percent = %f", runningAverage));
    }
}
