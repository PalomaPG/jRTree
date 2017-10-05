package structure;

import java.util.ArrayList;

public class LinearSplitter extends DistanceBasedSplitter {

    /**
     * Should return two NodeEntries whose child members are references to the new nodes created from splitting
     * the input.
     */
    public ArrayList<NodeEntry> split(NodeEntry ne, INode node){
        // Create set of all node entries
        ArrayList<NodeEntry> allNodeEntries = new ArrayList<NodeEntry>(node.getData());
        allNodeEntries.add(ne);
        // Pick the pair at most distance between them
        ArrayList<NodeEntry> farthest = chooseFarthestMBRs(allNodeEntries);
        // Remove them from the set
        allNodeEntries.removeAll(farthest);
        // Create new nodes and add their first entries
        int M = node.getCapacity();
        INode node1 = new Node(M);
        INode node2 = new Node(M);
        node1.insert(farthest.get(0));
        node2.insert(farthest.get(1));
        // Create containers for those nodes
        NodeEntry ne1 = new NodeEntry(farthest.get(0).getMBR(), node1);
        NodeEntry ne2 = new NodeEntry(farthest.get(1).getMBR(), node2);
        // Control variables
        int remainEntries = allNodeEntries.size();
        int remainSpaceNode1 = M - 1;
        int remainSpaceNode2 = M - 1;
        final int minimumAllocated = (int) Math.floor(M*0.4);
        for (NodeEntry toBeInserted : allNodeEntries){
            // remainEntries = L and one of the nodes has cur size m-L, insert all remains in that node.
            if (remainEntries + node1.getCurSize() == minimumAllocated){
                int currentIndex = allNodeEntries.indexOf(toBeInserted);
                for (int i = currentIndex; i<allNodeEntries.size(); i++){
                    node1.insert(allNodeEntries.get(i));
                }
                ne1.setMbr(calculateMBR(node1.getData()));
                break;
            } else if ((remainEntries + node2.getCurSize() == minimumAllocated)){
                int currentIndex = allNodeEntries.indexOf(toBeInserted);
                for (int i = currentIndex; i<allNodeEntries.size(); i++){
                    node2.insert(allNodeEntries.get(i));
                }
                ne2.setMbr(calculateMBR(node2.getData()));
                break;
            }
            // Insert allNodeEntries.get(current) in the node which requires min enlargement
            double minEnlargement1 = ne1.calculateEnlargement(toBeInserted);
            double minEnlargement2 = ne2.calculateEnlargement(toBeInserted);
            if (minEnlargement1 < minEnlargement2 && remainSpaceNode1 >= 1){
                ne1.getChild().insert(toBeInserted);
                remainSpaceNode1--;
                // Should updates ne.mbr
                ne1.setMbr(calculateMBR(node1.getData()));
            } else if (minEnlargement1 > minEnlargement2 && remainSpaceNode2 >= 1){
                ne2.getChild().insert(toBeInserted);
                remainSpaceNode2--;
                // Should updates ne.mbr
                ne2.setMbr(calculateMBR(node2.getData()));
            } else if (minEnlargement1 == minEnlargement2){
                if (ne1.getMBR().area() < ne2.getMBR().area() && remainSpaceNode1 >= 1){
                    ne1.getChild().insert(toBeInserted);
                    remainSpaceNode1--;
                    // Should updates ne.mbr
                    ne1.setMbr(calculateMBR(node1.getData()));
                } else {
                    ne2.getChild().insert(toBeInserted);
                    remainSpaceNode2--;
                    // Should updates ne.mbr
                    ne2.setMbr(calculateMBR(node2.getData()));
                }
            }
            remainEntries--;
        }
        ArrayList<NodeEntry> newEntries = new ArrayList<NodeEntry>(2);
        newEntries.add(ne1);
        newEntries.add(ne2);
        return newEntries;
    }
}
