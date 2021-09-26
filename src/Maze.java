import java.util.Arrays;

public class Maze {
    private Cell[][] cells;

    public Maze(int NLength, int MLength) {
        this.cells = new Cell[NLength][MLength];
        this.initialize();
    }

    private void initialize() {
        for (Cell[] row : cells) {
            Arrays.fill(row, new Cell());
        }
    }

    public void setCell(int inN, int inM, Cell.Type inType, Cell.Status inStatus) throws InvalidCellException {
        if (inN <= this.cells.length && inM <= this.cells[0].length) {
            this.cells[inN][inM].setType(inType);
            this.cells[inN][inM].setStatus(inStatus);
        } else {
            throw new InvalidCellException(inN, inM);
        }
    }

    public void setCell(int inN, int inM, Cell.Type inType) throws InvalidCellException {
        if (inN <= this.cells.length && inM <= this.cells[0].length) {
            this.setCell(inN, inM, inType, this.cells[inN][inM].getStatus());
        }
    }

    public void setCell(int inN, int inM, Cell.Status inStatus) throws InvalidCellException {
        if (inN <= this.cells.length && inM <= this.cells[0].length) {
            this.setCell(inN, inM, this.cells[inN][inM].getType(), inStatus);
        }
    }

    @Override
    public String toString() {
        return "Maze{" +
                "cells=" + Arrays.toString(cells) +
                '}';
    }
}

class InvalidCellException extends Exception {
    InvalidCellException(int inN, int inM) {
        super(String.format("Invalid Cell: Cell[%s][%s]", inN, inM));
    }
}
