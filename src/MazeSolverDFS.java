/**
 *  FileName: MazeSolverDFS.java
 *  Assessment: COMP2230 Assignment
 *  Author: Yosiah de Koeyer
 *  Student No: c3329520
 */

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

/**
 * Class used to represent a maze solver using a DFS strategy
 */
public class MazeSolverDFS {
    /**
     * Entrypoint method
     * @param args command line args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid arguments, Example: java MazeSolverDFS example_maze.dat");
        }
        // Check if file exists
        Path filePath = Paths.get(args[0]);
        File f = new File(String.valueOf(filePath.toAbsolutePath()));
        // Return if the file doesnt exist
        if (!(f.exists() && !f.isDirectory())) {
            return;
        }
        // Here if file does exist
        MazeSolverDFS MS = new MazeSolverDFS();
        MS.run(filePath);
    }


    /**
     * Runs the solver
     * @param p path to the maze data file
     */
    private void run(Path p) {
        Maze m;
        // Try to generate a maze from file, return if file invalid
        try {
            m = fileToMaze(p);
        } catch (Exception e) {
            return;
        }

        long startTime = System.currentTimeMillis();
        ArrayList<Coordinate2D> solvePath = this.solveMazePath(m);
        long endTime = System.currentTimeMillis();

        // Print summary
        this.printSummary(m, solvePath, endTime - startTime);
    }


    /**
     * prints a summary of the solve
     * @param m maze instance
     * @param solvePath path the solve took
     * @param solveTime time the solve took
     */
    private void printSummary(Maze m, ArrayList<Coordinate2D> solvePath, long solveTime) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        String suffix = "";
        for (Coordinate2D coord : solvePath) {
            sb.append(suffix);
            sb.append(m.coordToIndex(coord.getX(), coord.getY()));
            suffix = ",";
        }
        sb.append(")");
        System.out.println(sb);
        System.out.println(solvePath.size() - 1);
        System.out.println(solveTime + "ms");
    }


    /**
     * Solves the maze by generating a path using DFS
     * @param m maze instance
     * @return list of coordinates representing the steps the maze took
     */
    private ArrayList<Coordinate2D> solveMazePath(Maze m) {
        ArrayList<Coordinate2D> visitedCoords = new ArrayList<>();
        Stack<Coordinate2D> coordStack = new Stack<>();

        // Ranking of directions :)
        Maze.Direction[] directionRanking = {Maze.Direction.DOWN, Maze.Direction.RIGHT, Maze.Direction.LEFT, Maze.Direction.UP};

        // Set finish cell
        Coordinate2D finishCell = m.getFinishCellCoord();

        // Set start cell
        Coordinate2D startCell = m.getStartCellCoord();
        m.setStartCellCoord(startCell);
        coordStack.push(startCell);

        // While stack isnt empty
        while(!coordStack.isEmpty()) {
            // Get cell at top of stack
            Coordinate2D currentCellCoord = coordStack.pop();

            visitedCoords.add(currentCellCoord);

            // We made it!
            if (currentCellCoord.equals(finishCell)) {
                break;
            }

            // Get available neighbours of cell
            ArrayList<Coordinate2D> availableNeighboursIndex = m.getVisitableNeighbours(currentCellCoord);
            availableNeighboursIndex.removeAll(visitedCoords);

            // Figure out which neighbour we should visit next!
            Coordinate2D nextCoord = null;
            int nextCoordRank = 0;
            for (Coordinate2D coord : availableNeighboursIndex) {
                // Find direction of neighbour
                Maze.Direction neighbourDirection = Maze.directionOfCell(currentCellCoord, coord);
                // If its ranked higher, use it instead
                if (Arrays.asList(directionRanking).indexOf(neighbourDirection) >= nextCoordRank) {
                    nextCoordRank = Arrays.asList(directionRanking).indexOf(neighbourDirection);
                    nextCoord = coord;
                }
            }

            // We aint backtracking yet :)
            if (nextCoord != null) {
                // Push current and next coord back onto the stack :)
                coordStack.push(currentCellCoord);
                coordStack.push(nextCoord);
            }
        }
        return visitedCoords;
    }


    /**
     * Generates a maze instance from a file
     * @param p path to the file
     * @return maze instance
     * @throws Exception if file path, or file itself invalid
     */
    private static Maze fileToMaze(Path p) throws Exception {
        Scanner inputStream = new Scanner(new File(String.valueOf(p.toAbsolutePath())));
        Maze m;
        String fileString;
        if (inputStream.hasNext()) {
            fileString = inputStream.next().trim().toLowerCase();
        } else {
            throw new Exception("Invalid file");
        }

        String[] fileStringArray = fileString.split(":");
        String[] mazeSizes = fileStringArray[0].trim().split(",");
        m = new Maze(Integer.parseInt(mazeSizes[0]), Integer.parseInt(mazeSizes[1]));

        // Add cell values in
        for (int i = 0; i < m.numOfRows(); i++) {
            for (int j = 0; j < m.numOfColumns(); j++) {
                int index = m.coordToIndex(j, i) - 1;
                int cellValue = Integer.parseInt(String.valueOf(fileStringArray[3].charAt(index)));
                m.setCellValue(j, i, cellValue);
            }
        }
        // Add start cell
        m.setStartCellCoord(m.indexToCoord(Integer.parseInt(fileStringArray[1])));

        // Add finish cell
        m.setFinishCellCoord(m.indexToCoord(Integer.parseInt(fileStringArray[2])));

        return m;
    }

}
