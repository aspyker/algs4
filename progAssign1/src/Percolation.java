import java.text.MessageFormat;



public class Percolation {
    private WeightedQuickUnionUF wquuf;
    
    private boolean gridOpenSites[][];
    private int sizeOfGrid;
    private int virtualTopIndex;
    private int virtualBottomIndex;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        gridOpenSites = new boolean[N][N];
        // grid of N*N plus virtual nodes for virtual top (N -1) and virtual bottom (N -2)
        wquuf = new WeightedQuickUnionUF(N*N + 2);
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
        if (i > sizeOfGrid - 1 && j > sizeOfGrid - 1) {
            throw new IndexOutOfBoundsException();
        }
        
//        System.out.println(String.format("*** opening %d,%d", i, j));        
        gridOpenSites[i][j] = true;
        
        int quLoc = getWhereInWeightedDS(i, j);
        
        if (i > 0) {
            // connect to above
            boolean isAboveOpen = gridOpenSites[i - 1][j];
            if (isAboveOpen) {
                wquuf.union(quLoc, getWhereInWeightedDS(i - 1, j));
            }
        }
        else if (i == 0) {
            wquuf.union(quLoc, virtualTopIndex);
        }
        if (j > 0) {
            // connect to left
            boolean isLeftOpen = gridOpenSites[i][j - 1];
            if (isLeftOpen) {
                wquuf.union(quLoc, getWhereInWeightedDS(i, j - 1));
            }
        }
        if (j < sizeOfGrid - 1) {
            // connect to right
            boolean isRightOpen = gridOpenSites[i][j + 1];
            if (isRightOpen) {
                wquuf.union(quLoc, getWhereInWeightedDS(i, j + 1));
            }
        }
        if (i < sizeOfGrid - 1) {
            // connect to below
            boolean isBelowOpen = gridOpenSites[i + 1][j];
            if (isBelowOpen) {
                wquuf.union(quLoc, getWhereInWeightedDS(i + 1, j));
            }
        }
        else if (i == sizeOfGrid - 1) {
            wquuf.union(quLoc, virtualBottomIndex);
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        // the below isn't really needed as Java would throw this on the first access
        if (i > sizeOfGrid - 1 && j > sizeOfGrid - 1) {
            throw new IndexOutOfBoundsException();
        }
        return gridOpenSites[i][j];
    }
    
    public void printGrid() {
        for (int ii = 0; ii < sizeOfGrid; ii++) {
            String line = "";
            line = line + '[';
            for (int jj = 0; jj < sizeOfGrid; jj++) {
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
    
    public static void main(String args[]) {
        Percolation p = new Percolation(5);
        p.open(0, 0);
        p.printGrid();
        p.open(0, 1);
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
        if (i > sizeOfGrid - 1 && j > sizeOfGrid - 1) {
            throw new IndexOutOfBoundsException();
        }
        int quLoc = getWhereInWeightedDS(i, j);
        boolean isOpen = gridOpenSites[i][j];
        boolean isConnectedToTop = wquuf.connected(quLoc, virtualTopIndex);
//        System.out.println(String.format("** for i = %d, j = %d - isOpen = %b, isConnecteToTop = %b", i, j, isOpen, isConnectedToTop));
        return isOpen && isConnectedToTop;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquuf.connected(virtualTopIndex, virtualBottomIndex);
    }
}