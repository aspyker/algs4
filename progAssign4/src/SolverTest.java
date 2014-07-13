import org.junit.Assert;
import org.junit.Test;

public class SolverTest {
    
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
    public void testUnsolvable() {
        int[][] fromSpec1 = { { 2, 1 }, { 0, 3 } };
        Board b1 = new Board(fromSpec1);
        Solver s1 = new Solver(b1);
        Assert.assertFalse(s1.isSolvable());
        
        Board b2 = buildBoardFromInputFile("8puzzle-testing/puzzle3x3-unsolvable.txt");
        Solver s2 = new Solver(b2);
        Assert.assertFalse(s2.isSolvable());
    }
    
    @Test
    public void testSolvable() {
        Board b2 = buildBoardFromInputFile("8puzzle-testing/puzzle04.txt");
        Solver s2 = new Solver(b2);
        Assert.assertTrue(s2.isSolvable());
    }
}
