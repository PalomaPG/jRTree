package structure;

import java.util.ArrayList;

public class LinearSplitter implements NodeSplitter{

    /**
     * Should return two NodeEntries whose child members are references to the new nodes created from splitting
     * the input.
     */
    public ArrayList<NodeEntry> split(NodeEntry ne, Node node){
        // Create set of all node entries
        ArrayList<NodeEntry> allNodeEntries = new ArrayList<NodeEntry>(node.getData());
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
            //Read from memory children of ne1, ne2
            //Node ne1_child =Node.readFromDisk(ne1.getChild());
            //Node ne2_child =Node.readFromDisk(ne2.getChild());
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
        node.deleteFile(node.getNodeId());
        return newEntries;
    }


    public ArrayList<NodeEntry> chooseFarthestMBRs(ArrayList<NodeEntry> nodeEntries){
        // First we need to find new MBR for entries in order to normalize i.e. width and height
        MBR tempMBR = calculateMBR(nodeEntries);
        double xLength = tempMBR.width();
        double yLength = tempMBR.height();

        NodeEntry xLower = null;
        NodeEntry xHighest = null;
        NodeEntry yLower = null;
        NodeEntry yHighest = null;
        // Records x distance between bottom x side tempMBR and the rectangle with lowest high side.
        // Should be minimum possible. And the same for other sides
        double xDistanceDown = xLength;
        double xDistanceUp = xLength;
        double yDistanceDown = yLength;
        double yDistanceUp = yLength;
        final double xTop = tempMBR.getTopRight().getX();
        final double xDown = tempMBR.getLeftBottom().getX();
        final double yTop = tempMBR.getTopRight().getY();
        final double yDown = tempMBR.getLeftBottom().getY();
        for (NodeEntry nodeEntry : nodeEntries){
            MBR currentMBR = nodeEntry.getMBR();
            double curXTop = currentMBR.getTopRight().getX();
            double curXDown = currentMBR.getLeftBottom().getX();
            double curYTop = currentMBR.getTopRight().getY();
            double curYDown = currentMBR.getLeftBottom().getY();
            if (curXTop - xDown < xDistanceDown){
                xDistanceDown = curXTop - xDown;
                xLower = nodeEntry;
            }
            if (xTop - curXDown < xDistanceUp){
                xDistanceUp = xTop - curXDown;
                xHighest = nodeEntry;
            }
            if (curYTop - yDown < yDistanceDown){
                yDistanceDown = curYTop - yDown;
                yLower = nodeEntry;
            }
            if (yTop - curYDown < yDistanceUp){
                yDistanceUp = yTop - curYDown;
                yHighest = nodeEntry;
            }
        }
        // Check which dimension have the farthest rectangles
        // Normalized distances
        final double normXDistance = (xLength - (xDistanceUp + xDistanceDown))/xLength;
        final double normYDistance = (yLength - (yDistanceUp + yDistanceDown))/yLength;
        ArrayList<NodeEntry> farthest = new ArrayList<NodeEntry>(2);
        // We need to compare distances and check we have different rectangles
        if(normXDistance > normYDistance && !xLower.getMBR().equals(xHighest.getMBR())){
            farthest.add(xLower);
            farthest.add(xHighest);
        } else {
            farthest.add(yLower);
            farthest.add(yHighest);
        }
        return farthest;
    }

    /**
     * The MBR for a set of node entries is defined by finding the lower and higher
     * for both x and y among MBRs in node entries.
     */
    public MBR calculateMBR(ArrayList<NodeEntry> nodeEntries){
        int lowX = Integer.MAX_VALUE;
        int highX = -1 * Integer.MIN_VALUE;
        int lowY = Integer.MAX_VALUE;
        int highY = -1 * Integer.MIN_VALUE;
        for (NodeEntry ne : nodeEntries){
            MBR mbr = ne.getMBR();
            int mbrLowX = mbr.getLeftBottom().getX();
            int mbrHighX = mbr.getTopRight().getX();
            int mbrLowY = mbr.getLeftBottom().getY();
            int mbrHighY = mbr.getTopRight().getY();
            lowX = lowX > mbrLowX ? mbrLowX : lowX;
            lowY = lowY > mbrLowY ? mbrLowY : lowY;
            highX = highX < mbrHighX ? mbrHighX : highX;
            highY = highY < mbrHighY ? mbrHighY : highY;
        }
        return new MBR(new Coord2D(lowX, lowY), new Coord2D(highX, highY));
    }
}


/* To have in mind for method dimmesionalDistance

 -------
|       |
|       |   -----
|       |   |   |
|       |   -----
|       |
 -------

*/