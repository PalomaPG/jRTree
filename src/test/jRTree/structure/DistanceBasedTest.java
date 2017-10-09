package structure;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Ignore
public class DistanceBasedTest {

    public static NodeSplitter ls;
    public NodeEntry ne1, ne2, ne3, ne4;

    @Before
    public void setUp(){
        ne1 = new NodeEntry(new MBR(new Coord2D(0,0), new Coord2D(1,1)),-1);
        ne2 = new NodeEntry(new MBR(new Coord2D(9,9), new Coord2D(10,10)),-1);
        ne3 = new NodeEntry(new MBR(new Coord2D(1,1), new Coord2D(1,1)),-1);
        ne4 = new NodeEntry(new MBR(new Coord2D(8,9), new Coord2D(9,10)),-1);
    }

    @Ignore
    @Test
    public void splitTest(){
        // arrange
        Node node = new Node(3, null);
        node.insert(ne1);
        node.insert(ne2);
        node.insert(ne3);
        Node expectedNode1 = new Node(3, null);
        Node expectedNode2 = new Node(3, null);
        expectedNode1.insert(ne1);
        expectedNode1.insert(ne3);
        expectedNode2.insert(ne2);
        expectedNode2.insert(ne4);
        // act
        ArrayList<NodeEntry> splittedNodes = ls.split(ne4, node, "");
        NodeEntry neLeft = splittedNodes.get(0);
        NodeEntry neRight = splittedNodes.get(1);
        Node left = Node.readFromDisk(neLeft.getChild(), "");
        Node right = Node.readFromDisk(neRight.getChild(), "");

        assertEquals(new MBR(ne1.getMBR().getLeftBottom(), ne3.getMBR().getTopRight()), neLeft.getMBR());
        assertEquals(new MBR(ne4.getMBR().getLeftBottom(), ne2.getMBR().getTopRight()), neRight.getMBR());
    }

    @Ignore
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
}
