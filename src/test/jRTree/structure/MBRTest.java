package test.jRTree.structure;

import main.jRtree.structure.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import structure.Coord2D;
import structure.MBR;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class MBRTest {



    @Before
    public void MBRCreation(){
        // arrange

        double[][] mbr_square = {{1,1},{-1,1},{-1,-1},{-1,1}};  // Simple 2x2 rectangle
        double [] pt1 = {1,1};
        double [] pt2 = {-1,1};
        double [] pt3 = {-1,-1};
        double [] pt4 = {-1,1};
        // act
        MBR mbr_1 = new MBR(mbr_square);  // structure.MBR using and array of points
        MBR mbr_2 = new MBR(pt1, pt2, pt3, pt4);  // structure.MBR(pt1, pt2, pt3, pt4) counterclockwise
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
        //arrange
        Coord2D leftBottom1 = new Coord2D(0,0);
        Coord2D topRight1 = new Coord2D(2,2);
        Coord2D leftBottom2 = new Coord2D(-1,-1);
        Coord2D topRight2 = new Coord2D(1,1);
        MBR mbr_test = new MBR(new Coord2D(3,20), new Coord2D(5, 56));
        MBR mbr_test2 = new MBR(new Coord2D(0,-1), new Coord2D(4,0));

        //act
        MBR mbr1 = new MBR(leftBottom1, topRight1);
        MBR mbr2 = new MBR(leftBottom2, topRight2);
        //assert
        Assert.assertNotEquals(mbr1, mbr2);
        assertEquals(4, mbr1.area(), 0.0000001);
        assertEquals(4, mbr2.area(),0.0000001);
        assertFalse(mbr_test.equals(mbr1));
        assertFalse(mbr_test.equals(mbr2));
        assertEquals(mbr_test2.area(), 4, 0.0000001);
        assertEquals(mbr_test2.area(), mbr1.area(),0.0000001);
        assertFalse(mbr_test2.equals(mbr1));
    }

}
