package structure;

import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


/**
 * Test class for implementations of structure.Node interface.
 */
public class NodeTest {

    /**
     * Test node creation
     */
    @Test
    public void nodeCreationTest(){
        // arrange
        int capacity = 3;
        // act
        Node n = new Node(capacity, null);
        // assert
        assertEquals(capacity, n.getCapacity());
        assertEquals(0, n.getCurSize());
    }


    /**
     * Test adding elements into a structure.Node object without exceeding its maximum capacity.
     */
    @Test
    public void nodeInsertTest(){
        // arrange
        Node n = new Node(3, null);  // structure.Node with capacity of 3 node entries
        NodeEntry ne1 = new NodeEntry();  // Empty constructor
        NodeEntry ne2 = new NodeEntry(null, -1);  // structure.NodeEntry(mbr, nodePtr or objectId)
        NodeEntry ne3 = new NodeEntry(null, -1);
        // act
        n.insert(ne1);
        n.insert(ne2);
        // assert
        assertTrue(n.insert(ne3));
        assertEquals(n.getCapacity(), n.getCurSize());
        assertFalse(n.insert(new NodeEntry(null,-1)));
    }

    /**
     * Test searching an element in the structure.Node. Should return all
     */
    @Test
    public void nodeSearchTest(){
        // arrange
        MBR mbr1 = new MBR(new double[][] {{0,0},{0,0},{0,0},{0,0}});  /* Coordinates have no sense but aren't important
                                                                          for this test */
        MBR mbr2 = new MBR(new double[][] {{1,1},{0,1},{0,0},{1,0}});
        Node n = new Node(1, null);
        n.insert(new NodeEntry(mbr1, -1));  // insert should accept structure.NodeEntry and structure.MBR objects
        // act
        ArrayList<MBR> mbrArray1 = n.search(mbr1, ".");  // Searching is always about data in a structure.NodeEntry
        ArrayList<MBR> mbrArray2 = n.search(mbr2, ".");
        // assert
        assertTrue(mbrArray1.contains(mbr1));
        assertNull(mbrArray2);
    }

    /**
     * Test deleting elements from a structure.Node.
     */
    @Ignore
    @Test
    public void nodeDeleteTest(){
        // arrange
        MBR mbr_1 = new MBR(new double[][] {{0,0},{0,0},{0,0},{0,0}});
        MBR mbr_2 = new MBR(new double[][] {{0,0},{0,0},{0,0},{0,0}});
        NodeEntry ne = new NodeEntry(mbr_1, -1);
        Node n = new Node(3, null);
        // act
        n.insert(ne);
        n.delete(mbr_2);  // Searches for node entry which stores the same data as the genrect
        // assert
//        assertEquals(0, n.getCurSize());
        assertFalse(n.delete(mbr_2));  // Can't delete again
//        assertEquals(0, n.getCurSize());
    }


    @Test
    public void equalsTest(){ /* equal without overriding */
        // arrange
        Node n1 = new Node(1,null);
        Node n2 = new Node(1, null);
        // act
        // assert
        assertEquals(n1, n1);
        assertNotEquals(n1,n2);
    }

}
