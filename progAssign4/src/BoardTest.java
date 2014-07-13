import java.util.Iterator;

import org.junit.Test;
import org.junit.Assert;

public class BoardTest {
    
    private Board buildBoardFromInputFile(String inputFileName) {
        In in = new In(inputFileName);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board board = new Board(blocks);
        return board;
    }
    
    @Test
    public void testHamming() {
        Board b;
        b = buildBoardFromInputFile("8puzzle-testing/puzzle01.txt");
        Assert.assertEquals(b.hamming(), 1);
        b = buildBoardFromInputFile("8puzzle-testing/puzzle3x3-unsolvable.txt");
        Assert.assertEquals(b.hamming(), 2);
        int[][] fromSpec = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        b = new Board(fromSpec);
        Assert.assertEquals(b.hamming(), 5);
    }
    
    @Test
    public void testManhatten() {
        Board b;
        b = buildBoardFromInputFile("8puzzle-testing/puzzle01.txt");
        Assert.assertEquals(b.manhattan(), 1);
        b = buildBoardFromInputFile("8puzzle-testing/puzzle3x3-unsolvable.txt");
        Assert.assertEquals(b.manhattan(), 2);
        int[][] fromSpec = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        b = new Board(fromSpec);
        Assert.assertEquals(b.manhattan(), 10);
    }
    
    @Test
    public void testGoal() {
        Board b;
        b = buildBoardFromInputFile("8puzzle-testing/puzzle01.txt");
        Assert.assertFalse(b.isGoal());
    }
    
    @Test
    public void testEquals() {
        int[][] fromSpec1 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board b1 = new Board(fromSpec1);
        int[][] fromSpec2 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board b2 = new Board(fromSpec2);
        Assert.assertTrue(b1.equals(b2));
        int[][] transposed = { { 5, 1, 3 }, { 4, 0, 2 }, { 7, 6, 8 } };
        Board b3 = new Board(transposed);
        Assert.assertFalse(b1.equals(b3));
    }
    
    @Test
    public void testNeighbors() {
        int[][] fromSpec1 = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
        Board b1 = new Board(fromSpec1);
        Iterator<Board> neighbors = b1.neighbors().iterator();
        int num = 0;
        while (neighbors.hasNext()) {
            Board board = neighbors.next();
            Assert.assertEquals(board.dimension(), 3);
            num++;
        }
        Assert.assertEquals(num, 4);
    }
    
    @Test
    public void testTwin() {
        Board b;
        b = buildBoardFromInputFile("8puzzle-testing/puzzle2x2-unsolvable1.txt");
        Board twin1 = b.twin();
        Board twin2 = twin1.twin();
        Assert.assertTrue(b.equals(twin2));
    }
}
