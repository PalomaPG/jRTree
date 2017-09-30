package structure;

import org.junit.Test;
import structure.Coord2D;
import structure.MBR;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MBRTest {


    MBR mbr_1, mbr_2;
    @Before
    public void MBRCreation(){
        // arrange
        double[][] mbr_square = {{1,1},{-1,1},{-1,-1},{-1,1}};  // Simple 2x2 rectangle
        double [] pt1 = {1,1};
        double [] pt2 = {-1,1};
        double [] pt3 = {-1,-1};
        double [] pt4 = {-1,1};
        // act
        mbr_1 = new MBR(mbr_square);  // structure.MBR using and array of points
        mbr_2 = new MBR(pt1, pt2, pt3, pt4);  // structure.MBR(pt1, pt2, pt3, pt4) counterclockwise
        // assert

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
    public void mbrProperties(){
        assertEquals(mbr_1, mbr_2);
        assertEquals(4, mbr_1.area());
        assertEquals(4, mbr_2.area());
        MBR mbr_test = new MBR(new Coord2D(3,20), (5, 56));
        assertFalse(mbr_test.equals(mbr_1));
        assertFalse(mbr_test.equals(mbr_2));
        MBR mbr_test2 = new MBR(new Coord2D(0,-1), new Coord2D(4,0));
        assertEquals(mbr_test2.area(), 4);
        assertEquals(mbr_test2.area(), mbr_1.area());
        assertFalse(mbr_test2.equals(mbr_1));
    }

}
