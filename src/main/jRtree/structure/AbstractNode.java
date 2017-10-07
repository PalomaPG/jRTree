package structure;



import utils.Constants;

import java.io.*;
import java.util.ArrayList;

public abstract class AbstractNode extends IDSettings implements INode{


    protected ArrayList<NodeEntry> data;
    protected NodeEntry parent;

    protected boolean isLeaf = false;
    protected int capacity = 0;
    protected int curSize = 0;

    public AbstractNode(){
        createID();
        this.curSize = 0;
        this.data = new ArrayList<NodeEntry>(capacity);
        this.isLeaf = true; // By default to make tests pass
        parent = null;
    }



}
