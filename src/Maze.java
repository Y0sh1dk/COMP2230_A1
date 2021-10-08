import java.util.ArrayList;
import java.util.Arrays;

public class Maze {
    private Integer[][] cells;
    private Coordinate2D startCellCoord;
    private Coordinate2D finishCellCoord;

    public Maze(int XLength, int YLength) {
        this.cells = new Integer[XLength][YLength];
        this.initialize();
    }

    /**
     * Initialise all cells to zero
     */
    private void initialize() {
        for (Integer[] row: this.cells) {
            Arrays.fill(row, null);
        }
    }

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

    public ArrayList<Coordinate2D> getVisitableNeighbours(Coordinate2D inCoord) {
        ArrayList<Coordinate2D> visitableNeighbours = new ArrayList<>();
        // Get all valid coords around it
        ArrayList<Coordinate2D> validNeighbours = this.getNeighbours(inCoord);

        // remove ones cant travel too.
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
                    if (this.getCellValue(nextCoord.getX(), nextCoord.getY()) == 1 || this.getCellValue(nextCoord.getX(), nextCoord.getY()) == 3) {
                        visitableNeighbours.add(nextCoord);
                    }
                    break;
                case RIGHT:
                    if (this.getCellValue(inCoord.getX(), inCoord.getY()) == 1 || this.getCellValue(inCoord.getX(), inCoord.getY()) == 3) {
                        visitableNeighbours.add(nextCoord);
                    }
                    break;
            }
        }
        return visitableNeighbours;
    }

    public void setCellValue(int inX, int inY, int inValue) {
        this.cells[inX][inY] = inValue;
    }

    public Integer getCellValue(int inX, int inY) {
        return this.cells[inX][inY];
    }

    private boolean isValidCoord(Coordinate2D inCoord) {
        return (inCoord.getX() < this.cells.length && inCoord.getX() >= 0) && (inCoord.getY() < this.cells[0].length && inCoord.getY() >= 0);
    }

    // Direction to next coord
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

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

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

    public int coordToIndex(Coordinate2D coord) {
        return this.coordToIndex(coord.getX(), coord.getY());
    }

    public int coordToIndex(int inX, int inY) {
        return ((inY * this.numOfColumns()) + inX) + 1;
    }

    public Coordinate2D indexToCoord(int inIndex) {
        int x = (inIndex - 1) % this.numOfColumns();
        int y = (inIndex - 1) / this.numOfColumns();
        return new Coordinate2D(x, y);
    }

    public int numOfRows() {
        return this.cells[0].length;
    }

    public int numOfColumns() {
        return this.cells.length;
    }


    public Coordinate2D getStartCellCoord() {
        return startCellCoord;
    }

    public void setStartCellCoord(Coordinate2D startCellCoord) {
        this.startCellCoord = startCellCoord;
    }

    public Coordinate2D getFinishCellCoord() {
        return finishCellCoord;
    }

    public void setFinishCellCoord(Coordinate2D finishCellCoord) {
        this.finishCellCoord = finishCellCoord;
    }
}

class InvalidCellException extends Exception {
    InvalidCellException(int inC, int inR) {
        super(String.format("Invalid Cell: Cell[%s][%s]", inC, inR));
    }
}
