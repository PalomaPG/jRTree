package structure;

import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.containsString;

public class RTreeTest {

    NodeSplitter nodeSplitter;
    RTree rt;

    @Rule
    ExpectedException thrown;

    @Before
    public void setUp(){
        int nodeSize = 3;
        this.nodeSplitter = new LinearSplitter();
        this.rt = new RTree(nodeSize, this.nodeSplitter);
        this.thrown = ExpectedException.none();
    }

    @After
    public void tearDown(){
        this.nodeSplitter = null;
        this.rt = null;
        this.thrown = null;
    }

    /**
     * Insertion tests
     */
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
        this.thrown.expect(InsertionError.class);
        this.thrown.expect(containsString("New structure.MBR collides with another inside a leaf"));
        // act
        this.rt.insert(mbr1);
        this.rt.insert(mbr2);
        // assert
        // No assert section because execution should not pass last insert.
    }

    /**
     * This test tries to check if a split operation happened.
     * After a split (wherever used) the number of nodes increases.
     */
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
        assertTrue(rt.size()>1);
    }
}
