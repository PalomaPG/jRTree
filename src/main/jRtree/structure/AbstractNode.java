package structure;



import utils.Constants;
import utils.Utils;

import java.io.*;
import java.util.ArrayList;

public abstract class AbstractNode extends IDSettings implements INode, Serializable{


    protected ArrayList<NodeEntry> data;
    protected NodeEntry parent;
    protected long nodeID;

    protected boolean isLeaf = false;
    protected int capacity = 0;
    protected int curSize = 0;

    public AbstractNode(){
        this.nodeID = Utils.getRandomId();
        this.curSize = 0;
        this.data = new ArrayList<NodeEntry>(capacity);
        this.isLeaf = true; // By default to make tests pass
        parent = null;
    }



    public long getNodeId() {
        return nodeID;
    }


    public void setNodeID(long nodeID){
        this.nodeID = nodeID;
    }
}
