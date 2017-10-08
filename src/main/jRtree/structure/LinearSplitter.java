package structure;

import utils.Constants;

import java.util.ArrayList;

public class LinearSplitter extends DistanceBasedSplitter {

    /**
     * Should return two NodeEntries whose child members are references to the new nodes created from splitting
     * the input.
     */
    public ArrayList<NodeEntry> split(NodeEntry ne, Node node){
        // Create set of all node entries
        ArrayList<NodeEntry> allNodeEntries = new ArrayList<NodeEntry>(node.getData());
        node.deleteFile(node.getNodeId());
        allNodeEntries.add(ne);
        // Pick the pair at most distance between them
        ArrayList<NodeEntry> farthest = chooseFarthestMBRs(allNodeEntries);
        // Remove them from the set
        allNodeEntries.removeAll(farthest);
        // Create new nodes and add their first entries
        int M = node.getCapacity();
        Node node1 = new Node(M);
        System.err.println(String.format("node id in linearSplitter, node1 %d", node1.nodeID));
        Node node2 = new Node(M);
        System.err.println(String.format("node id in linearSplitter, node2 %d", node2.nodeID));
        node1.insert(farthest.get(0));
        node2.insert(farthest.get(1));
        // Create containers for those nodes
        NodeEntry ne1 = new NodeEntry(farthest.get(0).getMBR(), node1.getNodeId());
        NodeEntry ne2 = new NodeEntry(farthest.get(1).getMBR(), node2.getNodeId());


        // Control variables
        int remainEntries = allNodeEntries.size();
        int remainSpaceNode1 = M - 1;
        int remainSpaceNode2 = M - 1;
        final int minimumAllocated = (int) Math.floor(M*0.4);
        NodeEntry toBeInserted;
        while (!allNodeEntries.isEmpty()){
            // remainEntries = L and one of the nodes has cur size m-L, insert all remains in that node.
            if (remainEntries + node1.getCurSize() == minimumAllocated){
                for (NodeEntry remainEntry : allNodeEntries){
                    node1.insert(remainEntry);
                }
                ne1.setMbr(calculateMBR(node1.getData()));
                break;
            } else if ((remainEntries + node2.getCurSize() == minimumAllocated)){
                for (NodeEntry remainEntry : allNodeEntries){
                    node2.insert(remainEntry);
                }
                ne2.setMbr(calculateMBR(node2.getData()));
                break;
            }
            // Insert the node entry in the node which requires min enlargement
            int randomIndex = (int)Math.floor(Math.random()*remainEntries);
            toBeInserted = allNodeEntries.remove(randomIndex);
            double minEnlargement1 = ne1.calculateEnlargement(toBeInserted);
            double minEnlargement2 = ne2.calculateEnlargement(toBeInserted);
            //Read from memory children of ne1, ne2
            if (minEnlargement1 < minEnlargement2 && remainSpaceNode1 >= 1){
                node1.insert(toBeInserted);
                remainSpaceNode1--;
                // Should updates ne.mbr
                ne1.setMbr(calculateMBR(node1.getData()));
            } else if (minEnlargement1 > minEnlargement2 && remainSpaceNode2 >= 1){
                node2.insert(toBeInserted);
                remainSpaceNode2--;
                // Should updates ne.mbr
                ne2.setMbr(calculateMBR(node2.getData()));
            } else if (minEnlargement1 == minEnlargement2){
                if (ne1.getMBR().area() < ne2.getMBR().area() && remainSpaceNode1 >= 1){
                    node1.insert(toBeInserted);
                    remainSpaceNode1--;
                    // Should updates ne.mbr
                    ne1.setMbr(calculateMBR(node1.getData()));
                } else {
                    node2.insert(toBeInserted);
                    remainSpaceNode2--;
                    // Should updates ne.mbr
                    ne2.setMbr(calculateMBR(node2.getData()));
                }
            }
            remainEntries--;
        }
        //Write nodes to disk
        node1.writeToDisk();
        System.err.println("Nodo 1 despues de..."+node1.nodeID);
        node2.writeToDisk();
        System.err.println("Nodo 2 despues de..."+node2.nodeID);
        ArrayList<NodeEntry> newEntries = new ArrayList<NodeEntry>(2);
        newEntries.add(ne1);
        newEntries.add(ne2);
        return newEntries;
    }

}
