package structure;



import utils.Utils;

import java.io.*;
import java.util.ArrayList;

public abstract class AbstractNode extends IDSettings implements INode, Serializable{


    protected ArrayList<NodeEntry> data;
    protected long nodeID;

    protected boolean isLeaf = false;
    protected int capacity = 0;
    protected int curSize = 0;

    public AbstractNode(){
        this.nodeID = Utils.getRandomId();
        this.curSize = 0;
        this.data = new ArrayList<NodeEntry>(capacity);
        this.isLeaf = true; // By default to make tests pass
    }

    public long getNodeId() {
        return nodeID;
    }


}
