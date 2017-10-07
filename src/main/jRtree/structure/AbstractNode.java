package structure;



import utils.Constants;

import java.io.*;
import java.util.ArrayList;

public abstract class AbstractNode extends IDSettings implements INode, Serializable{
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

    public void writeToDisk() {
        try {
            ObjectOutputStream out
                    = new ObjectOutputStream(new FileOutputStream("b" + nodeID + ".node"));
            out.writeObject(this);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /** Read from disk and return the node with the specified id. */
    public static AbstractNode readFromDisk(long id) {
        try {
            ObjectInputStream in
                    = new ObjectInputStream
                    (new FileInputStream("b" + id + ".node"));
            return (AbstractNode)(in.readObject());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

}
