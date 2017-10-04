package structure;

import java.util.ArrayList;

public class RTree {

    private int nodeSize;
    private NodeSplitter nodeSplitter;
    private Node root;

    public RTree(int nodeSize, NodeSplitter nodeSplitter){
        this.nodeSize = nodeSize;
        this.nodeSplitter = nodeSplitter;
        this.root = new Node(nodeSize);
    }

    public NodeEntry getMinEnlargement(ArrayList<NodeEntry> neList){
        if(neList.size()==1){
            return neList.remove(0);
        } else {
            /* Verify the size of the MBRs and return the smallest */
            double minAreaMBR = Double.MAX_VALUE;
            ArrayList<Integer> indices = new ArrayList<Integer>();

            for(int i=0;i<neList.size();i++){
                NodeEntry ne = neList.get(i);
                double mbrArea = ne.getMBR().area();
                if(mbrArea<minAreaMBR){
                    minAreaMBR = mbrArea;
                    indices.clear();
                    indices.add(i);
                } else if(mbrArea==minAreaMBR){
                    indices.add(i);
                }
            }

            if(indices.size()==1){
                return neList.get(indices.get(0));
            } else{
                /* If there are more than one with the same area, should choose one randomly and return it */

                int rnd = (int) Math.floor(Math.random()*(indices.size()+1));
                return neList.get(indices.get(rnd));

            }
        }
    }


    public void insert(MBR mbr){
        realInsert(new NodeEntry(mbr, new NullNode()), this.root);
    }

    //public void insert(NodeEntry ne, INode node, ArrayList<NodeEntry>track){
    private ArrayList<NodeEntry> realInsert(NodeEntry ne, INode node){

        if (node.isLeaf()){
            boolean inserted = node.insert(ne);  // O(1)
            if (!inserted){
                ArrayList<NodeEntry> newEntries = nodeSplitter.split(ne, node);  // Size 2
                return newEntries;
            } else {
                return new ArrayList<NodeEntry>(0);
            }
        }

        ArrayList<NodeEntry> nodeData = node.getData();
        double minArea = Double.MAX_VALUE;
        ArrayList<NodeEntry> candidates = new ArrayList<NodeEntry>();
        double area;
        for (NodeEntry entry : nodeData){  // O(node.curSize)
            area = entry.calculateEnlargement(ne);
            if(minArea > area){
                candidates.clear();
                candidates.add(entry);
            }
            else if(minArea==area){
                candidates.add(entry);
            }
            // Agregar caso si hay mas de uno que puede albergar la nueva entrada
        }
        NodeEntry minEnlargement = getMinEnlargement(candidates);
        ArrayList<NodeEntry> newEntries = realInsert(ne, minEnlargement.getChild());
        if (!(newEntries.isEmpty())){
            // Si entra aquí debe actualizarse este nodo con las nuevas entradas que vienen de abajo

        }
        return newEntries;
        //track.add(minEnlargement);
        //insert(ne, minEnlargement.getChild(), track);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root=root;
    }

    public void search(){}

}
