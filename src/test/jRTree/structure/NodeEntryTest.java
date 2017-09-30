package structure;

import org.junit.*;
import org.junit.rules.ExpectedException;

import structure.NodeEntry;

import static org.junit.Assert.assertEquals;

public class NodeEntryTest{

    NodeEntry container;
    NodeEntry ne1;
    NodeEntry ne2;

    /*Seteo de variables*/
    @before
    public void setUp(){

        /*Coordenadas mbr1 - el area no aumente: (1,1);(2,2)*/
        MBR mbr1 = new MBR(new Coord2D(1, 1), new Coord2D(2,2));

        /*Coordenadas mbr2 - area aumenta horizontalmente: (13.5, 10) ; (17.5, 12.5)*/
        MBR mbr2 = new MBR(new Coord2D(13, 10), new Coord2D(17,12));

        /*INode MBR: (0.0, 0.0), (15,15)*/
        MBR cont_mbr = new MBR(new Coord2D(0, 0), new Coord2D(15,15));

        container = new NodeEntry(cont_mbr, new NullNode());
        ne1 = new NodeEntry(mbr1,  new NullNode());
        ne2 = new NodeEntry(mbr2,  new NullNode());
    }


    @Test
    public void zeroEnlargement(){

        assertEquals(0, container.calcEnlargement(ne1));
        assertFalse(0.0==container.calcEnlargement(ne2));

    }

    @Test void nonZeroEnlargement(){
        assertEquals(30, container.calcEnlargement(ne2));
        asserFalse(30>container.calcEnlargement(ne2));
        asserFalse(30<container.calcEnlargement(ne2));
    }
}