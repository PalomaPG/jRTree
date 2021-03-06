package structure;

import java.util.ArrayList;

public interface INode {
    int getCapacity();

    int getCurSize();

    boolean insert(NodeEntry ne);

    ArrayList<MBR> search(MBR mbr, String path);

    boolean delete(MBR mbr);

    boolean isLeaf();

    void setIsLeaf(boolean b);

    ArrayList<NodeEntry> getData();

    void replace(NodeEntry oldEntry, NodeEntry newEntry);

}
