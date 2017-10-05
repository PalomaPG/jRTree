package structure;

import java.util.ArrayList;

public class LinearSplitter implements NodeSplitter{

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
        double lowX = Double.MAX_VALUE;
        double highX = -1 * Double.MIN_VALUE;
        double lowY = Double.MAX_VALUE;
        double highY = -1 * Double.MIN_VALUE;
        for (NodeEntry ne : nodeEntries){
            MBR mbr = ne.getMBR();
            double mbrLowX = mbr.getLeftBottom().getX();
            double mbrHighX = mbr.getTopRight().getX();
            double mbrLowY = mbr.getLeftBottom().getY();
            double mbrHighY = mbr.getTopRight().getY();
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