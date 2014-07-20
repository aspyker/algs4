public class KdTree {
    private Node root;
    private int size;

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * 
     * @return is the set empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 
     * @return number of points in the set
     */
    public int size() {
        return size;
    }

    /**
     * add the point p to the set (if it is not already in the set)
     * 
     * @param p
     */
    public void insert(Point2D p) {
        if (root == null) {
            size++;
            RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            root = new Node(p, rect, null, null, false);
            return;
        }
        
        Node cur = root;
        int level = 0;
        while (cur != null) {
            boolean verticalLevel = (level % 2) == 1; // l = 0 false (horiz), l = 1 true, l = 2 false
            int compare = verticalLevel ? compareVert(p, cur.p) : compareHoriz(p, cur.p);
            if (compare == 0) {
                return; // trying to insert the same point
            }
            else if (compare > 0) {
                if (cur.rt != null) {
                    cur = cur.rt;
                    level++;
                    continue;
                }
                double left, right, top, bottom;
                if (verticalLevel) {
                    left = cur.rect.xmin();
                    right = cur.rect.xmax();
                    top = cur.rect.ymax();
                    bottom = cur.p.y();
                }
                else {
                    left = cur.p.x();
                    right = cur.rect.xmax();
                    top = cur.rect.ymax();
                    bottom = cur.rect.ymin();
                }
                RectHV nodeBounds = new RectHV(left, bottom, right, top);
                cur.rt = new Node(p, nodeBounds, null, null, !verticalLevel);
                size++;
                return;
            }
            else if (compare < 0) {
                if (cur.lb != null) {
                    cur = cur.lb;
                    level++;
                    continue;
                }
                double left, right, top, bottom;
                if (verticalLevel) {
                    left = cur.rect.xmin();
                    right = cur.rect.xmax();
                    top = cur.p.y();
                    bottom = cur.rect.ymin(); 
                }
                else {
                    left = cur.rect.xmin();
                    right = cur.p.x();
                    top = cur.rect.ymax();
                    bottom = cur.rect.ymin();
                }
                RectHV nodeBounds = new RectHV(left, bottom, right, top);
                cur.lb = new Node(p, nodeBounds, null, null, !verticalLevel);
                size++;
                return;
            }
        }
    }
    
    private int compareHoriz(Point2D p1, Point2D p2) {
        // TODO:  Avoid boxing for extra overheads
        return new Double(p1.x()).compareTo(new Double(p2.x()));
    }
    
    private int compareVert(Point2D p1, Point2D p2) {
        // TODO:  Avoid boxing for extra overheads
        return new Double(p1.y()).compareTo(new Double(p2.y()));
    }

    /**
     * does the set contain the point p?
     * 
     * @param p
     * @return
     */
    public boolean contains(Point2D p) {
        Node cur = root;
        
        int level = 0;
        
        while (cur != null) {
            boolean verticalLevel = (level % 2) == 1; // l = 0 false (horiz), l = 1 true, l = 2 false
            int compare = verticalLevel ? compareVert(p, cur.p) : compareHoriz(p, cur.p);
            if (compare == 0) {
                return true; // trying to insert the same point
            }
            else if (compare > 0) {
                if (cur.rt != null) {
                    cur = cur.rt;
                    level++;
                    continue;
                }
                return false;
            }
            else if (compare < 0) {
                if (cur.rt != null) {
                    cur = cur.rt;
                    level++;
                    continue;
                }
                return false;
            }
        }
        
        throw new RuntimeException("should never get here");
    }

    /**
     * draw all of the points to standard draw
     */
    public void draw() {
        drawNodeAndSubNodes(root);
    }
    
    private void drawNodeAndSubNodes(Node node) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            StdDraw.point(node.p.x(), node.p.y());
            StdDraw.setPenRadius(0.005);
            if (node.vertLevel) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            }
            else { // horizontal
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            if (node.lb != null) {
                drawNodeAndSubNodes(node.lb);
            }
            if (node.rt != null) {
                drawNodeAndSubNodes(node.rt);
            }
        }
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
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean vertLevel;
        
        public Node(Point2D p, RectHV rect, Node lb, Node rt, boolean vertLevel) {
            this.p = p;
            this.rect = rect;
            this.lb = lb;
            this.rt = rt;
            this.vertLevel = vertLevel;
        }
     }
}