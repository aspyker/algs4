import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestPointSet {
    PointSET ps;
    
    @Before
    public void setup() {
        ps = new PointSET();
        ps.insert(new Point2D(0.5, 0.5));
        ps.insert(new Point2D(0.25, 0.25));
        ps.insert(new Point2D(0.76, 0.76));
    }
    
    @Test
    public void testPointSETDraw() throws Exception {
        ps.draw();
        StdDraw.show();
        Thread.sleep(5000);
    }
    
    @Test
    public void testPointSETNearest() {
        Point2D nearest = ps.nearest(new Point2D(0.4, 0.4));
        assertTrue(nearest.equals(new Point2D(0.5, 0.5)));
    }
    
    @Test
    public void testPointSETRange() {
        RectHV rect = new RectHV(0.20, 0.20, 0.6, 0.6);
        for (Point2D point : ps.range(rect)) {
            assertTrue(
                point.equals(new Point2D(0.5, 0.5)) ||
                point.equals(new Point2D(0.25, 0.25)));
        }
    }
}
