import java.util.ArrayList;

public class KdTree {
    private Node root;
    private int size;
    //public int nodesVis = 0;

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
            if (p.equals(cur.p)) {
                return; // trying to insert the same point;
            }
            int compare = verticalLevel ? compareVert(p, cur.p) : compareHoriz(p, cur.p);
            if (compare >= 0) {
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
            else { // if (compare < 0) {
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
            if (p.equals(cur.p)) {
                return true;
            }
            boolean verticalLevel = (level % 2) == 1; // l = 0 false (horiz), l = 1 true, l = 2 false
            int compare = verticalLevel ? compareVert(p, cur.p) : compareHoriz(p, cur.p);
            if (compare >= 0) {
                if (cur.rt != null) {
                    cur = cur.rt;
                    level++;
                    continue;
                }
                return false;
            }
            else { // if (compare < 0) {
                if (cur.lb != null) {
                    cur = cur.lb;
                    level++;
                    continue;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * draw all of the points to standard draw
     */
    public void draw() {
        drawNodeAndSubNodes(root);
    }
    
    private void drawNodeAndSubNodes(Node node) {
        if (node != null) {
            StdDraw.show(1000);
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
        ArrayList<Point2D> list = new ArrayList<Point2D>();
        if (root == null) {
            return list;
        }
        rangeSearchTree(rect, root, list, 0);
        return list;
    }
    
    private void rangeSearchTree(RectHV rect, Node curNode, ArrayList<Point2D> foundPoints, int level) {
        boolean verticalLevel = (level % 2) == 1; // l = 0 false (horiz), l = 1 true, l = 2 false
        if (rect.contains(curNode.p)) {
            foundPoints.add(curNode.p);
        }
        if (verticalLevel) {
            if (curNode.lb != null && rect.ymin() < curNode.p.y()) {
                rangeSearchTree(rect, curNode.lb, foundPoints, level+1);
            }
            if (curNode.rt != null && rect.ymax() >= curNode.p.y()) {
                rangeSearchTree(rect, curNode.rt, foundPoints, level+1);
            }
        }
        else { // horizontal
            if (curNode.lb != null && rect.xmin() < curNode.p.x()) {
                rangeSearchTree(rect, curNode.lb, foundPoints, level+1);
            }
            if (curNode.rt != null && rect.xmax() >= curNode.p.x()) {
                rangeSearchTree(rect, curNode.rt, foundPoints, level+1);
            }
        }
    }

    /**
     * a nearest neighbor in the set to p; null if set is empty
     * 
     * @param p
     * @return
     */
    public Point2D nearest(Point2D p) {
        if (root == null) {
            return null;
        }
        BestPoint bp = new BestPoint(root.p, p.distanceTo(root.p));
        return nearestSearchTree(p, bp, root, 0).p;
    }
    
    private BestPoint nearestSearchTree(Point2D searchPoint, BestPoint champion, Node curNode, int level) {
        boolean verticalLevel = (level % 2) == 1; // l = 0 false (horiz), l = 1 true, l = 2 false
        
        if (curNode == null) {
            return champion;
        }
        
        //nodesVis++;
        double distance = searchPoint.distanceTo(curNode.p);
        if (distance < champion.distance) {
            champion = new BestPoint(curNode.p, distance);
        }
        
        if (verticalLevel) {
            double yDiff = searchPoint.y() - curNode.p.y();
            if (yDiff >= 0) { // search point is above(or same y) of curPoint
                // go towards above first
                BestPoint rtBest = nearestSearchTree(searchPoint, champion, curNode.rt, level + 1);
                if (rtBest.distance < champion.distance) {
                    champion = rtBest;
                }
                if (champion.distance >= yDiff) {
                    BestPoint lbBest = nearestSearchTree(searchPoint, champion, curNode.lb, level + 1);
                    if (lbBest.distance < champion.distance) {
                        champion = lbBest;
                    }
                }
            }
            else { // search point is below curPoint
                yDiff = -yDiff;
                BestPoint lbBest = nearestSearchTree(searchPoint, champion, curNode.lb, level + 1);
                if (lbBest.distance < champion.distance) {
                    champion = lbBest;
                }
                if (champion.distance >= yDiff) {
                    BestPoint rtBest = nearestSearchTree(searchPoint, champion, curNode.rt, level + 1);
                    if (rtBest.distance < champion.distance) {
                        champion = rtBest;
                    }
                }
            }
        }
        else { // horizontal
            double xDiff = searchPoint.x() - curNode.p.x();
            if (xDiff >= 0) { // search point is to right(or same x) of curPoint
                // go towards right first
                BestPoint rtBest = nearestSearchTree(searchPoint, champion, curNode.rt, level + 1);
                if (rtBest.distance < champion.distance) {
                    champion = rtBest;
                }
                if (champion.distance >= xDiff) {
                    BestPoint lbBest = nearestSearchTree(searchPoint, champion, curNode.lb, level + 1);
                    if (lbBest.distance < champion.distance) {
                        champion = lbBest;
                    }
                }
            }
            else { // search point is to left of curPoint
                // go towards left first
                xDiff = -xDiff;
                BestPoint lbBest = nearestSearchTree(searchPoint, champion, curNode.lb, level + 1);
                if (lbBest.distance < champion.distance) {
                    champion = lbBest;
                }
                if (champion.distance >= xDiff) {
                    BestPoint rtBest = nearestSearchTree(searchPoint, champion, curNode.rt, level + 1);
                    if (rtBest.distance < champion.distance) {
                        champion = rtBest;
                    }
                }
            }
        }
        
        return champion;
        
//        BestPoint lbBest = null;
//        BestPoint rtBest = null;
//        if (curNode.lb != null) {
//            boolean shouldSearchLb = true;
//            if (verticalLevel) {
//                // TODO:
//            }
//            else { // horizontal
//                // TODO:
//            }
//            if (shouldSearchLb) {
//                lbBest = nearestSearchTree(searchPoint, champion, curNode.lb, level + 1);
//                if (lbBest.distance < champion.distance) {
//                    champion = lbBest;
//                }
//            }
//        }
//        
//        if (curNode.rt != null) {
//            boolean shouldSearchRt = true;
//            if (verticalLevel) {
//                // TODO:
//            }
//            else { // horizontal
//                double distanceToAxisAligned = curNode.p.x() - searchPoint.x();
//                shouldSearchRt = champion.distance > distanceToAxisAligned;
//            }
//            
//            if (shouldSearchRt) {
//                rtBest = nearestSearchTree(searchPoint, champion, curNode.rt, level + 1);
//                if (rtBest.distance < champion.distance) {
//                    champion = rtBest;
//                }
//            }
//        }
//                
//        return champion;
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
    
    private static class BestPoint {
        private Point2D p;
        private double distance;

        public BestPoint(Point2D p, double distance) {
            this.p = p;
            this.distance = distance;
        }
    }
}