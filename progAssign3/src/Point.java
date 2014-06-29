/*************************************************************************
 * Name:  Andrew Spyker
 * Email: awspyker@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Comparator<Point>() {
        @Override
        public int compare(Point p1, Point p2) {
            double p1Slope = slopeTo(p1);
            double p2Slope = slopeTo(p2);
            // TODO:  From text "Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method"
            return new Double(p1Slope).compareTo(p2Slope);
        }
    };

    private final int x; // x coordinate
    private final int y; // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        int x0 = this.x;
        int x1 = that.x;
        int y0 = this.y;
        int y1 = that.y;
        
        if (x0 == x1 && y0 == y1) {
            return Double.NEGATIVE_INFINITY;
        }
        
        if (y0 == y1) {
            return 0.0;
        }
        
        if (x0 == x1) {
            return Double.POSITIVE_INFINITY;
        }
        
        return  (double)(y1 - y0) / (double)(x1 - x0);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y != that.y) {
            if (this.y > that.y) {
                return 1;
            }
            else {
                return -1;
            }
        }
        return new Integer(this.x).compareTo(that.x);
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}