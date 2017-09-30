package structure;

import org.junit.Test;
import structure.Coord2D;

import static org.junit.Assert.assertEquals;

public class Coord2DTest {

    @Test
    public void coordCreation(){
        // arrange
        double x = 1.0;
        double y = 0.5;
        double[] pt = {1.0,0.5};
        double delta = 0.000000001;
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


}
