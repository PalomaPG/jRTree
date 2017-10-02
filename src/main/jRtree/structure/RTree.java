package structure;

import exception.RTreeInsertException;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


public class RTree {

    private int nodeSize;
    private NodeSplitter nodeSplitter;
    private Node root;


    public RTree(int nodeSize, NodeSplitter nodeSplitter){
        this.nodeSize = nodeSize;
        this.nodeSplitter = nodeSplitter;
        this.root = new Node(nodeSize);
    }

    public NodeEntry getMinEnlargement(ArrayList<NodeEntry> ne_list) throws  Exception{

        if(ne_list.isEmpty()) throw new RTreeInsertException("No hay candidatos para recibir MBR");
        else{
            if(ne_list.size()==1){
                return ne_list.remove(0);
            }
            else{
                /*Verificar el tamanho de los MBR, si existe uno
                *+ pequenho que el resto, se pasa ese*/
                double min_area_mbr = Double.MAX_VALUE;
                ArrayList<Integer> indexes = new ArrayList<Integer>();


                int i;
                for(i=0;i<ne_list.size();i++){
                    NodeEntry ne = ne_list.get(i);
                    double mbr_area = ne.getMBR().area();
                    if(mbr_area<min_area_mbr){
                        min_area_mbr = mbr_area;
                        indexes.clear();
                        indexes.add(i);
                    }
                    else if(mbr_area==min_area_mbr){
                        indexes.add(i);
                    }

                }

                if(indexes.isEmpty()) throw new RTreeInsertException("No se encontro minimo MBR");
                else if(indexes.size()==1){
                    return ne_list.get(indexes.get(0));
                }

                else{

                    /*si mas de un MBR tienen igual area que es minima,
                    se elige uno al azar*/

                    int rnd = ThreadLocalRandom.current().nextInt(0, indexes.size()+1);
                    return ne_list.get(indexes.get(rnd));

                }



            }
        }

    }

    public ArrayList<NodeEntry> insert(MBR mbr) throws Exception{

        ArrayList<NodeEntry> track = new ArrayList<NodeEntry>();
        fakeInsert(new NodeEntry(mbr, new NullNode()), this.root, track);
        return track;
    }

    public void fakeInsert(NodeEntry ne, INode node, ArrayList<NodeEntry>track) throws RTreeInsertException{

        if (node.isLeaf()){
            node.insert(ne);  // O(1)
            return;
        }


        ArrayList<NodeEntry> nodeData = node.getData();
        NodeEntry minEnlargement;

        double minArea = Double.MAX_VALUE;
        ArrayList<NodeEntry> candidates = new ArrayList<NodeEntry>();
        double area;
        for (NodeEntry entry : nodeData){  // O(node.curSize)
            area = entry.calcEnlargement(ne);
            if(minArea > area){
                minArea =area;
                candidates.clear();
                candidates.add(entry);
            }
            else if(minArea==area){
                candidates.add(entry);
            }
        }
        try {
            minEnlargement = getMinEnlargement(candidates);
            track.add(minEnlargement);
            fakeInsert(ne, minEnlargement.getChild(), track);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void insert(){

    }

    public Node getRoot() {
        return root;
    }
    public void setRoot(Node root) {
        this.root=root;
    }
    public void search(){}

}
