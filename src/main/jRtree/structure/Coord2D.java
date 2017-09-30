package structure;// Simple class to represent two-dimensional coordinates of real numbers

public class Coord2D {

    private Double x;
    private Double y;

    public Coord2D(double x, double y){
        this.x = new Double(x);
        this.y = new Double(y);
    }

    public Coord2D(double[] pt){
        this.x = new Double(pt[0]);
        this.y = new Double(pt[1]);
    }

    public Double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object aCoord){
        if(aCoord instanceof Coord2D){
            Coord2D c = (Coord2D) aCoord;
            return this.x.equals(c.getX()) && this.y.equals(c.getY());
        }
        else {
            return false;
        }
    }

}
