package structure;

import java.io.Serializable;

public class NodeEntry extends IDSettings implements Serializable {

    private MBR mbr;
    private INode child;

    public NodeEntry(){
        this(null,null);
    }

    public NodeEntry(MBR mbr, INode n){
        createID();
        this.mbr = mbr;
        this.child = n;
        //this.host = null;
    }


    public MBR getMBR() {
        return mbr;
    }
    public INode getChild(){
        return child;
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
