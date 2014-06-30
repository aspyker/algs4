import java.util.Arrays;
import java.util.TreeSet;

public class Fast {
    public static void main(String[] args) throws Exception {
        In in = new In(args[0]);
        int numPoints = in.readInt();
        Point[] points = new Point[numPoints];
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int ii = 0; ii < numPoints; ii++) {
            int x = in.readInt();
            int y = in.readInt();
            points[ii] = new Point(x, y);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.point(x, y);
        }
        Thread.sleep(1000);
        StdDraw.setPenColor(StdDraw.BLACK);
        detectLines(points);
    }
    
    private static void detectLines(Point[] points) throws Exception {
        TreeSet<StartOfLine> startOfLines = new TreeSet<StartOfLine>();
        
        for (int ii = 0; ii< points.length; ii++) {
            Point[] orderedPoints = new Point[points.length];
            orderedPoints[0] = points[ii];
            System.arraycopy(points, 0, orderedPoints, 1, ii);
            System.arraycopy(points, ii+1, orderedPoints, ii + 1, points.length - ii - 1);
            //printListAndSlopes(orderedPoints);
            Arrays.sort(orderedPoints, 1, orderedPoints.length, orderedPoints[0].SLOPE_ORDER);
            printListAndSlopes(orderedPoints);
            
            int startOfCurSlope = 1;
            int endOfCurSlope = 1;
            do {
                endOfCurSlope = findEndOfSameSlope(orderedPoints, startOfCurSlope);
                if (endOfCurSlope - startOfCurSlope >= 2) {
                    int lenOfSpan = endOfCurSlope - startOfCurSlope + 1 + 1; // plus the original
                    Point[] allPoints = new Point[lenOfSpan];
                    allPoints[0] = orderedPoints[0];
                    System.arraycopy(orderedPoints, 1, allPoints, 1, lenOfSpan - 1);
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
                        Thread.sleep(1000);
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
    
    private static void printListAndSlopes(Point[] points) {
        System.out.println("**** " + points[0]);
        for (int ii = 1; ii < points.length; ii++) {
            System.out.println(points[ii] + " with slope of " + points[0].slopeTo(points[ii]));
        }
    }
    
    private static int findEndOfSameSlope(Point[] orderedPoints, int startOfCurSlope) {
        Point basePoint = orderedPoints[0];
        double slope = basePoint.slopeTo(orderedPoints[startOfCurSlope]);
        int endOfCurSlope = startOfCurSlope;
        while (endOfCurSlope + 1 < orderedPoints.length) {
            double curSlope = basePoint.slopeTo(orderedPoints[endOfCurSlope + 1]);
            if (slope == curSlope) {
                endOfCurSlope++;
            }
            else {
                break;
            }
        }
        return endOfCurSlope;
    }
    
    private static class StartOfLine implements Comparable {
        public Point startPoint;
        public double slope;
        
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            long temp;
            temp = Double.doubleToLongBits(slope);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            StartOfLine other = (StartOfLine)obj;
            // the below is a hack since Point doesn't implement equals
            return startPoint.toString().equals(other.startPoint.toString()) && slope == other.slope;
        }

        
        public StartOfLine(Point startPoint, double slope) {
            this.startPoint = startPoint;
            this.slope = slope;
        }

        @Override
        public int compareTo(Object o) {
            StartOfLine other = (StartOfLine)o;
            if (this.equals(other)) {
                return 0;
            }
            return -1;
        }
        
        @Override
        public String toString() {
            return startPoint.toString() + "-" + slope; 
         }
    }
}
