package structure;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class LinearSplitTest extends structure.DistanceBasedTest {

    static NodeSplitter ls;
    NodeEntry ne1, ne2, ne3, ne4;

    @BeforeClass
    public static void setUpSplitter(){
        ls = new LinearSplitter();
    }

    @Before
    public void setUp(){
        this.ls = new LinearSplitter();
        ne1 = new NodeEntry(new MBR(new Coord2D(0,0), new Coord2D(1,1)), -1);
        ne2 = new NodeEntry(new MBR(new Coord2D(9,9), new Coord2D(10,10)), -1);
        ne3 = new NodeEntry(new MBR(new Coord2D(1,1), new Coord2D(2,2)), -1);
        ne4 = new NodeEntry(new MBR(new Coord2D(8,9), new Coord2D(9,10)), -1);
    }

    @Test
    public void farthestTest(){
        // arrange
        ArrayList<NodeEntry> nodeEntries = new ArrayList<NodeEntry>(3);
        nodeEntries.add(ne1);
        nodeEntries.add(ne2);
        nodeEntries.add(ne3);
        // act
        ArrayList<NodeEntry> farthest = this.ls.chooseFarthestMBRs(nodeEntries);
        // assert
        assertEquals(2, farthest.size());
        assertTrue(farthest.contains(ne1));
        assertTrue(farthest.contains(ne2));
        assertFalse(farthest.contains(ne3));
    }

    @Test
    public void splitTest(){
        // arrange
        Node node = new Node(3);
        node.insert(ne1);
        node.insert(ne2);
        node.insert(ne3);
        Node expectedNode1 = new Node(3);
        Node expectedNode2 = new Node(3);
        expectedNode1.insert(ne1);
        expectedNode1.insert(ne3);
        expectedNode2.insert(ne2);
        expectedNode2.insert(ne4);
        // act
        ArrayList<NodeEntry> splittedNodes = ls.split(ne4, node);
        NodeEntry neLeft = splittedNodes.get(0);
        NodeEntry neRight = splittedNodes.get(1);
        System.err.println(String.format("ID left nodeEntry child in split test: %d", neLeft.getChild()));
        System.err.println(String.format("ID right nodeEntry child in split test: %d", neRight.getChild()));
        Node left = Node.readFromDisk(neLeft.getChild());
        Node right = Node.readFromDisk(neRight.getChild());
        System.err.println(String.format("ID node in split test: %d", left.nodeID));
        System.err.println(String.format("ID node in split test: %d", right.nodeID));

    }
}
