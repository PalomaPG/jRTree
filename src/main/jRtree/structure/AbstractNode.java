package structure;

import java.util.ArrayList;

public abstract class AbstractNode implements INode {
    protected ArrayList<NodeEntry> data;
    protected  NodeEntry parent;

    protected boolean isLeaf = false;
    protected boolean overflow = false;
    protected int capacity = 0;
    protected int curSize = 0;

    public AbstractNode(){
        this.curSize = 0;
        this.data = new ArrayList<NodeEntry>(capacity);
        this.isLeaf = true; // By default to make tests pass
        parent = null;
    }

}
