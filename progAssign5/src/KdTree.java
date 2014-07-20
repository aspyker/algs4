public class KdTree {
    public KdTree() {
        // construct an empty set of points
    }

    /**
     * 
     * @return is the set empty?
     */
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @return number of points in the set
     */
    public int size() {
        throw new UnsupportedOperationException();
    }

    /**
     * add the point p to the set (if it is not already in the set)
     * 
     * @param p
     */
    public void insert(Point2D p) {
        throw new UnsupportedOperationException();
    }

    /**
     * does the set contain the point p?
     * 
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        throw new UnsupportedOperationException();
    }

    /**
     * draw all of the points to standard draw
     */
    public void draw() {
        throw new UnsupportedOperationException();
    }

    /**
     * all points in the set that are inside the rectangle
     * 
     * @param rect
     * @return
     */
    public Iterable<Point2D> range(RectHV rect) {
        throw new UnsupportedOperationException();
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty
     * 
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        throw new UnsupportedOperationException();
   }
}