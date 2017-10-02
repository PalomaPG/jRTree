package structure;

import java.util.ArrayList;

public class NullNode extends AbstractNode {

    public int getCapacity() {
        return 0;
    }

    public int getCurSize() {
        return 0;
    }

    public boolean insert(NodeEntry ne) {
        return false;
    }

    public ArrayList<MBR> search(MBR mbr) {
        return null;
    }

    public boolean delete(MBR mbr) {
        return false;
    }

    public boolean isLeaf() {
        return false;
    }

    public void setIsLeaf(boolean b) {

    }

    public ArrayList<NodeEntry> getData() {
        return null;
    }
}
