package structure;

import java.io.Serializable;

public class NodeEntry extends IDSettings implements Serializable {

    private MBR mbr;
    private long child_id;

    public NodeEntry(){
        this(null,-1);
    }

    public NodeEntry(MBR mbr, long n){
        this.mbr = mbr;
        this.child_id = n;
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
}
