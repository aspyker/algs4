public class Fast {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int numPoints = in.readInt();
        Point[] points = new Point[numPoints];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int ii = 0; ii < numPoints; ii++) {
            int x = in.readInt();
            int y = in.readInt();
            points[ii] = new Point(x, y);
            StdDraw.point(x, y);
        }
        
        detectLines(points);
    }
    
    private static void detectLines(Point[] points) {
    }
}
