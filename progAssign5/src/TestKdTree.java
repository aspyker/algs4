import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestKdTree {
    KdTree kdt;
    
    @Before
    public void setup() {
        kdt = new KdTree();
        kdt.insert(new Point2D(0.5, 0.5));
        kdt.insert(new Point2D(0.25, 0.25));
        kdt.insert(new Point2D(0.76, 0.76));
    }
    
    @Ignore("avoid slow and visual test")
    @Test
    public void testPointSETDraw() throws Exception {
        kdt.draw();
        StdDraw.show();
        Thread.sleep(5000);
    }
    
    @Test
    public void testNearest() {
        Point2D nearest = kdt.nearest(new Point2D(0.4, 0.4));
        assertTrue(nearest.equals(new Point2D(0.5, 0.5)));
    }
    
    @Test
    public void testRange() {
        RectHV rect = new RectHV(0.20, 0.20, 0.6, 0.6);
        for (Point2D point : kdt.range(rect)) {
            assertTrue(
                point.equals(new Point2D(0.5, 0.5)) ||
                point.equals(new Point2D(0.25, 0.25)));
        }
    }
    
    @Test
    public void testContains() {
        assertTrue(kdt.contains(new Point2D(0.5, 0.5)));
        assertFalse(kdt.contains(new Point2D(0.2, 0.2)));
    }
    
    @Test
    public void testSize() {
        assertEquals(kdt.size(), 3);
    }
    
    @Test
    public void testSize2() {
        KdTree kdt2 = new KdTree();
        for (int ii = 0; ii < 100; ii++) {
            double randomX = StdRandom.uniform();
            double randomY = StdRandom.uniform();
            kdt2.insert(new Point2D(randomX, randomY));
        }
        //kdt2.draw();
        //StdDraw.show(1000);
        assertEquals(kdt2.size(), 100);
    }
    
    @Test
    public void testCircle() {
        KdTree kdt2 = new KdTree();
        kdt2.insert(new Point2D(0.206107, 0.095492));
        kdt2.insert(new Point2D(0.975528, 0.654508));
        kdt2.insert(new Point2D(0.024472, 0.345492));
        kdt2.insert(new Point2D(0.793893, 0.095492));
        kdt2.insert(new Point2D(0.793893, 0.904508));
        kdt2.insert(new Point2D(0.975528, 0.345492));
        kdt2.insert(new Point2D(0.206107, 0.904508));
        kdt2.insert(new Point2D(0.500000, 0.000000));
        kdt2.insert(new Point2D(0.024472, 0.654508));
        kdt2.insert(new Point2D(0.500000, 1.000000));
        assertEquals(kdt2.size(), 10);
        //kdt2.draw();
        //StdDraw.show(15000);
    }
    
    @Test
    public void testSquare() {
        KdTree kdt2 = new KdTree();
        kdt2.insert(new Point2D(0.2, 0.1));
        //kdt2.draw();
        //StdDraw.show(1500);
        kdt2.insert(new Point2D(0.8, 0.1));
        //kdt2.draw();
        //StdDraw.show(1500);
        kdt2.insert(new Point2D(0.8, 0.9));
        //kdt2.draw();
        //StdDraw.show(1500);
        kdt2.insert(new Point2D(0.2, 0.9));
        assertEquals(kdt2.size(), 4);
        //kdt2.draw();
        //StdDraw.show(15000);
    }
}
