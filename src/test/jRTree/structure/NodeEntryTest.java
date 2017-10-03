package structure;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NodeEntryTest{

    NodeEntry container;
    NodeEntry ne1;
    NodeEntry ne2;
    final double delta = 0.00001;

    /* Seteo de variables */
    @Before
    public void setUp(){
        /* Coordenadas mbr1 - el area no aumente: (1,1);(2,2) */
        MBR mbr1 = new MBR(new Coord2D(1, 1), new Coord2D(2,2));

        /* Coordenadas mbr2 - area aumenta horizontalmente: (13.5, 10) ; (17.5, 12.5)*/
        MBR mbr2 = new MBR(new Coord2D(13, 10), new Coord2D(17,12));

        /* INode MBR: (0.0, 0.0), (15,15)*/
        MBR contMBR = new MBR(new Coord2D(0, 0), new Coord2D(15,15));

        container = new NodeEntry(contMBR, new NullNode());
        ne1 = new NodeEntry(mbr1,  new NullNode());
        ne2 = new NodeEntry(mbr2,  new NullNode());
    }

    /**
     * Enlargement tests
     */

    @Test
    public void zeroEnlargement(){
        // act
        double enlargement1 = container.calculateEnlargement(ne1);
        double enlargement2 = container.calculateEnlargement(ne2);
        // assert
        assertEquals(0, enlargement1, delta);
        assertNotEquals(0, enlargement2, delta);
    }

    @Test
    public void nonZeroEnlargement(){
        // act
        double enlargement = container.calculateEnlargement(ne2);
        // assert
        assertEquals(30, enlargement, delta);
    }
}