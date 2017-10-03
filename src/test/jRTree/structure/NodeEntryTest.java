package test.jRTree.structure;

import org.junit.*;
import org.junit.rules.ExpectedException;

import main.jRtree.structure.*;


import static org.junit.Assert.assertEquals;

public class NodeEntryTest{

    NodeEntry container;
    NodeEntry ne1;
    NodeEntry ne2;

    /*Seteo de variables*/
    @Before
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

        Assert.assertEquals(0, container.calcEnlargement(ne1),0.00001);
        Assert.assertFalse(0.0==container.calcEnlargement(ne2));

    }

    @Test
    public void nonZeroEnlargement(){
        Assert.assertEquals(30, container.calcEnlargement(ne2),0.00001);
        Assert.assertFalse(30>container.calcEnlargement(ne2));
        Assert.assertFalse(30<container.calcEnlargement(ne2));
    }

    @Test
    public void hosting(){
        //arrange
        INode in = new Node(3);
        in.setIsLeaf(false);
        INode leaf1 = new Node(4);
        INode leaf2 = new Node(4);

        MBR R1 = new MBR(new Coord2D(1,9), new Coord2D(10,18));
        MBR R2 = new MBR(new Coord2D(8,1), new Coord2D(20,12));
        MBR R3 = new MBR(new Coord2D(9,13), new Coord2D(10,17));
        MBR R4 = new MBR(new Coord2D(2, 11), new Coord2D(7,15));
        MBR R5 = new MBR(new Coord2D(13,6),new Coord2D(17,11));

        MBR R6 = new MBR(new Coord2D(9,17), new Coord2D(22,26));
        MBR R7 = new MBR(new Coord2D(8.5, 1.3), new Coord2D(12.8, 7.5));

        NodeEntry NE1 = new NodeEntry(R1,leaf1, in);
        NodeEntry NE2 = new NodeEntry(R2, leaf2, in);
        NodeEntry NE3 = new NodeEntry(R3, null, leaf1);
        NodeEntry NE4 = new NodeEntry(R4, null, leaf1);
        NodeEntry NE5 = new NodeEntry(R5, null, leaf2);

        NodeEntry NE6 = new NodeEntry(R6, null);
        NodeEntry NE7 = new NodeEntry(R7, null);

        in.insert(NE1);
        in.insert(NE2);
        in.insert(NE6);


        leaf1.insert(NE3);
        leaf1.insert(NE4);
        leaf2.insert(NE5);
        leaf2.insert(NE7);

        //act
        Assert.assertEquals(NE1.getHost(), in);
        Assert.assertEquals(NE2.getHost(), in);
        Assert.assertEquals(NE3.getHost(), leaf1);
        Assert.assertEquals(NE4.getHost(), leaf1);
        Assert.assertEquals(NE5.getHost(), leaf2);
        Assert.assertFalse(in.isLeaf());
        Assert.assertEquals(NE7.getHost(), leaf2);
        Assert.assertEquals(NE6.getHost(), in);
    }
}