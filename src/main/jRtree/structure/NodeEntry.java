package structure;

public class NodeEntry{

    private MBR mbr;
    private INode child;

    public NodeEntry(){
        this(null,null);
    }

    public NodeEntry(MBR mbr, INode n){
        this.mbr = mbr;
        this.child = n;
    }

    public MBR getMBR() {
        return mbr;
    }

    public INode getChild(){
        return child;
    }

    public double calculateEnlargement(NodeEntry ne){

        //Coordenandas del MBR de este NodeEntry

        MBR ne_mbr = ne.getMBR();

        double min_x = mbr.min_x();
        double min_y = mbr.min_y();
        double max_x = mbr.max_x();
        double max_y = mbr.max_y();


        double ne_min_x = ne_mbr.min_x();
        double ne_min_y = ne_mbr.min_y();
        double ne_max_x = ne_mbr.max_x();
        double ne_max_y = ne_mbr.max_y();
        /*
        Caso en que el MBR no crezca nada: esta entrada cabe completamente
        en el.
         */
        if(min_y>=ne_min_y && max_y<= ne_max_y && min_x >=ne_min_x && max_x<=ne_max_x) {
            System.err.println("within MBR");
            return 0.0;
        }
        /*Caso en el que este MBR posea alguna coordenada mayor o menor a las
        * que definen el rango del MBR contenedor */
        else

            return (Math.max(max_x, ne_max_x) - Math.min(min_x, ne_min_x))*
                    (Math.max(max_y, ne_max_y) - Math.min(min_y, ne_min_y))-(ne_mbr.height()*ne_mbr.width());


    }
}
