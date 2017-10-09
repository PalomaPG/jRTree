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
    public ArrayList<MBR> search(MBR mbr, String path){
        ArrayList<MBR> matched = new ArrayList<MBR>();
        if (this.isLeaf){
            for (NodeEntry ne : this.data){
                MBR tested = ne.getMBR();
                System.err.println(tested.toString());
                System.err.println(mbr.toString());
                if (tested.intersect(mbr)){
                    matched.add(tested);
                }
            }
        } else {
            for (NodeEntry ne : this.data){
                MBR tested = ne.getMBR();
                if (tested.intersect(mbr)){
                    Node child =readFromDisk(ne.getChild(), path);
                    if(child==null) System.err.println(String.format("Searching for child #%d", ne.getChild()));
                    this.writeToDisk(path);
                    ArrayList<MBR> mbrs = child.search(mbr, path);
                   try {
                       matched.addAll(mbrs);
                   }catch (NullPointerException e){
                   }
                }
            }
        }
        return matched.isEmpty() ? null : matched;
    }

    /* This does exactly that, replace an oldEntry by a new one.
     * Nothing happens if oldEntry is not in data */
    public void replace(NodeEntry oldEntry, NodeEntry newEntry){

        int toBeReplaced = data.indexOf(oldEntry);
        if (toBeReplaced >= 0){
            newEntry.setContainer(this);
            data.set(toBeReplaced, newEntry);
        }
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

    public void deleteFile(long id, String path){
        try{
            File file = new File(path+"/r"+id+".node");
            if(file.delete()){
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void writeToDisk(String path) {

        try {
            File nodeFile = new File(path+"/r" + nodeID + ".node");
            nodeFile.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(nodeFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(this);
            fileOutputStream.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /** Read from disk and return the node with the specified id. */
    public static Node readFromDisk(long id, String path) {
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(path+"/r" +id + ".node");
            System.err.println("Reading from: "+path+"/r" +id + ".node");
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
