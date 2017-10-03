package main.jRtree.structure;

import java.util.ArrayList;

public interface NodeSplitter {

    ArrayList<NodeEntry> split(NodeEntry ne, INode node);
}
