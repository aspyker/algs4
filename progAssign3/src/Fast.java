import java.util.Arrays;
import java.util.TreeSet;

public class Fast {
    private static boolean debugDrawing = false;
    
    public static void main(String[] args) throws Exception {
        In in = new In(args[0]);
        int numPoints = in.readInt();
        Point[] points = new Point[numPoints];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        if (debugDrawing) {
            StdDraw.setPenColor(StdDraw.GREEN);
            for (int ii = 0; ii< 32768; ii+=1000) {
                StdDraw.line(0, ii, 100, ii);
            }
            for (int ii = 0; ii< 32768; ii+=1000) {
                StdDraw.line(ii, 0, ii, 100);
            }
        }
        
        if (debugDrawing) { StdDraw.setPenRadius(0.01); }
        for (int ii = 0; ii < numPoints; ii++) {
            int x = in.readInt();
            int y = in.readInt();
            points[ii] = new Point(x, y);
            if (debugDrawing) {
                StdDraw.setPenColor(StdDraw.RED);
            }
            StdDraw.point(x, y);
        }
        if (debugDrawing) {
            Thread.sleep(1000);
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BLACK);
        }
        detectLines(points);
    }
    
    private static void detectLines(Point[] points) throws Exception {
        TreeSet<StartOfLine> startOfLines = new TreeSet<StartOfLine>();
        
        if (points.length < 4) {
            // not worth looking for lines of length 4 in less than 4 points
            return;
        }
        
        for (int ii = 0; ii< points.length; ii++) {
            Point[] orderedPoints = new Point[points.length - 1];
            Point compareToPoint = points[ii];
            System.arraycopy(points, 0, orderedPoints, 0, ii);
            System.arraycopy(points, ii + 1, orderedPoints, ii, points.length - ii - 1);
//            printListAndSlopes(compareToPoint, orderedPoints);
            Arrays.sort(orderedPoints, compareToPoint.SLOPE_ORDER);
//            printListAndSlopes(compareToPoint, orderedPoints);
            
            int startOfCurSlope = 0;
            int endOfCurSlope = 0;
            do {
                endOfCurSlope = findEndOfSameSlope(compareToPoint, orderedPoints, startOfCurSlope);
                if (endOfCurSlope - startOfCurSlope >= 2) {
                    int lenOfSpan = endOfCurSlope - startOfCurSlope + 1 + 1; // plus the original
                    Point[] allPoints = new Point[lenOfSpan];
                    allPoints[0] = compareToPoint;
                    System.arraycopy(orderedPoints, startOfCurSlope, allPoints, 1, lenOfSpan - 1);
                    Arrays.sort(allPoints);
                    StartOfLine start = new StartOfLine(allPoints[0], allPoints[0].slopeTo(allPoints[1]));
                    if (!startOfLines.contains(start)) {
                        for (int jj = 0; jj < allPoints.length; jj++) {
                            System.out.print(allPoints[jj]);
                            if (jj + 1 < allPoints.length) {
                                System.out.print(" -> ");
                            }
                        }
                        System.out.println();
                        allPoints[0].drawTo(allPoints[allPoints.length - 1]);
                        if (debugDrawing) { Thread.sleep(1000); }
                        startOfLines.add(start);
                    }
                }
                startOfCurSlope = endOfCurSlope + 1;
            }
            while (startOfCurSlope < orderedPoints.length);
        }
    }
//            
//            
//            
//            for (int jj = 1; jj + 2 < orderedPoints.length; jj++) {
//                Point first = orderedPoints[jj];
//                Point second = orderedPoints[jj + 1];
//                Point third = orderedPoints[jj + 2];
//                
//                if (
//                    orderedPoints[0].slopeTo(first) == orderedPoints[0].slopeTo(second) &&
//                    orderedPoints[0].slopeTo(first) == orderedPoints[0].slopeTo(third)
//                ) {
//                    for (int kk = jj + 1; kk < orderedPoints.length; kk++) {
//                        orderedPoints[0].drawTo(third);
//                        System.out.println(orderedPoints[0] + " -> " + first + " -> " + second + " -> " + third);
//                }
//            }
//            }
    
    private static void printListAndSlopes(Point compareToPoint, Point[] points) {
        System.out.println("**** " + compareToPoint);
        for (int ii = 0; ii < points.length; ii++) {
            System.out.println(points[ii] + " with slope of " + compareToPoint.slopeTo(points[ii]));
        }
    }
    
    private static int findEndOfSameSlope(Point compareToPoint, Point[] orderedPoints, int startOfCurSlope) {
        double slope = compareToPoint.slopeTo(orderedPoints[startOfCurSlope]);
        int endOfCurSlope = startOfCurSlope;
        while (endOfCurSlope + 1 < orderedPoints.length) {
            double curSlope = compareToPoint.slopeTo(orderedPoints[endOfCurSlope + 1]);
            if (slope == curSlope) {
                endOfCurSlope++;
            }
            else {
                break;
            }
        }
        return endOfCurSlope;
    }
    
    private static class StartOfLine implements Comparable<Object> {
        public Point startPoint;
        public double slope;
        
//        @Override
//        public int hashCode() {
//            final int prime = 31;
//            int result = 1;
//            long temp;
//            temp = Double.doubleToLongBits(slope);
//            result = prime * result + (int) (temp ^ (temp >>> 32));
//            result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
//            return result;
//        }
//
//        @Override
//        public boolean equals(Object obj) {
//            StartOfLine other = (StartOfLine)obj;
//            // the below is a hack since Point doesn't implement equals, if toString is changed, we break
//            return startPoint.toString().equals(other.startPoint.toString()) && slope == other.slope;
//        }

        
        public StartOfLine(Point startPoint, double slope) {
            this.startPoint = startPoint;
            this.slope = slope;
        }

        @Override
        public int compareTo(Object o) {
            StartOfLine other = (StartOfLine)o;
            Point otherStartPoint = other.startPoint;
            double otherSlope = other.slope;
            
            int startingPointCompare = startPoint.compareTo(otherStartPoint);
            if (startingPointCompare == 0) {
                return new Double(slope).compareTo(otherSlope);
            }
            return startingPointCompare;
        }
        
        @Override
        public String toString() {
            return startPoint.toString() + "-" + slope; 
         }
    }
}
