package structure;

import java.util.Comparator;

public class NodeEntry{

    private MBR mbr;
    private INode child;
    //private INode host;

    public NodeEntry(){
        this(null,null);
    }

    public NodeEntry(MBR mbr, INode n){
        this.mbr = mbr;
        this.child = n;
        //this.host = null;
    }

    /*public NodeEntry(MBR mbr, INode n, INode host){
        this.mbr = mbr;
        this.child = n;
        this.host = host;
    }*/

    public MBR getMBR() {
        return mbr;
    }
    public INode getChild(){
        return child;
    }
   /* public INode getHost(){ return host;}
    public void setHost(INode in){
        host = in;
    }
*/

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

    public static Comparator<NodeEntry>  xAxisComparator(){
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

}
