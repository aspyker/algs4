public class Percolation {
    private WeightedQuickUnionUF connected;
    private WeightedQuickUnionUF full;
    
    private boolean[][] gridOpenSites;
    private int N;
    private int gridOpenSitesVirtualTopIndex;
    private int gridOpenSitesVirtualBottomIndex;
    private int gridFullSitesVirtualTopIndex;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        gridOpenSites = new boolean[N][N];

        connected = new WeightedQuickUnionUF(N*N + 2);
        gridOpenSitesVirtualTopIndex = N*N + 2 - 1;
        gridOpenSitesVirtualBottomIndex = N*N + 2 - 2;

        full = new WeightedQuickUnionUF(N*N + 1);
        gridFullSitesVirtualTopIndex = N*N + 1 - 1;

        this.N = N;
    }
    
    // i = row, j = column
    private int getWhereInWeightedDS(int i, int j) {
        return (i * N) + j;
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        int iminusone = i - 1;
        int jminusone = j - 1;
        
        if (iminusone > N - 1 && jminusone > N - 1) {
            throw new IndexOutOfBoundsException();
        }
        
        gridOpenSites[iminusone][jminusone] = true;
        
        int quLoc = getWhereInWeightedDS(iminusone, jminusone);
        boolean madeFull = false;
        
        // if in first row
        if (iminusone == 0) {
            connected.union(quLoc, gridOpenSitesVirtualTopIndex);
            full.union(quLoc, gridFullSitesVirtualTopIndex);
            madeFull = true;
        }
        else {
            // connect to above
            int abovei = iminusone - 1;
            int abovej = jminusone;
            int aboveQuLoc = getWhereInWeightedDS(abovei, abovej);
            
            boolean isAboveOpen = gridOpenSites[abovei][abovej];
            if (isAboveOpen) {
                connected.union(quLoc, aboveQuLoc);
                full.union(quLoc, aboveQuLoc);
            }
        }
        
        // if not in first column
        if (jminusone > 0) {
            // connect to left
            int lefti = iminusone;
            int leftj = jminusone - 1;
            int leftQuLoc = getWhereInWeightedDS(lefti, leftj);
            
            boolean isLeftOpen = gridOpenSites[lefti][leftj];
            if (isLeftOpen) {
                connected.union(quLoc, leftQuLoc);
                full.union(quLoc, leftQuLoc);
            }
        }
        
        // if not in last column
        if (jminusone < N - 1) {
            // connect to right
            int righti = iminusone;
            int rightj = jminusone + 1;
            int rightQuLoc = getWhereInWeightedDS(righti, rightj);
            
            boolean isRightOpen = gridOpenSites[righti][rightj];
            if (isRightOpen) {
                connected.union(quLoc, rightQuLoc);
                full.union(quLoc, rightQuLoc);
            }
        }
        
        // if in last row
        if (iminusone == N - 1) {
            connected.union(quLoc, gridOpenSitesVirtualBottomIndex);
            // do not connect full using any virtualBottomIndex
        }
        else {
            // connect to below
            int belowi = iminusone + 1;
            int belowj = jminusone;
            int belowQuLoc = getWhereInWeightedDS(belowi, belowj);
            
            boolean isBelowOpen = gridOpenSites[belowi][belowj];
            if (isBelowOpen) {
                connected.union(quLoc, belowQuLoc);
                full.union(quLoc, belowQuLoc);
            }
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if (i > N || j > N || i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException();
        }
        return gridOpenSites[i - 1][j - 1];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if (i > N || j > N || i <= 0 || j <= 0) {
            throw new IndexOutOfBoundsException();
        }
      int quLoc = getWhereInWeightedDS(i - 1, j - 1);
      boolean isFull = full.connected(quLoc, gridFullSitesVirtualTopIndex);
      return isFull;
    }

    // does the system percolate?
    public boolean percolates() {
        return connected.connected(gridOpenSitesVirtualTopIndex, gridOpenSitesVirtualBottomIndex);
    }
}