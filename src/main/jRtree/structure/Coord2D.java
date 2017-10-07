package structure;// Simple class to represent two-dimensional coordinates of real numbers

import java.io.Serializable;

public class Coord2D implements Serializable{
    private static final long serialVersionUID = 1L;

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

    public boolean isAtNorth(Coord2D c){
        return this.y > c.getY();
    }

    public boolean isAtWest(Coord2D c){
        return this.x < c.getX();
    }

    public boolean isAtSouth(Coord2D c){
        return this.y < c.getY();
    }

    public boolean isAtEast(Coord2D c){
        return this.x > c.getX();
    }

    public double distanceTo(Coord2D c){
        return Math.sqrt(Math.pow(this.x - c.getX(), 2) + Math.pow(this.y - c.getY(), 2));
    }

}
