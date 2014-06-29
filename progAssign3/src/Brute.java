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
            // for every point (ii), lets compute the slopes to other points and
            // store in slopes
            double[] slopes = new double[points.length]; // TODO:  This wastes one slot where ii == jj
            for (int jj = 0; jj < points.length; jj++) {
                if (ii == jj) {
                    continue; // don't compare to self
                }
                double slope = points[ii].slopeTo(points[jj]);
                slopes[jj] = slope;
            }
            // at this point for point ii, we have slopes compared to all other
            // points, slopes[ii] is uninitialized and should be ignored 
            
            for (int kk = 0; kk < slopes.length; kk++) {
                if (ii == kk) {
                    continue; // don't compare to self
                }
                double compareToSlope = slopes[kk];
                for (int mm = 0; mm < slopes.length; mm++) {
                    // if mm == ii, the slope doesn't matter (as ii was the
                    // point we were considering)
                    // if mm == kk, the slope doesn't matter (as kk was the
                    // slope we're trying to find other co-linear points)
                    if (mm == ii || mm == kk) {
                        continue;
                    }
                    double slope = slopes[mm];
                    if (slope == compareToSlope) {
                        // we found common slopes between slopes[kk] and slopes[mm]
                        // slopes[kk] = slope from points[ii] and points[kk]
                        // slopes[mm] = slope from points[ii] and points[mm]
                        // therefore points[ii], points[kk], and points[mm] are co-linear
                        System.out.println("line detected - " + points[ii] + ", " + points[kk] + ", " + points[mm]);
                        points[ii].drawTo(points[kk]);
                        points[ii].drawTo(points[mm]);
                    }
                }
            }
        }
    }
}
