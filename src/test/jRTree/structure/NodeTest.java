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
        INode n = new Node(capacity);
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
        INode n = new Node(3);  // structure.Node with capacity of 3 node entries
        NodeEntry ne1 = new NodeEntry();  // Empty constructor
        NodeEntry ne2 = new NodeEntry(null, null);  // structure.NodeEntry(mbr, nodePtr or objectId)
        NodeEntry ne3 = new NodeEntry(null, null);
        // act
        n.insert(ne1);
        n.insert(ne2);
        // assert
        assertTrue(n.insert(ne3));
        assertEquals(n.getCapacity(), n.getCurSize());
        assertFalse(n.insert(new NodeEntry(null,null)));
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
        INode n = new Node(1);
        n.insert(new NodeEntry(mbr1, null));  // insert should accept structure.NodeEntry and structure.MBR objects
        // act
        ArrayList<MBR> mbrArray1 = n.search(mbr1);  // Searching is always about data in a structure.NodeEntry
        ArrayList<MBR> mbrArray2 = n.search(mbr2);
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
        NodeEntry ne = new NodeEntry(mbr_1, null);
        INode n = new Node(3);
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
        Node n1 = new Node(1);
        Node n2 = new Node(1);
        // act
        // assert
        assertEquals(n1, n1);
        assertNotEquals(n1,n2);
    }

}
