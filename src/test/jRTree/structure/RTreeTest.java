package structure;

import exception.RTreeDiskAccessException;
import exception.RTreeException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class RTreeTest {

    NodeSplitter nodeSplitter;
    RTree rt;

    @Rule
    public ExpectedException thrown;

    @Before
    public void setUp(){
        int nodeSize = 3;
        this.nodeSplitter = new LinearSplitter();
        try {
            this.rt = new RTree(nodeSize, this.nodeSplitter, "output/");
        } catch (RTreeException e) {
            e.printStackTrace();
        } catch (RTreeDiskAccessException e) {
            e.printStackTrace();
        }
        this.thrown = ExpectedException.none();
    }

    /**
     * Insertion tests
     */

    @Test
    public void insertNotContainingMBRs(){
        // arrange
        MBR mbr1 = new MBR(new Coord2D(0, 0), new Coord2D(1, 1));
        MBR mbr2 = new MBR(new Coord2D(0, 0), new Coord2D(1, 1));
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
        MBR mbr3 = new MBR(new Coord2D(1,1), new Coord2D(2,2));
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
        MBR mbr1 = new MBR(new Coord2D(0, 0), new Coord2D(1, 1));
        MBR mbr2 = new MBR(new Coord2D(0, 0), new Coord2D(1, 1));
        //this.thrown.expect(InsertionError.class);
        this.thrown.expect(containsString("New structure.MBR collides with another inside a leaf"));
        // act
        this.rt.insert(mbr1);
        this.rt.insert(mbr2);
        // assert
        // No assert section because execution should not pass last insert.
    }

}
