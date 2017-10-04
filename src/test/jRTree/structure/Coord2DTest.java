package structure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Coord2DTest {

    final double delta = 0.00001;

    @Test
    public void coordCreation(){
        // arrange
        double x = 1.0;
        double y = 0.5;
        double[] pt = {1.0,0.5};
        // act
        Coord2D coord1 = new Coord2D(x,y);
        Coord2D coord2 = new Coord2D(pt);
        // assert
        // Use assertEquals(expected, actual, delta) to compare floating-point numbers
        assertEquals(coord1.getX(), x, delta);
        assertEquals(coord1.getY(), y, delta);
        assertEquals(coord2.getX(), coord1.getX(), delta);
        assertEquals(coord2.getY(), coord1.getY(), delta);
    }

    @Test
    public void locationTest(){
        // arrange
        Coord2D origin = new Coord2D(0,0);
        Coord2D northEast = new Coord2D(1,1);
        Coord2D northWest = new Coord2D(-1,1);
        Coord2D southWest = new Coord2D(-1,-1);
        Coord2D southEast = new Coord2D(1,-1);
        // act
        boolean isAtNE = northEast.isAtNorth(origin) && northEast.isAtEast(origin);
        boolean isAtNW = northWest.isAtNorth(origin) && northWest.isAtWest(origin);
        boolean isAtSW = southWest.isAtSouth(origin) && southWest.isAtWest(origin);
        boolean isAtSE = southEast.isAtSouth(origin) && southEast.isAtEast(origin);
        // assert
        assertTrue(isAtNE);
        assertTrue(isAtNW);
        assertTrue(isAtSW);
        assertTrue(isAtSE);
    }

    @Test
    public void distanceTest(){
        // arrange
        Coord2D pt1 = new Coord2D(0,0);
        Coord2D pt2 = new Coord2D(1,1);
        // act
        double distance1 = pt1.distanceTo(pt2);
        double distance2 = pt2.distanceTo(pt1);
        // assert
        assertEquals(Math.sqrt(2), distance1, delta);
        assertEquals(distance1, distance2, delta);
    }


}
