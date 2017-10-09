package structure;

import java.util.ArrayList;
import java.util.Collections;

public class GreeneSplit extends DistanceBasedSplitter {

    public ArrayList<NodeEntry> split(NodeEntry ne, Node node) {
        ArrayList<NodeEntry> allNodeEntries = new ArrayList<NodeEntry>(node.getData());
        node.deleteFile(node.getNodeId());
        allNodeEntries.add(ne);
        chooseFarthestMBRs(allNodeEntries); // This set the variable axisOfLongestSeparation
        orderByAxis(allNodeEntries);
        // Nodes to allocate entries
        int M = allNodeEntries.size() - 1;
        Node node1 = new Node(M,null);
        Node node2 = new Node(M, null);
        int mid = (M+1)/2;
        int i,j;
        for (i=0; i<mid; i++){
            node1.insert(allNodeEntries.get(i));
        }
        for (j=mid; j<2*mid; j++){
            node2.insert(allNodeEntries.get(j));
        }
        // Create Node Entries
        NodeEntry left = new NodeEntry(this.calculateMBR(node1.getData()), node1.getNodeId());
        node1.setParent(left);
        NodeEntry right = new NodeEntry(this.calculateMBR(node2.getData()), node2.getNodeId());
        node2.setParent(right);
        if ((M+1)%2 == 1){ // M+1 is odd
            NodeEntry lastEntry = allNodeEntries.get(M);
            double growth1 = left.calculateEnlargement(lastEntry);
            double growth2 = right.calculateEnlargement(lastEntry);
            if (growth1 < growth2){
                node1.insert(lastEntry);
            } else if (growth1 > growth2){
                node2.insert(lastEntry);
            } else {  // Get the smaller
                if (left.getMBR().area() < right.getMBR().area()){
                    node1.insert(lastEntry);
                } else {
                    node2.insert(lastEntry);
                }
            }
        }
        ArrayList<NodeEntry> newEntries = new ArrayList<NodeEntry>(2);
        node1.writeToDisk();
        node2.writeToDisk();
        newEntries.add(left);
        newEntries.add(right);
        return newEntries;
    }

    private void orderByAxis(ArrayList<NodeEntry> entries){
        if (axisOfLongestSeparation == 'X'){
            Collections.sort(entries, NodeEntry.xAxisComparator());
        } else if (axisOfLongestSeparation == 'Y'){
            Collections.sort(entries, NodeEntry.yAxisComparator());
        }
    }

}
