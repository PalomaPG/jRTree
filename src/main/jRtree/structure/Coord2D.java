package structure;// Simple class to represent two-dimensional coordinates of real numbers

import java.io.Serializable;

public class Coord2D implements Serializable{
    private static final long serialVersionUID = 1L;

    private int x;
    private int y;

    public Coord2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coord2D(int[] pt){
        this.x = pt[0];
        this.y = pt[1];
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object aCoord){
        if(aCoord instanceof Coord2D){
            Coord2D c = (Coord2D) aCoord;
            return this.x == c.getX() && this.y == c.getY();
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

    @Override
    public String toString() {
        return new StringBuilder().append("(").append(x).append(",").append(y).append(")").toString();
    }
}
