package structure;

import java.util.ArrayList;

public class GreeneSplit extends DistanceBasedSplitter {

    public ArrayList<NodeEntry> split(NodeEntry ne, INode node) {
        // Detect the dimension to work on
        ArrayList<NodeEntry> allNodeEntries = new ArrayList<NodeEntry>(node.getData());
        allNodeEntries.add(ne);
        chooseFarthestMBRs(allNodeEntries);

        return null;
    }

    private void orderByAxis(ArrayList<NodeEntry> entries, int start, int end){
        if (axisOfLongestSeparation == 'X'){

        } else if (axisOfLongestSeparation == 'Y'){

        }

    }

}
