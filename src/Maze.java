import java.util.Arrays;

public class Maze {
    private Cell[][] cells;

    public Maze() {

    }

    @Override
    public String toString() {
        return "Maze{" +
                "cells=" + Arrays.toString(cells) +
                '}';
    }
}
