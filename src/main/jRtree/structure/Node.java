package structure;

import utils.Constants;

import java.io.*;
import java.util.ArrayList;

/**
 * structure.Node class for RTree. A node can contain a maximum number of node entries M (Capacity).
 * M must be determined by the user (or automatically) to make a structure.Node fits in a memory page.
 */

public class Node extends AbstractNode implements Serializable{

    private static final long serialVersionUID = 6717676594323462987L;
    private NodeEntry parent;

    public Node(int capacity, NodeEntry parent){
        super();
        this.capacity = capacity;
        this.parent = parent;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getCurSize() {
        return this.curSize;
    }


    public boolean insert(NodeEntry ne){
        if (curSize < capacity) {
            ne.setContainer(this);
            data.add(ne);
            curSize++;
            return true;
        }
        return false;
    }

    /**
     * If the node is a leaf should return a list of structure.MBR which intersects the genrect.
     * Else should ask to its children when a structure.MBR in a structure.NodeEntry intersects.
     */
    public ArrayList<MBR> search(MBR mbr){
        ArrayList<MBR> matched = new ArrayList<MBR>();
        if (this.isLeaf){
            for (NodeEntry ne : this.data){
                MBR tested = ne.getMBR();
                System.err.println(tested.toString());
                System.err.println(mbr.toString());
                if (tested.intersect(mbr)){
                    System.err.println("Haaaaaaaaaaaaaaaaaaaa");
                    matched.add(tested);
                }
            }
        } else {
            for (NodeEntry ne : this.data){
                MBR tested = ne.getMBR();
                if (tested.intersect(mbr)){
                    Node child =readFromDisk(ne.getChild());
                    if(child==null) System.err.println(String.format("Searching for child #%d", ne.getChild()));
                    else System.err.println("Not null");
                    this.writeToDisk();
                    System.err.println((child.getData().get(0) instanceof NodeEntry));
                    ArrayList<MBR> mbrs = child.search(mbr);
                    System.err.println("Es una hoja?" + child.isLeaf());
                   try {
                       matched.addAll(mbrs);
                   }catch (NullPointerException e){
                       System.err.println("hola\n");
                   }
                }
            }
        }
        return matched.isEmpty() ? null : matched;
    }

    /* This does exactly that, replace an oldEntry by a new one.
     * Nothing happens if oldEntry is not in data */
    public void replace(NodeEntry oldEntry, NodeEntry newEntry){
        System.out.println("----------------------------");
        System.out.println(oldEntry.getContainer().getNodeId());
        System.out.println(oldEntry.getContainer().curSize);
        System.out.println(oldEntry.getContainer().capacity);


        System.out.println(oldEntry.getChild());
        System.out.println(newEntry.getChild());
        System.out.println("----------------------------");

        int toBeReplaced = data.indexOf(oldEntry);
        if (toBeReplaced >= 0){
            newEntry.setContainer(this);
            data.set(toBeReplaced, newEntry);
        }
        else
            System.err.println("OLOOOOOOOOOOOOOOOOOOOOOJSKDFJKRJ");
    }

    public boolean delete(MBR mbr){
        return false;
    }

    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setIsLeaf(boolean b){
        this.isLeaf = b;
    }

    public ArrayList<NodeEntry> getData() {
        return data;
    }

    public void deleteFile(long id){
        try{
            File file = new File(Constants.TREE_DATA_DIRECTORY+"r"+id+".node");
            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeToDisk() {
        File theDir = new File(Constants.TREE_DATA_DIRECTORY);
        // if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + theDir.getName());
            boolean result = false;
            try{
                theDir.mkdir();
                result = true;
            } catch(SecurityException se){
                //handle it
                se.printStackTrace();
            }
            if(result) {
                System.out.println("DIR created");
            }
        } else{
            System.err.println("Ya existe dir");
        }

        System.err.println("Intentando escribir");
        try {
            File nodeFile = new File(Constants.TREE_DATA_DIRECTORY+"r" + nodeID + ".node");
            System.err.println(String.format("ID node: %d", nodeID));
            nodeFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(nodeFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(this);
            System.err.println("Escribiendo");
            fileOutputStream.close();
            out.close();
            System.err.println(Constants.TREE_DATA_DIRECTORY+"r" + nodeID + ".node");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /** Read from disk and return the node with the specified id. */
    public static Node readFromDisk(long id) {
        System.err.println("Leyendooooo....");
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(Constants.TREE_DATA_DIRECTORY+"r" +id + ".node");
            System.err.println("Abriendo archivo");

            ObjectInputStream in = new ObjectInputStream (fileInputStream);
            Node read = (Node)in.readObject();
            fileInputStream.close();
            in.close();
            return read;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }

    public NodeEntry getParent() {
        return parent;
    }

    public void setParent(NodeEntry parent) {
        this.parent = parent;
    }

    public Node removeParent() {

        Node parent_container = parent.getContainer();
        ArrayList<NodeEntry> ne_lst =parent_container.getData();
        int idx= ne_lst.indexOf(parent);
        ne_lst.remove(idx);
        return parent_container;

    }
}
