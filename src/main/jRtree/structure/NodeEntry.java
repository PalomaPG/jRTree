package structure;

public class NodeEntry{

    private MBR mbr;
    private INode child;
    private INode host;

    public NodeEntry(){
        this(null,null);
    }

    public NodeEntry(MBR mbr, INode n){
        this.mbr = mbr;
        this.child = n;
        this.host = null;
    }

    public NodeEntry(MBR mbr, INode n, INode host){
        this.mbr = mbr;
        this.child = n;
        this.host = host;
    }

    public MBR getMBR() {
        return mbr;
    }
    public INode getChild(){
        return child;
    }
    public INode getHost(){ return host;}
    public void setHost(INode in){
        host = in;
    }

    public double calcEnlargement(NodeEntry ne){

        //Coordenandas del MBR de este NodeEntry

        MBR ne_mbr = ne.getMBR();

        double min_x = mbr.getPt2().getX();
        double min_y = mbr.getPt2().getY();
        double max_x = mbr.getPt4().getX();
        double max_y = mbr.getPt4().getY();


        double ne_min_x = ne_mbr.getPt2().getX();
        double ne_min_y = ne_mbr.getPt2().getY();
        double ne_max_x = ne_mbr.getPt4().getX();
        double ne_max_y = ne_mbr.getPt4().getY();
        /*
        Caso en que el MBR no crezca nada: esta entrada cabe completamente
        en el.
         */
        if(min_y<=ne_min_y && max_y>= ne_max_y &&
                min_x <=ne_min_x && max_x>=ne_max_x) {
            System.err.println("within MBR");
            return 0.0;
        }
        /*Caso en el que este MBR posea alguna coordenada mayor o menor a las
        * que definen el rango del MBR contenedor */
        else

            return (Math.max(max_x, ne_max_x) - Math.min(min_x, ne_min_x))*
                    (Math.max(max_y, ne_max_y) - Math.min(min_y, ne_min_y))- mbr.area();


    }
}
