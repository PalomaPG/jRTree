package structure;

import org.junit.Test;
import structure.INode;
import structure.MBR;
import structure.Node;
import structure.NodeEntry;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


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
        MBR mbr_1 = new MBR(new double[][] {{0,0},{0,0},{0,0},{0,0}});
        MBR mbr_2 = new MBR(new double[][] {{1,1},{0,1},{0,0},{1,0}});
        INode n = new Node(1);
        n.insert(new NodeEntry(mbr_1, null));  // insert should accept structure.NodeEntry and structure.MBR objects
        // act
        ArrayList<MBR> mbrArray_1 = n.search(mbr_1);  // Searching is always about data in a structure.NodeEntry
        ArrayList<MBR> mbrArray_2 = n.search(mbr_2);
        // assert
        assertTrue(mbrArray_1.contains(mbr_1));
        assertNull(mbrArray_2);
    }

    /**
     * Test deleting elements from a structure.Node.
     */
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
        assertEquals(0, n.getCurSize());
        assertFalse(n.delete(mbr_2));  // Can't delete again
        assertEquals(0, n.getCurSize());
    }

}
