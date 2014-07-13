public class Board {
    private int N;
    private int[][] tiles;
    
    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        N = blocks[0].length;
        tiles = blocks;
    }

    public int dimension() {
        // board dimension N
        return N;
    }
    
    public int hamming() {
        // number of blocks out of place
        int incorrectVals = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = tiles[i][j];
                if (val == 0) { // the "empty" tile
                    continue;
                }
                int correctVal = i*N + j + 1;
                if (val != correctVal) {
                    incorrectVals++;
                }
            }
        }
        return incorrectVals;
    }
    
    public int manhattan() {
        // sum of Manhattan distances between blocks and goal
        int distances = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = tiles[i][j];
                if (val == 0) { // the "empty" tile
                    continue;
                }
                int row = (val - 1) / N;
                distances += Math.abs(i - row);
                int col = (val - 1) % N;
                distances += Math.abs(j - col);
            }
        }
        return distances;
    }
    
    public boolean isGoal() {
        // is this board the goal board?
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i == N - 1) && (j == N - 1)) {
                    return tiles[i][j] == 0;
                }
                if (tiles[i][j] != (i*N + j + 1)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Board twin() {
        // a board obtained by exchanging two adjacent blocks in the same row
        throw new UnsupportedOperationException();
    }
    
    public boolean equals(Object y) {
        // does this board equal y?
        Board other = (Board)y;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public Iterable<Board> neighbors() {
        // all neighboring boards
        Stack<Board> boards = new Stack<Board>();
        
        int zeroI = 0, zeroJ = 0;
        
        // fine the index of the "empty" tile
        outerloop:
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break outerloop;
                }
            }
        }
        
        if (zeroI > 0) { // left open
            int[][] newTiles = getNeighbor(tiles, zeroI, zeroJ, zeroI - 1, zeroJ);
            Board b = new Board(newTiles);
            boards.push(b);
        }
        
        if (zeroI <= N - 2) { // right open
            int[][] newTiles = getNeighbor(tiles, zeroI, zeroJ, zeroI + 1, zeroJ);
            Board b = new Board(newTiles);
            boards.push(b);
        }
        if (zeroJ > 0) { // above open
            int[][] newTiles = getNeighbor(tiles, zeroI, zeroJ, zeroI, zeroJ - 1);
            Board b = new Board(newTiles);
            boards.push(b);
        }
        if (zeroJ <= N - 2) { // below open
            int[][] newTiles = getNeighbor(tiles, zeroI, zeroJ, zeroI, zeroJ + 1);
            Board b = new Board(newTiles);
            boards.push(b);
        }
        
        return boards;
    }
    
    public static int[][] getNeighbor(int[][] orig, int zeroI, int zeroJ, int swapI, int swapJ) {
        int M = orig[0].length;
        int[][] newTiles = new int[M][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                if (i == swapI && j == swapJ) {
                    newTiles[i][j] = 0;
                }
                else if (i == zeroI && j == zeroJ) {
                    newTiles[i][j] = orig[swapI][swapJ];
                }
                else {
                    newTiles[i][j] = orig[i][j];
                }
            }
        }
        return newTiles;
    }
    
    public String toString() {
        // string representation of the board (in the output format specified below)
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
}
