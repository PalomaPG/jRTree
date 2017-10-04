package structure;

import java.util.ArrayList;

/**
 * structure.Node class for RTree. A node can contain a maximum number of node entries M (Capacity).
 * M must be determined by the user (or automatically) to make a structure.Node fits in a memory page.
 */

public class Node extends AbstractNode {



    public Node(int capacity){
        super();
        this.capacity = capacity;
    }

    public Node(int capacity, NodeEntry parent){
        super();
        this.capacity = capacity;
        this.parent = parent;
    }


    public int getCapacity() {
        return this.capacity;
    }

    public int getCurSize() {
        return this.curSize;
    }

    public boolean insert(NodeEntry ne){
        data.add(ne);
        ne.setHost(this);
        curSize++;
        if (curSize <=capacity) {
            return true;
        }
        else {
            overflow = true;
            return false;
        }

    }

    /**
     * If the node is a leaf should return a list of structure.MBR which intersects the genrect.
     * Else should ask to its children when a structure.MBR in a structure.NodeEntry intersects.
     */
    public ArrayList<MBR> search(MBR mbr){
        ArrayList<MBR> matched = new ArrayList<MBR>();
        if (this.isLeaf){
            for (NodeEntry ne : this.data){
                MBR tested = ne.getMBR();
                if (tested.intersect(mbr)){
                    matched.add(tested);
                }
            }
        } else {
            for (NodeEntry ne : this.data){
                MBR tested = ne.getMBR();
                if (tested.intersect(mbr)){
                    matched.addAll(ne.getChild().search(mbr));
                }
            }
        }
        return matched.isEmpty() ? null : matched;
    }

    public boolean delete(MBR mbr){

        return false;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(boolean b){
        this.isLeaf = b;
    }

    public ArrayList<NodeEntry> getData() {
        return data;
    }

    public NodeEntry getParent(){
        return parent;
    }


}
