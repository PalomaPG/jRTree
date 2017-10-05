package structure;

//import diskmanagement.DiskAccess;

import exception.RTreeDiskAccessException;
import exception.RTreeException;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RTree{

    private int nodeSize;
    private NodeSplitter nodeSplitter;
    private Node root;
    private long rootPtr = -1;
    private File saveFile;
    //private DiskAccess diskAccess;


    public RTree(int nodeSize, NodeSplitter nodeSplitter) throws RTreeException, RTreeDiskAccessException {
        this.nodeSize = nodeSize;
        this.nodeSplitter = nodeSplitter;
        this.root = new Node(nodeSize);
        //this.diskAccess = new DiskAccess();
        this.saveFile = new File(Constants.TREE_FILE);
        if(!this.saveFile.exists()){
            try {
                this.saveFile.createNewFile();
            }catch (IOException e){
                throw new RTreeException("problema al crear archivo de arbol");
            }
        }
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
        realInsert(new NodeEntry(mbr, new NullNode()), this.root, false);
    }


    /**
     *
     * @param ne : NodeEntry to be inserted
     * @param node : Where insertion begins
     * @param reCalcMBR : Tells if the NodeEntry instance containing node should update it's MBR
     * @return NodeEntry(s) to be inserted in node's parent. Doesn't apply for root.
     */
    private ArrayList<NodeEntry> realInsert(NodeEntry ne, INode node, boolean reCalcMBR){
        /* Base case leaf node */
        if (node.isLeaf()){
            boolean inserted = node.insert(ne);  // O(1)
            ArrayList<NodeEntry> newEntries;
            if (!inserted){
                newEntries = nodeSplitter.split(ne, node);  // Size 2
                if (this.root.isLeaf()){
                    /* Just happens once. When the root overflows must create a new root, but since was a leaf
                     * realInsert returns at insert method. That's why the new root should be created here */
                    newRoot(newEntries);
                }
            } else { /* MBR was inserted. Should return an ArrayList with a NodeEntry with updated MBR. In overflow case
             (above), the Splitter should be responsible of calculating new MBRs.*/
                newEntries = new ArrayList<NodeEntry>(1);
                if (reCalcMBR){
                    newEntries.add(newUpdatedNodeEntry(node));
                }
            }
            return newEntries;
        }
        /* General case non-leaf node */
        ArrayList<NodeEntry> nodeData = node.getData();
        double minAreaGrowth = Double.MAX_VALUE;
        ArrayList<NodeEntry> candidates = new ArrayList<NodeEntry>();
        double areaGrowth;
        for (NodeEntry entry : nodeData){  // O(node.curSize)
            areaGrowth = entry.calculateEnlargement(ne);
            if(minAreaGrowth > areaGrowth){
                candidates.clear();
                candidates.add(entry);
            } else if(minAreaGrowth==areaGrowth){
                /* Multiple entries have to grow the same and are minimum at this moment*/
                candidates.add(entry);
            }
        }
        NodeEntry minEnlargement = getMinEnlargement(candidates);
        /* Recursive call 'ö' */
        ArrayList<NodeEntry> newEntries = realInsert(ne, minEnlargement.getChild(), !(minAreaGrowth == 0) );
        if (!(newEntries.isEmpty())){
            /* Si entra aquí debe actualizarse este nodo con las nuevas entradas que vienen de abajo.
            Puede devolder un NodeEntry con el MBR actualizado hacia arriba o dos si es que hay overflow. Vacío si es
            q el MBR no necesita actualzación.
            Al menos una de las nuevas entradas debe reemplazar al NodeEntry apuntado por minEnlargement, el otro
            (si es que existe) debe insertarse y chequear si hay overflow */
            node.replace(minEnlargement, newEntries.get(0));
            ArrayList<NodeEntry> possibleNewEntries;
            try {
                boolean inserted = node.insert(newEntries.get(1));
                if (!inserted){ /* Overflow */
                    possibleNewEntries = nodeSplitter.split(newEntries.get(1), node);
                    possibleNewEntries.get(0).getChild().setIsLeaf(false);
                    possibleNewEntries.get(1).getChild().setIsLeaf(false);
                    if (node.equals(this.root)){
                        /* Se debe crear nueva raiz e insertar las nuevas entradas antes de retornar */
                        newRoot(possibleNewEntries);
                    }
                    return possibleNewEntries;
                }
            } catch (IndexOutOfBoundsException exception){

            } finally {
                /* Updating above MBR. */
                newEntries.clear();
                if (reCalcMBR){
                    newEntries.add(newUpdatedNodeEntry(node));
                }
                return newEntries;  /* Could be empty */
            }
        } else {
            /* The MBR didn't have to increase. Just return an empty array again */
            return new ArrayList<NodeEntry>(0);
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root=root;
    }

    public ArrayList<MBR> search(MBR mbr){
        return this.root.search(mbr);
    }

    private void newRoot(ArrayList<NodeEntry> newEntries){
        this.root = new Node(this.nodeSize);
        this.root.setIsLeaf(false);  /* <-- very important */
        for (NodeEntry nodeEntry : newEntries){  /* Optional: Create a insertAll method at Node class */
            this.root.insert(nodeEntry);
        }
    }

    private NodeEntry newUpdatedNodeEntry(INode childNode){
        MBR newMBR = nodeSplitter.calculateMBR(childNode.getData());
        return new NodeEntry(newMBR, childNode);
    }
}
