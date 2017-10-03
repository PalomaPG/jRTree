package structure;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MBRTest {

    MBR mbr1, mbr2, mbr3;
    final double delta = 0.00001;

    @Before
    public void MBRCreation(){
        double[][] mbr_square = {{-1,1},{-1,-1},{1,-1},{1,1}};  // Simple 2x2 rectangle in counterclockwise sense
        double [] pt1 = {-1,1};
        double [] pt2 = {-1,-1};
        double [] pt3 = {1,-1};
        double [] pt4 = {1,1};
        mbr1 = new MBR(mbr_square);  // structure.MBR using and array of points
        mbr2 = new MBR(pt1, pt2, pt3, pt4);  // structure.MBR(pt1, pt2, pt3, pt4) counterclockwise
        mbr3 = new MBR(new Coord2D(3,6), new Coord2D(5,20));

    }

    /**
     * Checks whether two MBRs intersect.
     */
    @Test
    public void MBRIntersection(){
        // arrange
        Coord2D leftBottom1 = new Coord2D(0,0);
        Coord2D topRight1 = new Coord2D(1,1);
        Coord2D leftBottom2 = new Coord2D(0.5,0.5);
        Coord2D topRight2 = new Coord2D(2,2);
        MBR mbr1 = new MBR(leftBottom1, topRight1);
        MBR mbr2 = new MBR(leftBottom2, topRight2);
        MBR mbr3 = new MBR(leftBottom1, leftBottom2);
        MBR mbr4 = new MBR(topRight1, topRight2);
        // act
        boolean intersect1 = mbr1.intersect(mbr2);
        boolean intersect2 = mbr2.intersect(mbr1);
        boolean intersect3 = mbr3.intersect(mbr4);
        // assert
        assertTrue(intersect1);
        assertTrue(intersect2);
        assertFalse(intersect3);
    }

    @Test
    public void MBREquality(){
        assertEquals(mbr1, mbr2);
    }

    @Test
    public void areaTest(){
        assertEquals(4, mbr1.area(), delta);
        assertEquals(28, mbr3.area(), delta);
    }

    /**
     * Containment tests. There small group of cases, a rectangle is fully inside another, is inside but share some
     * side total or partially, or are the same.
     */
    @Test
    public void containmentTestSharingBottomSide(){
        // arrange
        MBR contained = new MBR(new Coord2D(0.0,-1.0), new Coord2D(0.5,0.5));
        // act and assert
        actAndAssertFotContainmentTests(contained, mbr1);
    }

    @Test
    public void containmentTestSharingLeftSide(){
        // arrange
        MBR contained = new MBR(new Coord2D(-1.0,0.0), new Coord2D(0.5,0.5));
        // act and assert
        actAndAssertFotContainmentTests(contained, mbr1);
    }

    @Test
    public void containmentTestSharingTopSide(){
        // arrange
        MBR contained = new MBR(new Coord2D(0.0,0.0), new Coord2D(0.5,1));
        // act and assert
        actAndAssertFotContainmentTests(contained, mbr1);
    }

    @Test
    public void containmentTestSharingRightSide(){
        // arrange
        MBR contained = new MBR(new Coord2D(0.0,0.0), new Coord2D(1,0.5));
        // act and assert
        actAndAssertFotContainmentTests(contained, mbr1);
    }

    @Test
    public void containmentTestFullyContained(){
        // arrange
        MBR contained = new MBR(new Coord2D(0.0,0.0), new Coord2D(0.5,0.5));
        // act and assert
        actAndAssertFotContainmentTests(contained, mbr1);
    }

    private static void actAndAssertFotContainmentTests(MBR contained, MBR container){
        boolean isContained = container.contains(contained);
        boolean notIsContained = contained.contains(container);
        assertTrue(isContained);
        assertFalse(notIsContained);
    }

}
