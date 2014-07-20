public class Board {
    private int N;
    private int[][] tiles;
    
    public Board(int[][] blocks) {
        // construct a board from an N-by-N array of blocks
        // (where blocks[i][j] = block in row i, column j)
        N = blocks[0].length;
        
        tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = blocks[i][j];
            }
        }
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
        // "To detect such situations, use the fact that boards are divided into two equivalence classes with respect
        // to reachability: (i) those that lead to the goal board and (ii) those that lead to the goal board if we
        // modify the initial board by swapping any pair of adjacent (non-blank) blocks in the same row"
        
        int i = 0, j = 0;
        outerloop:
        for (; i < N; i++) {
            j = 0;
            for (; j < N - 1; j++) {
                int first = tiles[i][j];
                int second = tiles[i][j+1];
                if (first != 0 && second != 0) {
                    break outerloop;
                }
            }
        }
        int[][] twin = swappedArray(tiles, i, j, i, j + 1);
        return new Board(twin);
    }
    
    public boolean equals(Object otherO) {
        // does this board equal y?
        
        if (otherO == this) return true;
        if (otherO == null) return false;
        if (otherO.getClass() != getClass()) return false;
        
        Board other = (Board)otherO;
        
        if (other.N != N) {
            return false;
        }
            
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
            int[][] newTiles = swappedArray(tiles, zeroI, zeroJ, zeroI - 1, zeroJ);
            Board b = new Board(newTiles);
            boards.push(b);
        }
        
        if (zeroI <= N - 2) { // right open
            int[][] newTiles = swappedArray(tiles, zeroI, zeroJ, zeroI + 1, zeroJ);
            Board b = new Board(newTiles);
            boards.push(b);
        }
        if (zeroJ > 0) { // above open
            int[][] newTiles = swappedArray(tiles, zeroI, zeroJ, zeroI, zeroJ - 1);
            Board b = new Board(newTiles);
            boards.push(b);
        }
        if (zeroJ <= N - 2) { // below open
            int[][] newTiles = swappedArray(tiles, zeroI, zeroJ, zeroI, zeroJ + 1);
            Board b = new Board(newTiles);
            boards.push(b);
        }
        
        return boards;
    }
    
    private static int[][] swappedArray(int[][] orig, int firstSwapI, int firstSwapJ, int secondSwapI, int secondSwapJ) {
        int M = orig[0].length;
        int[][] newTiles = new int[M][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < M; j++) {
                if (i == secondSwapI && j == secondSwapJ) {
                    newTiles[i][j] = orig[firstSwapI][firstSwapJ];
                }
                else if (i == firstSwapI && j == firstSwapJ) {
                    newTiles[i][j] = orig[secondSwapI][secondSwapJ];
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
