package structure;

import java.util.ArrayList;

public interface NodeSplitter {

    ArrayList<NodeEntry> split(NodeEntry ne, Node node, String written_nodes);

    ArrayList<NodeEntry> chooseFarthestMBRs(ArrayList<NodeEntry> nodeEntries);

    MBR calculateMBR(ArrayList<NodeEntry> nodeEntries);
}
