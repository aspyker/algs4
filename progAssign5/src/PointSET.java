import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;

    /**
     * construct an empty set of points
     */
    public PointSET() {
        set = new TreeSet<Point2D>();
    }
    
    /**
     * 
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * 
     * @return number of points in the set
     */
    public int size() {
        return set.size();
    }
    
    /**
     * add the point p to the set (if it is not already in the set)
     * @param p
     */
    public void insert(Point2D p) {
        set.add(p);
    }
    
    /**
     * does the set contain the point p?
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        return set.contains(p);
    }
    
    /**
     * draw all of the points to standard draw
     */
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for (Point2D point : set) {
            StdDraw.point(point.x(), point.y());
        }
    }
    
    /**
     * all points in the set that are inside the rectangle
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        for (Point2D point : set) {
            if  (rect.contains(point)) {
                list.add(point);
            }
        }
        return list;
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        double nearestDistance = Double.MAX_VALUE;
        Point2D nearestPoint = null;
        
        for (Point2D point : set) {
            double newDistance = p.distanceTo(point);
            if (newDistance < nearestDistance) {
                nearestPoint = point;
                nearestDistance = newDistance;
            }
        }
        return nearestPoint;
    }
}
