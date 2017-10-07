package jRTree.structure;

import exception.RTreeDiskAccessException;
import exception.RTreeException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import structure.*;

import java.util.ArrayList;

public class DiskAccessTest {


    RTree rtree;
    String filename;
    MBR R1, R2, R3, R4, R5, R6, R7;

    @Before
    public void setUp(){
        R1 = new MBR(new Coord2D(1,9), new Coord2D(10,18));
        R2 = new MBR(new Coord2D(8,1), new Coord2D(20,12));
        R3 = new MBR(new Coord2D(9,13), new Coord2D(10,17));
        R4 = new MBR(new Coord2D(2, 11), new Coord2D(7,15));
        R5 = new MBR(new Coord2D(13,6),new Coord2D(17,11));
        R6 = new MBR(new Coord2D(9,17), new Coord2D(22,26));
        R7 = new MBR(new Coord2D(8, 1), new Coord2D(12, 7));
    }

    @Test
    public void createNode(){
        // arrange
        Node node = new Node(3);
        long id = node.getNodeId();
        NodeEntry nodeEntry = new NodeEntry(new MBR(new Coord2D(0,0), new Coord2D(1,1)), -1);
        node.insert(nodeEntry);
        // act
        node.writeToDisk();
        Node n = Node.readFromDisk(id);
        // assert
        Assert.assertEquals(nodeEntry, n.getData().get(0));
    }

    /*Debe crear el arbol y generar RAF*/
    @Test
    public void createTreeLinear() throws RTreeDiskAccessException, RTreeException {
        rtree = new RTree(2, new LinearSplitter());
        long rootIdx = rtree.getRootPtr();
        rtree.insert(R1);
        Assert.assertEquals(rootIdx, rtree.getRootPtr());
        rtree.insert(R2);
        Assert.assertEquals(rootIdx, rtree.getRootPtr());
        Assert.assertEquals(rtree.getRoot().getCurSize(), 2);
        rtree.insert(R3);
        Assert.assertFalse(rootIdx== rtree.getRootPtr());
        rootIdx = rtree.getRootPtr();
        rtree.insert(R4);
        rtree.insert(R5);
        Assert.assertFalse(rootIdx== rtree.getRootPtr());
        System.err.println("Buscar.....");
        /*buscar*/
        ArrayList<MBR> mbrs = rtree.search(R6);
        for(MBR mbr : mbrs){
            System.err.println(mbr.getLeftBottom().getX()+" "+mbr.getLeftBottom().getY());
        }
        mbrs = rtree.search(R5);
        for(MBR mbr : mbrs){
            System.err.println(mbr.getLeftBottom().getX()+" "+mbr.getLeftBottom().getY());
        }

    }



    @Test
    public void createNode2(){

        /*Creates a node and checks either file was created*/
        Node node = new Node(3);
        long id = node.getNodeId();
        node.writeToDisk();
        Node n = (Node)Node.readFromDisk(node.getNodeId());
        Assert.assertEquals(id, n.getNodeId());
    }
}
