package structure;

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
        double minX = mbr.getPt2().getX();
        double minY = mbr.getPt2().getY();
        double maxX = mbr.getPt4().getX();
        double maxY = mbr.getPt4().getY();

        double neMinX = neMBR.getPt2().getX();
        double neMinY = neMBR.getPt2().getY();
        double neMaxX = neMBR.getPt4().getX();
        double neMaxY = neMBR.getPt4().getY();

        return (Math.max(maxX, neMaxX) - Math.min(minX, neMinX))*
                (Math.max(maxY, neMaxY) - Math.min(minY, neMinY))-mbr.area();
    }

    public void setMbr(MBR mbr){
        this.mbr = mbr;
    }
}
