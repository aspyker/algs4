import java.util.Arrays;

public class Brute {

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
        for (int ii = 0; ii < points.length; ii++) {
            for (int jj = ii + 1; jj < points.length; jj++) {
                for (int kk = jj + 1; kk < points.length; kk++) {
                    for (int ll = kk + 1; ll < points.length; ll++) {
                        // System.out.println(points[ii] + ", " + points[jj] + ", " + points[kk] + ", " + points[ll]);
                        if (
                            points[ii].slopeTo(points[jj]) == points[ii].slopeTo(points[kk]) &&
                            points[ii].slopeTo(points[jj]) == points[ii].slopeTo(points[ll])) {
                            Point[] fourPoints = { points[ii], points[jj], points[kk], points[ll] };
                            Arrays.sort(fourPoints);
                            fourPoints[0].drawTo(fourPoints[3]);
                            System.out.println(fourPoints[0] + " -> " + fourPoints[1] + " -> " + fourPoints[2] + " -> " + fourPoints[3]);
                        }
                    }
                }
            }
        }
    }
}
