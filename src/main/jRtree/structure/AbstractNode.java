package structure;

import java.util.ArrayList;

public abstract class AbstractNode implements INode {
    protected ArrayList<NodeEntry> data;

    protected boolean isLeaf = false;

    protected int capacity = 0;
    protected int curSize = 0;
}
