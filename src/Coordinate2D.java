/**
 *  FileName: Coordinate2D.java
 *  Assessment: COMP2230 Assignment
 *  Author: Yosiah de Koeyer
 *  Student No: c3329520
 */

public class Coordinate2D {
    private int X;
    private int Y;

    Coordinate2D(int inX, int inY) {
        this.X = inX;
        this.Y = inY;
    }

    public int getX() {
        return this.X;
    }

    public int getY() {
        return this.Y;
    }

    public static boolean isCoordsNeighbours(Coordinate2D coord1, Coordinate2D coord2) {
        if (new Coordinate2D(coord1.getX() + 1, coord1.getY()).equals(coord2)) {
            return true;
        } else if (new Coordinate2D(coord1.getX() - 1, coord1.getY()).equals(coord2)) {
            return true;
        }  else if (new Coordinate2D(coord1.getX(), coord1.getY() + 1).equals(coord2)) {
            return true;
        } else if (new Coordinate2D(coord1.getX(), coord1.getY() - 1).equals(coord2)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Coordinate2D{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Coordinate2D)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Coordinate2D c = (Coordinate2D) o;

        return this.X == c.getX() && this.Y == c.getY();
    }
}
