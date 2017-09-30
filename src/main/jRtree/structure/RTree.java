package structure;

import java.util.ArrayList;

public class RTree {

    int nodeSize;
    NodeSplitter nodeSplitter;
    Node root;


    public RTree(int nodeSize, NodeSplitter nodeSplitter){
        this.nodeSize = nodeSize;
        this.nodeSplitter = nodeSplitter;
        this.root = new Node(nodeSize);
    }

    public void insert(MBR mbr){
        fakeInsert(new NodeEntry(mbr, new NullNode()), this.root);
    }

    public ArrayList<NodeEntry> fakeInsert(NodeEntry ne, INode node){
        if (node.isLeaf()){
            boolean inserted = node.insert(ne);  // O(1)
            if (!inserted){
                ArrayList<NodeEntry> newEntries = nodeSplitter.split(ne, node);  // Size 2
                return newEntries;
            }
            return null;
        }

        ArrayList<NodeEntry> nodeData = node.getData();
        NodeEntry minEnlargement = null;
        double lastArea = Double.MAX_VALUE;
        double min_mbr = Double.MAX_VALUE;

        for (NodeEntry entry : nodeData){  // O(node.curSize)
            double area = entry.calculateEnlargement(ne);
            if (lastArea > area){
                lastArea = area;
                minEnlargement = entry;
            }
            // Agregar caso si hay mas de uno que puede albergar la nueva entrada
        }
        ArrayList<NodeEntry> newEntries = fakeInsert(ne, minEnlargement.getChild());
        if (!(newEntries.equals(null))){
            // Si entra aquí debe actualizarse este nodo con las nuevas entradas que vienen de abajo
        }

        return null;  // Para q no tire warning el IDE
    }

    public Node getRoot() {
        return root;
    }
    public void search(){}

}
