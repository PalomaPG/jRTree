package structure;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RTreeTest {

    NodeSplitter nodeSplitter;
    RTree rt;

    @Rule
    public ExpectedException thrown;

    @Before
    public void setUp(){
        int nodeSize = 3;
        this.nodeSplitter = new LinearSplitter();
        this.rt = new RTree(nodeSize, this.nodeSplitter);
        this.thrown = ExpectedException.none();
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

    @Test
    public void insertWithOverflowUsingLinearSplit(){
        // arrange
        MBR mbr1 = new MBR(new Coord2D(0,0), new Coord2D(1,1));
        MBR mbr2 = new MBR(new Coord2D(9,9), new Coord2D(10,10));
        MBR mbr3 = new MBR(new Coord2D(1,1), new Coord2D(1.5,1.5));
        MBR mbr4 = new MBR(new Coord2D(8,9), new Coord2D(9,10));
        // act
        rt.insert(mbr1);
        rt.insert(mbr2);
        rt.insert(mbr3);
        rt.insert(mbr4);  /* Overflow occurs here */
        // assert
        INode root = rt.getRoot();
        assertFalse(root.isLeaf());
        assertEquals(3, root.getCapacity());
        assertEquals(2, root.getCurSize());
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

}
