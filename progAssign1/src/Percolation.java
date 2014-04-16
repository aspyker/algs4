public class Percolation {
    private WeightedQuickUnionUF wquuf;
    //private QuickFindUF wquuf;
    
    private boolean[][] gridOpenSites;
    private int sizeOfGrid;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        gridOpenSites = new boolean[N][N];
        // grid of N*N plus virtual nodes for virtual
        // top (N -1) and virtual bottom (N -2)
        wquuf = new WeightedQuickUnionUF(N*N + 2);
        //wquuf = new QuickFindUF(N*N + 2);
        virtualTopIndex = N*N + 2 - 1;
        virtualBottomIndex = N*N + 2 - 2;
        sizeOfGrid = N;
    }
    
    // i = row, j = column
    private int getWhereInWeightedDS(int i, int j) {
        return (i * sizeOfGrid) + j;
    }

    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        int iminusone = i - 1;
        int jminusone = j - 1;
        
        if (iminusone > sizeOfGrid - 1 && jminusone > sizeOfGrid - 1) {
            throw new IndexOutOfBoundsException();
        }
        
//        System.out.println(String.format("*** opening %d,%d", i, j));        
        gridOpenSites[iminusone][jminusone] = true;
        
        int quLoc = getWhereInWeightedDS(iminusone, jminusone);
        
        if (iminusone > 0) {
            // connect to above
            boolean isAboveOpen = gridOpenSites[iminusone - 1][jminusone];
            if (isAboveOpen) {
                wquuf.union(quLoc, getWhereInWeightedDS(iminusone - 1, jminusone));
            }
        }
        else if (iminusone == 0) {
            wquuf.union(quLoc, virtualTopIndex);
        }
        if (jminusone > 0) {
            // connect to left
            boolean isLeftOpen = gridOpenSites[iminusone][jminusone - 1];
            if (isLeftOpen) {
                wquuf.union(quLoc, getWhereInWeightedDS(iminusone, jminusone - 1));
            }
        }
        if (jminusone < sizeOfGrid - 1) {
            // connect to right
            boolean isRightOpen = gridOpenSites[iminusone][jminusone + 1];
            if (isRightOpen) {
                wquuf.union(quLoc, getWhereInWeightedDS(iminusone, jminusone + 1));
            }
        }
        if (iminusone < sizeOfGrid - 1) {
            // connect to below
            boolean isBelowOpen = gridOpenSites[iminusone + 1][jminusone];
            if (isBelowOpen) {
                wquuf.union(quLoc, getWhereInWeightedDS(iminusone + 1, jminusone));
            }
        }
        else if (iminusone == sizeOfGrid - 1) {
            wquuf.union(quLoc, virtualBottomIndex);
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        // the below isn't really needed as Java would throw this on the first access
        if (i > sizeOfGrid && j > sizeOfGrid) {
            throw new IndexOutOfBoundsException();
        }
        return gridOpenSites[i - 1][j - 1];
    }
    
    public void printGrid() {
        for (int ii = 1; ii <= sizeOfGrid; ii++) {
            String line = "";
            line = line + '[';
            for (int jj = 1; jj <= sizeOfGrid; jj++) {
                if (isFull(ii, jj)) {
                    line = line + '*';
                }
                else if (isOpen(ii, jj)) {
                    line = line + 'O';
                }
                else {
                    line = line + '-';
                }
            }
            line = line + ']';
            System.out.println(line);
        }
        System.out.println("perculates = " + percolates() + "\n");
    }
    
    public static void main(String[] args) {
        Percolation p = new Percolation(5);
        p.printGrid();
        p.open(1, 1);
        p.printGrid();
        p.open(2, 4);
        p.printGrid();
        p.open(3, 4);
        p.printGrid();
        p.open(4, 4);
        p.printGrid();
        p.open(2, 2);
        p.printGrid();
        p.open(2, 3);
        p.printGrid();
        p.open(2, 1);
        p.printGrid();
    }

    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        // the below isn't really needed as Java would throw this on the first access
        if (i > sizeOfGrid && j > sizeOfGrid) {
            throw new IndexOutOfBoundsException();
        }
        int quLoc = getWhereInWeightedDS(i - 1, j - 1);
        boolean isOpen = gridOpenSites[i - 1][j - 1];
        boolean isConnectedToTop = wquuf.connected(quLoc, virtualTopIndex);
        //System.out.println(String.format(
        //"** for i = %d, j = %d - isOpen = %b, isConnecteToTop = %b",
        //i, j, isOpen, isConnectedToTop));
        return isOpen && isConnectedToTop;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquuf.connected(virtualTopIndex, virtualBottomIndex);
    }
}