package structure;

import java.util.ArrayList;

public interface NodeSplitter {

    ArrayList<NodeEntry> split(NodeEntry ne, Node node);

    ArrayList<NodeEntry> chooseFarthestMBRs(ArrayList<NodeEntry> nodeEntries);

    MBR calculateMBR(ArrayList<NodeEntry> nodeEntries);
}
