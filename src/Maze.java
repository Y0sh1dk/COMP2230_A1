/**
 *  FileName: Maze.java
 *  Assessment: COMP2230 Assignment
 *  Author: Yosiah de Koeyer
 *  Student No: c3329520
 */

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class used to represent a maze
 */
public class Maze {
    private Integer[][] cells;
    private Coordinate2D startCellCoord;
    private Coordinate2D finishCellCoord;

    /**
     * enum for directions :)
     */
    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }


    /**
     * Maze constructor
     * @param XLength width of maze
     * @param YLength height of maze
     */
    public Maze(int XLength, int YLength) {
        this.cells = new Integer[XLength][YLength];
        this.initialize();
    }


    /**
     * Initialize all cells to null
     */
    private void initialize() {
        for (Integer[] row: this.cells) {
            Arrays.fill(row, null);
        }
    }


    /**
     * Gets all valid neighbours of a provided coordinate
     * @param inCoord coordinate to find neighbours of
     * @return list of neighbours
     */
    public ArrayList<Coordinate2D> getNeighbours(Coordinate2D inCoord) {
        ArrayList<Coordinate2D> neighbourCoords = new ArrayList<>();

        neighbourCoords.add(new Coordinate2D(inCoord.getX(), inCoord.getY() + 1));
        neighbourCoords.add(new Coordinate2D(inCoord.getX(), inCoord.getY() - 1));
        neighbourCoords.add(new Coordinate2D(inCoord.getX() - 1, inCoord.getY()));
        neighbourCoords.add(new Coordinate2D(inCoord.getX() + 1, inCoord.getY()));

        // Remove if not valid coord
        neighbourCoords.removeIf(index -> (!isValidCoord(index)));

        return neighbourCoords;
    }


    /**
     * Gets all neighbours that are visitable, taking into account walls
     * @param inCoord coordinate to find neighbours of
     * @return list of neighbours
     */
    public ArrayList<Coordinate2D> getVisitableNeighbours(Coordinate2D inCoord) {
        ArrayList<Coordinate2D> visitableNeighbours = new ArrayList<>();
        // Get all valid coords around it
        ArrayList<Coordinate2D> validNeighbours = this.getNeighbours(inCoord);

        // remove neighbours we cant travel too.
        for (Coordinate2D nextCoord : validNeighbours) {
            Direction directionOfNextCell = directionOfCell(inCoord, nextCoord);
            switch (directionOfNextCell) {
                case UP:
                    // If next cell is 3 or 2
                    if (this.getCellValue(nextCoord.getX(), nextCoord.getY()) == 3 || this.getCellValue(nextCoord.getX(), nextCoord.getY()) == 2) {
                        visitableNeighbours.add(nextCoord);
                    }
                    break;
                case DOWN:
                    // If this cell 3 or 2
                    if (this.getCellValue(inCoord.getX(), inCoord.getY()) == 3 || this.getCellValue(inCoord.getX(), inCoord.getY()) == 2) {
                        visitableNeighbours.add(nextCoord);
                    }
                    break;
                case LEFT:
                    // If next cell is 1 or 3
                    if (this.getCellValue(nextCoord.getX(), nextCoord.getY()) == 1 || this.getCellValue(nextCoord.getX(), nextCoord.getY()) == 3) {
                        visitableNeighbours.add(nextCoord);
                    }
                    break;
                case RIGHT:
                    // If this cell is 1 or 3
                    if (this.getCellValue(inCoord.getX(), inCoord.getY()) == 1 || this.getCellValue(inCoord.getX(), inCoord.getY()) == 3) {
                        visitableNeighbours.add(nextCoord);
                    }
                    break;
            }
        }
        return visitableNeighbours;
    }


    /**
     * Sets the value of a cell
     * @param inX cell x coord
     * @param inY cell y coord
     * @param inValue value to set
     */
    public void setCellValue(int inX, int inY, int inValue) {
        this.cells[inX][inY] = inValue;
    }


    /**
     * Gets the value of a cell
     * @param inX cell x coord
     * @param inY cell y coord
     * @return cell value
     */
    public Integer getCellValue(int inX, int inY) {
        return this.cells[inX][inY];
    }


    /**
     * check if a coordinate is valid
     * @param inCoord coordinate to check
     * @return true if it is, else false
     */
    private boolean isValidCoord(Coordinate2D inCoord) {
        return (inCoord.getX() < this.cells.length && inCoord.getX() >= 0) && (inCoord.getY() < this.cells[0].length && inCoord.getY() >= 0);
    }


    /**
     * Direction to another coordinate
     * @param coord reference coordinate
     * @param nextCoord coordinate to check
     * @return a direction
     */
    public static Direction directionOfCell(Coordinate2D coord, Coordinate2D nextCoord) {
        // Up
        if (coord.getX() == nextCoord.getX() && coord.getY() > nextCoord.getY()) {
            return Direction.UP;
        }
        // Down
        else if (coord.getX() == nextCoord.getX() && coord.getY() < nextCoord.getY()) {
            return Direction.DOWN;
        }
        // Left
        else if (coord.getX() > nextCoord.getX() && coord.getY() == nextCoord.getY()) {
            return Direction.LEFT;
        }
        // Right
        else if (coord.getX() < nextCoord.getX() && coord.getY() == nextCoord.getY()) {
            return Direction.RIGHT;
        }
        // Shouldnt get here
        else {
            return null;
        }
    }


    /**
     * Overridden toString() method
     * @return a string representation of instance
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.numOfColumns()).append(",").append(this.numOfRows()).append(":");
        sb.append(this.coordToIndex(startCellCoord)).append(":").append(this.coordToIndex(finishCellCoord)).append(":");
        // Add cell openness
        for (int i = 0; i < this.numOfRows(); i++) {
            for (int j = 0; j < this.numOfColumns(); j++) {
                sb.append(this.cells[j][i]);
            }
        }
        return sb.toString();
    }


    /**
     * Converts a coordinate to a index
     * @param coord coordinate to convert
     * @return index
     */
    public int coordToIndex(Coordinate2D coord) {
        return this.coordToIndex(coord.getX(), coord.getY());
    }


    /**
     * Converts a coordinate to a index
     * @param inX x coord
     * @param inY y coord
     * @return index
     */
    public int coordToIndex(int inX, int inY) {
        return ((inY * this.numOfColumns()) + inX) + 1;
    }


    /**
     * Converts a index to a coordinate
     * @param inIndex index to convert
     * @return coordinate
     */
    public Coordinate2D indexToCoord(int inIndex) {
        int x = (inIndex - 1) % this.numOfColumns();
        int y = (inIndex - 1) / this.numOfColumns();
        return new Coordinate2D(x, y);
    }


    /**
     * Return the number of rows of the maze
     * @return
     */
    public int numOfRows() {
        return this.cells[0].length;
    }


    /**
     * Return the number of columns of the maze
     * @return
     */
    public int numOfColumns() {
        return this.cells.length;
    }


    /**
     * Get coordinates of the start cell
     * @return start cell
     */
    public Coordinate2D getStartCellCoord() {
        return startCellCoord;
    }


    /**
     * Set the start cell
     * @param startCellCoord start cell
     */
    public void setStartCellCoord(Coordinate2D startCellCoord) {
        this.startCellCoord = startCellCoord;
    }


    /**
     * Get the finish cell
     * @return the finish cell
     */
    public Coordinate2D getFinishCellCoord() {
        return finishCellCoord;
    }


    /**
     * Set the finish cell
     * @param finishCellCoord finish cell
     */
    public void setFinishCellCoord(Coordinate2D finishCellCoord) {
        this.finishCellCoord = finishCellCoord;
    }

}
