import org.junit.Assert;
import org.junit.Test;

public class SolverTest {
    @Test
    public void testUnsolvable() {
        int[][] fromSpec1 = { { 2, 1 }, { 0, 3 } };
        Board b1 = new Board(fromSpec1);
        Solver s = new Solver(b1);
        Assert.assertFalse(s.isSolvable());
    }
}
