package structure;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;

public class RTreeTest {

    NodeSplitter nodeSplitter;
    RTree rt, rt2;

    @Rule
    public ExpectedException thrown;

    @Before
    public void setUp(){
        int nodeSize = 3;
        this.nodeSplitter = new LinearSplitter();
        this.rt = new RTree(nodeSize, this.nodeSplitter);
        this.rt2 = new RTree(nodeSize, this.nodeSplitter);
        this.thrown = ExpectedException.none();
    }

    /**
     * Insertion tests
     */
    @Test
    public void insertTest() throws Exception {
        MBR mbr1 = new MBR(new Coord2D(.0, .0), new Coord2D(.5, .5));
        MBR mbr2 = new MBR(new Coord2D(.3, .35), new Coord2D(.4, .8));

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

        MBR R8 = new MBR(new Coord2D(2,15), new Coord2D(6,17));
        MBR R9 = new MBR(new Coord2D(15,4), new Coord2D(23,8));

        NodeEntry NE1 = new NodeEntry(R1,leaf1, in);
        leaf1.setParent(NE1);
        NodeEntry NE2 = new NodeEntry(R2, leaf2, in);
        leaf2.setParent(NE2);
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


        Node leaf = rt.insert(mbr1);
        Assert.assertNotNull(leaf);
        Assert.assertEquals(leaf, rt.getRoot());
        rt2.setRoot((Node)in);
        Node leaf4 = rt2.insert(R8);
        Node leaf5 = rt2.insert(R9);

        Assert.assertEquals(leaf4, leaf1);
        Assert.assertEquals(leaf5, leaf2);
        rt2.adjust(leaf5,R9);
    }

    /*
    @Test
    public void insertNotContainingMBRs(){
        // arrange
        MBR mbr1 = new MBR(new Coord2D(.0, .0), new Coord2D(.5, .5));
        MBR mbr2 = new MBR(new Coord2D(.3, .35), new Coord2D(.4, .8));
        // act
        this.rt.insert(mbr1);
        this.rt.insert(mbr2);
        // assert
        assertEquals(2, this.rt.getRoot().getCurSize());
        assertTrue(this.rt.getRoot().isLeaf());
    }

    @Ignore
    @Test
    public void insertContainingMBRs(){
        // arrange
        MBR mbr1 = new MBR(new Coord2D(.0, .0), new Coord2D(1.0, 1.0));
        MBR mbr2 = new MBR(new Coord2D(.2, .2), new Coord2D(.5, .5));
        //this.thrown.expect(InsertionError.class);
        this.thrown.expect(containsString("New structure.MBR collides with another inside a leaf"));
        // act
        this.rt.insert(mbr1);
        this.rt.insert(mbr2);
        // assert
        // No assert section because execution should not pass last insert.
    }
    */
    /**
     * This test tries to check if a split operation happened.
     * After a split (wherever used) the number of nodes increases.
     */
    /*
    @Test
    public void overflowHandle(){
        // arrange
        MBR mbr1 = new MBR(new Coord2D(.0, .0), new Coord2D(.5, .5));
        MBR mbr2 = new MBR(new Coord2D(.3, .35), new Coord2D(.4, .8));
        MBR mbr3 = new MBR(new Coord2D(-1, -1), new Coord2D(-0.5, -0.5));
        MBR mbr4 = new MBR(new Coord2D(1.5, 1.5), new Coord2D(2, 2));
        // act
        rt.insert(mbr1);
        rt.insert(mbr2);
        rt.insert(mbr3);
        rt.insert(mbr4);
        // assert
        //assertTrue(rt.size()>1);
    }*/


    @After
    public void tearDown(){
        this.nodeSplitter = null;
        this.rt = null;
        this.thrown = null;
    }
}
