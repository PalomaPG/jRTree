package structure;

import java.io.Serializable;

/**
 * structure.MBR implementation. Assumes 2D rectangles, doesn't make any check.
 *
 *      pt1-----------topRight
 *       |             |
 *       |             |
 *       |             |
 *      leftBottom-----------pt3
 */


public class MBR implements Serializable{

    private Coord2D leftBottom;
    private Coord2D topRight;

    public MBR(double[][] mbrCorners) {
        this(new Coord2D(mbrCorners[0]), new Coord2D(mbrCorners[1]));
    }

    public MBR(double[] leftBottom, double[] topRight) {
        this(new Coord2D(leftBottom), new Coord2D(topRight));
    }

    public MBR(Coord2D[] coord2DS){
        this(coord2DS[0], coord2DS[1]);
    }

    /**
     * A structure.MBR can also be defined using left-bottom and top-right corners.
     */
    public MBR(Coord2D leftBottom, Coord2D topRight){
        this.leftBottom = leftBottom;
        this.topRight = topRight;
    }

    /**
     * Checks whether two MBRs intersect. Proves by contradiction.
     */
    public boolean intersect(MBR mbr){
        // If a mbr is at left of the other
        if (topRight.getX() < mbr.leftBottom.getX() || mbr.topRight.getX() < leftBottom.getX()){
            return false;
        } else if (topRight.getY() < mbr.leftBottom.getY() || mbr.topRight.getY() < leftBottom.getY()) {  // If a mbr is above the other
            return false;
        }
        return true;
    }

    public Coord2D getLeftBottom() {
        return leftBottom;
    }

    public Coord2D getTopRight() {
        return topRight;
    }

    @Override
    public boolean equals(Object aMBR){
        if (aMBR instanceof MBR){
            MBR m = (MBR) aMBR;
            return this.leftBottom.equals(m.getLeftBottom()) && this.topRight.equals(m.getTopRight());
        } else {
            return false;
        }
    }

    /**
     * A 2d rectangle contains another if its left-bottom and top-right corners are outside the other.
     * Can share sides and at most 2 corners. If they share 3 then share 4 and mbr1 is equals to mbr2.
     */
    public boolean contains(MBR mbr){
        Coord2D oPt2 = mbr.getLeftBottom();
        Coord2D oPt4 = mbr.getTopRight();
        // Is strictly at south-west or south but share axis Y or west but share axis X or are equals
        boolean leastLB = (this.leftBottom.isAtSouth(oPt2) || this.leftBottom.getY().equals(oPt2.getY())) &&
                (this.leftBottom.isAtWest(oPt2) || this.leftBottom.getX().equals(oPt2.getX()));
        // Is strictly at north-east or north but share axis Y or east but share axis X or are equals
        boolean mostTP = (this.topRight.isAtNorth(oPt4) || this.topRight.getY().equals(oPt4.getY())) &&
                (this.topRight.isAtEast(oPt4) || this.topRight.getX().equals(oPt4.getX()));
        return leastLB && mostTP;
    }

    public double height(){
        return this.topRight.getY() - this.leftBottom.getY();
    }

    public double width() {return this.topRight.getX() - this.leftBottom.getX();}

    public double area(){ return height()*width();}
}
