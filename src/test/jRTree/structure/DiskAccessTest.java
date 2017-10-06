package jRTree.structure;

import exception.RTreeDiskAccessException;
import exception.RTreeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import structure.LinearSplitter;
import structure.RTree;

public class DiskAccessTest {


    RTree rtree;
    String filename;

    /*Debe crear el arbol y generar RAF*/
    @Test
    public void createTree() throws RTreeDiskAccessException, RTreeException {
        rtree = new RTree(3, new LinearSplitter());

    }


    @Test
    public void idsTest(){
        /*Tanto NodeEntries como Nodes tienen su propio id, y deben ser diferentes*/

    }
    @Test
    public void writeNodes(){

    }
}
