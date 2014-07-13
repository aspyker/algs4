import java.util.Comparator;
import java.util.Iterator;

public class Solver {
    private MinPQ<PQNode> pq;
    private PQNode finalMin;
    
    public Solver(Board initial) {
        // find a solution to the initial board (using the A* algorithm)
        PQNodeManhattenComparator comparator = new PQNodeManhattenComparator();
        pq = new MinPQ<PQNode>(comparator);
        
        PQNode nodeInitial = new PQNode(initial, 0, null);
        pq.insert(nodeInitial);
        
        PQNode min = (PQNode)pq.delMin();
        while (!min.board.isGoal()) {
            Iterator<Board> neighbors = min.board.neighbors().iterator();
            while (neighbors.hasNext()) {
                PQNode node = new PQNode(neighbors.next(), min.moves + 1, min);
                pq.insert(node);
            }
            min = (PQNode)pq.delMin();
        }
        finalMin = min;
    }
    
    public boolean isSolvable() {
        // is the initial board solvable?
        // TODO:  Implement
        return true;
    }
    
    public int moves() {
        // min number of moves to solve initial board; -1 if no solution
        return finalMin.moves;
    }
    
    public Iterable<Board> solution() {
        // sequence of boards in a shortest solution; null if no solution
        Stack<Board> history = new Stack<Board>();
        PQNode curNode = finalMin;
        while (curNode != null) {
            history.push(curNode.board);
            curNode = curNode.prevBoardNode;
        }
        return history;
    }
    
    public static void main(String[] args) {
        // solve a slider puzzle (given below)
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
    private class PQNodeManhattenComparator implements Comparator<PQNode> {
        @Override
        public int compare(PQNode node1, PQNode node2) {
            return
                new Integer(node1.board.manhattan() + node1.moves).compareTo(new Integer(node2.board.manhattan() + node2.moves));
        }
    }
    
    private class PQNode {
        Board board;
        int moves;
        PQNode prevBoardNode;
        
        public PQNode(Board board, int moves, PQNode prevBoardNode) {
            this.board = board;
            this.moves = moves;
            this.prevBoardNode = prevBoardNode;
        }
    }
}