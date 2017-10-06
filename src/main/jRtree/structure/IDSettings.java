package structure;

import utils.*;
public abstract class IDSettings {

    protected long nodeID;

    public long getNodeId() {
        createID();
        return nodeID;
    }

    public void createID() {
        if (nodeID == -1) {
            nodeID = Utils.getRandomId();
            if(nodeID < 0)
                nodeID = -1 * nodeID;
        }
    }


    public void setNodeID(long nodeID){
        this.nodeID = nodeID;
    }
}
