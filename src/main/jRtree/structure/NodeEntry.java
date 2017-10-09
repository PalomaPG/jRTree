package structure;

import java.io.Serializable;
import java.util.Comparator;


public class NodeEntry implements Serializable {

    private MBR mbr;
    private long child_id;
    private Node container;

    public NodeEntry(){
        this(null,-1);
    }

    public NodeEntry(MBR mbr, long n){
        this.mbr = mbr;
        this.child_id = n;
        this.container=null;
    }

    public NodeEntry(MBR mbr, long n, Node container){
        this.mbr = mbr;
        this.child_id = n;
        this.container=container;
    }

    public MBR getMBR() {
        return mbr;
    }
    public long getChild(){
        return child_id;
    }


    public double calculateEnlargement(NodeEntry ne){
        MBR neMBR = ne.getMBR();
        // Coordenadas del MBR de este NodeEntry
        double minX = mbr.getLeftBottom().getX();
        double minY = mbr.getLeftBottom().getY();
        double maxX = mbr.getTopRight().getX();
        double maxY = mbr.getTopRight().getY();

        double neMinX = neMBR.getLeftBottom().getX();
        double neMinY = neMBR.getLeftBottom().getY();
        double neMaxX = neMBR.getTopRight().getX();
        double neMaxY = neMBR.getTopRight().getY();

        return (Math.max(maxX, neMaxX) - Math.min(minX, neMinX))*
                (Math.max(maxY, neMaxY) - Math.min(minY, neMinY))-mbr.area();
    }

    public void setMbr(MBR mbr){
        this.mbr = mbr;
    }


    public static Comparator<NodeEntry> xAxisComparator(){
        return new Comparator<NodeEntry>() {
            public int compare(NodeEntry o1, NodeEntry o2) {
                double x1 = o1.mbr.getLeftBottom().getX();
                double x2 = o2.mbr.getLeftBottom().getX();
                return Double.compare(x1,x2);
            }
        };
    }

    public static Comparator<NodeEntry> yAxisComparator(){
        return new Comparator<NodeEntry>() {
            public int compare(NodeEntry o1, NodeEntry o2) {
                double y1 = o1.mbr.getLeftBottom().getY();
                double y2 = o2.mbr.getLeftBottom().getY();
                return Double.compare(y1,y2);
            }
        };
    }


    public boolean equals(Object o){
        if(o instanceof NodeEntry){

            NodeEntry ne = (NodeEntry)o;
            return this.mbr.equals(ne.mbr) && this.child_id==ne.child_id;

        }
        else return false;
    }

    public void setContainer(Node container) {
        this.container = container;
    }

    public Node getContainer() {
        return container;
    }
}
