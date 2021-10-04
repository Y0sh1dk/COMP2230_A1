import java.util.Arrays;

public class Maze {
    private Cell[][] cells;
    private Cell startCell;
    private Cell finishCell;

    public Maze(int NLength, int MLength) {
        this.cells = new Cell[NLength][MLength];
        this.initialize();
    }

    private void initialize() {
        //    for (Cell[] row : cells) {
        //        Arrays.fill(row, new Cell());
        //    }
        //}
        int i = 0;
        for (Cell[] row: this.cells) {
            for (Cell c: row) {

            }
        }
    }

    public int numOfRows() {
        return this.cells[0].length;
    }

    public int numOfColumns() {
        return this.cells.length;
    }

    public void setCell(int inC, int inR, Cell.Type inType, Cell.Status inStatus) throws InvalidCellException {
        if (inC <= this.cells.length && inR <= this.cells[0].length) {
            this.cells[inC][inR].setType(inType);
            this.cells[inC][inR].setStatus(inStatus);
        } else {
            throw new InvalidCellException(inC, inR);
        }
    }

    public void setCell(int inC, int inR, Cell.Type inType) throws InvalidCellException {
        if (inC <= this.cells.length && inR <= this.cells[0].length) {
            this.setCell(inC, inR, inType, this.cells[inC][inR].getStatus());
        }
    }

    public void setCell(int inC, int inR, Cell.Status inStatus) throws InvalidCellException {
        if (inC <= this.cells.length && inR <= this.cells[0].length) {
            this.setCell(inC, inR, this.cells[inC][inR].getType(), inStatus);
        }
    }

    public void setStartCell(int inC, int inR) throws InvalidCellException {
        this.startCell = this.cells[inC][inR];
        this.setCell(inC, inR, Cell.Type.START);
    }

    public void setFinishCell(int inC, int inR) throws InvalidCellException {
        this.finishCell = this.cells[inC][inR];
        this.setCell(inC, inR, Cell.Type.FINISH);
    }

    public Cell getCell(int inC, int inR) throws InvalidCellException {
        if (inC <= this.cells.length && inR <= this.cells[0].length) {
            return this.cells[inC][inR];
        }
        else {
            throw new InvalidCellException(inC, inR);
        }
    }

    @Override
    public String toString() {
        return "Maze{" +
                "cells=" + Arrays.toString(cells) +
                ", startCell=" + startCell +
                ", finishCell=" + finishCell +
                '}';
    }
}

class InvalidCellException extends Exception {
    InvalidCellException(int inC, int inR) {
        super(String.format("Invalid Cell: Cell[%s][%s]", inC, inR));
    }
}
