/**
 *  FileName: Coordinate2D.java
 *  Assessment: COMP2230 Assignment
 *  Author: Yosiah de Koeyer
 *  Student No: c3329520
 */

/**
 * Class used to represent a 2D coordinate
 */
public class Coordinate2D {
    private int X;
    private int Y;

    /**
     * Coordinate2D Constructor
     * @param inX x value
     * @param inY y values
     */
    Coordinate2D(int inX, int inY) {
        this.X = inX;
        this.Y = inY;
    }

    /**
     * Get x value stored
     * @return x value
     */
    public int getX() {
        return this.X;
    }

    /**
     * Get y value stored
     * @return y value
     */
    public int getY() {
        return this.Y;
    }

    /**
     * Checks if provided coordinates are immediate neighbours of each other
     * @param coord1 coordinate to compare
     * @param coord2 coordinate to compare
     * @return true if are immediate neighbours, else false
     */
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

    /**
     * Overridden toString() method
     * @return String representation of instance
     */
    @Override
    public String toString() {
        return "Coordinate2D{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }

    /**
     * Overridden equals() method
     * @param o object to compare to current instance
     * @return true if they are equal, else false
     */
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        if (!(o instanceof Coordinate2D)) {
            return false;
        }

        Coordinate2D c = (Coordinate2D) o;

        return this.X == c.getX() && this.Y == c.getY();
    }
}
