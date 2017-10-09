package structure;

import java.util.ArrayList;

public class DistanceBasedSplitter implements NodeSplitter {

    protected char axisOfLongestSeparation;


    public ArrayList<NodeEntry> split(NodeEntry ne, Node node, String written_nodes) {
        return null;
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
            axisOfLongestSeparation = 'X';
        } else {
            farthest.add(yLower);
            farthest.add(yHighest);
            axisOfLongestSeparation = 'Y';
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
