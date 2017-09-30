package structure;

/**
 * structure.MBR implementation. Assumes 2D rectangles, doesn't make any check.
 *
 *      pt1-----------pt4
 *       |             |
 *       |             |
 *       |             |
 *      pt2-----------pt3
 */


public class MBR {

    private Coord2D pt1;
    private Coord2D pt2;
    private Coord2D pt3;
    private Coord2D pt4;

    public MBR(double[][] mbr_square) {
        this(new Coord2D(mbr_square[0]), new Coord2D(mbr_square[1]), new Coord2D(mbr_square[2]),
                new Coord2D(mbr_square[3]));
    }

    public MBR(double[] pt1, double[] pt2, double[] pt3, double[] pt4) {
        this(new Coord2D(pt1), new Coord2D(pt2), new Coord2D(pt3), new Coord2D(pt4));
    }

    public MBR(Coord2D[] coord2DS){
        this(coord2DS[0], coord2DS[1], coord2DS[2], coord2DS[3]);
    }

    public MBR(Coord2D pt1, Coord2D pt2, Coord2D pt3, Coord2D pt4){
        this.pt1 = pt1;
        this.pt2 = pt2;
        this.pt3 = pt3;
        this.pt4 = pt4;
    }

    /**
     * A structure.MBR can also be defined using left-bottom and top-right corners.
     */
    public MBR(Coord2D leftBottom, Coord2D topRight){
        this(new Coord2D(leftBottom.getX(), topRight.getY()), leftBottom,
                new Coord2D(topRight.getX(), leftBottom.getY()), topRight);
    }

    /**
     * Checks whether two MBRs intersect. Proves by contradiction.
     */
    public boolean intersect(MBR mbr){
        // If a mbr is at left of the other
        if (pt4.getX() < mbr.pt2.getX() || mbr.pt4.getX() < pt2.getX()){
            return false;
        } else if (pt4.getY() < mbr.pt2.getY() || mbr.pt4.getY() < pt2.getY()) {  // If a mbr is above the other
            return false;
        }
        return true;
    }

    public Coord2D getPt1() {
        return pt1;
    }

    public Coord2D getPt2() {
        return pt2;
    }

    public Coord2D getPt3() {
        return pt3;
    }

    public Coord2D getPt4() {
        return pt4;
    }

    @Override
    public boolean equals(Object aMBR){
        if (aMBR instanceof MBR){
            MBR m = (MBR) aMBR;
            return this.pt1.equals(m.getPt1()) &&
                    this.pt2.equals(m.getPt2()) &&
                    this.pt3.equals(m.getPt3()) &&
                    this.pt4.equals(m.getPt4());
        }
        else {
            return false;
        }
    }

    public double min_x(){
        return Math.min(Math.min(pt1.getX(), pt2.getX()), pt3.getX());

    }

    public double max_x(){
        return Math.max(Math.max(pt1.getX(), pt2.getX()), pt3.getX());

    }
    public double width(){
        return max_x()-min_x();
    }

    public double min_y(){
        return Math.min(Math.min(pt1.getY(), pt2.getY()), pt3.getY());

    }

    public double max_y(){
        return Math.max(Math.max(pt1.getY(), pt2.getY()), pt3.getY());

    }
    public double height(){
        return max_y()-min_y();
    }

    public double area(){ return height()*width()}
}
