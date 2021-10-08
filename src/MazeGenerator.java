/**
 *  FileName: MazeGenerator.java
 *  Assessment: COMP2230 Assignment
 *  Author: Yosiah de Koeyer
 *  Student No: c3329520
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class used to represent a maze generator
 */
public class MazeGenerator {
    /**
     * Entrypoint method
     * @param args command line args
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid arguments, Example: java MazeGenerator 5 6 example_maze.dat");
        }
        // Check arguments
        int mazeXLength, mazeYLength;
        Path filePath;
        try {
            mazeXLength = Integer.parseInt(args[0]);
            mazeYLength = Integer.parseInt(args[1]);
            filePath = Paths.get(args[2]);
        } catch (Exception e) {
            return;
        }

        MazeGenerator MG = new MazeGenerator();
        MG.run(mazeXLength, mazeYLength, filePath);
    }


    /**
     * Runs the generator
     * @param inX width of maze
     * @param inY height of maze
     * @param fileName file to store maze in
     */
    private void run(int inX, int inY, Path fileName) {
        Maze m = new Maze(inX, inY);
        ArrayList<Coordinate2D> path = this.generatePath(m);
        System.out.println("\n-------------GENERATION PATH-------------");
        this.printPath(path, m);
        this.updateMazeCells(path, m);
        System.out.println("\n-------------CELL VALUES-------------");
        this.printMazeOpenness(m);
        System.out.println("\n-------------CELL WALLS-------------");
        this.printMazeWalls(m);
        System.out.println("Start Cell: " + m.getStartCellCoord() + "Finish Cell:"  + m.getFinishCellCoord());
        try {
            this.mazeToFile(m, fileName);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * Prints the walls of the maze
     * @param m maze
     */
    private void printMazeWalls(Maze m) {
        for (int y = 0; y < m.numOfRows(); y++){
            for (int x = 0; x < m.numOfColumns(); x++) {
                int openness = m.getCellValue(x, y);
                switch (openness) {
                    case 0:
                        System.out.print(centerString(5, "_|"));
                    break;
                    case 1:
                        System.out.print(centerString(5, "_"));
                    break;
                    case 2:
                        System.out.print(centerString(5, "|"));
                    break;
                    case 3:
                        System.out.print(centerString(5, " "));
                    break;
                }
            }
            System.out.println("\n");
        }
    }


    /**
     * Prints the openness of the maze
     * @param m maze
     */
    private void printMazeOpenness(Maze m) {
        for (int y = 0; y < m.numOfRows(); y++){
             for (int x = 0; x < m.numOfColumns(); x++) {
                System.out.print(centerString(5, String.valueOf(m.getCellValue(x, y))));
            }
            System.out.println("\n");
        }
    }


    /**
     * Get all the directions a list of coordinates can travel
     * @param path list of coordinates
     * @param m maze
     * @return all directions all coordinates can travel
     */
    private HashMap<Coordinate2D, ArrayList<Maze.Direction>> getCellTravelDirections(ArrayList<Coordinate2D> path, Maze m) {
        HashMap<Coordinate2D, ArrayList<Maze.Direction>> cellTravelDirections = new HashMap<>();

        // add keys into hashmap
        for (Coordinate2D coord : path) {
            cellTravelDirections.putIfAbsent(coord, new ArrayList<>());
        }

        // Loop through the whole path :)
        for (int i = 0; i < path.size(); i++) {

            Coordinate2D prevCoord;
            Coordinate2D currentCoord;
            Coordinate2D nextCoord;
            try {
                prevCoord = path.get(i - 1);
            } catch (Exception e) {
                prevCoord = null;
            }
            currentCoord = path.get(i);

            try {
                nextCoord = path.get(i + 1);
            } catch (Exception e) {
                nextCoord = null;
            }

            // Inner path
            if (prevCoord != null && nextCoord != null) {
                // If continuing on path
                if (Coordinate2D.isCoordsNeighbours(currentCoord, nextCoord)) {
                    cellTravelDirections.get(nextCoord).add(Maze.directionOfCell(nextCoord, currentCoord));
                    cellTravelDirections.get(currentCoord).add(Maze.directionOfCell(currentCoord, nextCoord));
                }
                // If had backtracked
                if (!Coordinate2D.isCoordsNeighbours(currentCoord, prevCoord)) {
                    Coordinate2D cameFromCoord = null;
                    for (int j = path.indexOf(currentCoord) - 1; j >= 0; j--) {
                        if (Coordinate2D.isCoordsNeighbours(path.get(j), currentCoord)) {
                            cameFromCoord = path.get(j);
                            break;
                        }
                    }
                    cellTravelDirections.get(currentCoord).add(Maze.directionOfCell(currentCoord, cameFromCoord));
                    cellTravelDirections.get(cameFromCoord).add(Maze.directionOfCell(cameFromCoord, currentCoord));
                }

            }
            // Start Cell
            else if (prevCoord == null) {
                // Should always be
                if (Coordinate2D.isCoordsNeighbours(currentCoord, nextCoord)) {
                    cellTravelDirections.get(currentCoord).add(Maze.directionOfCell(currentCoord, nextCoord));
                    cellTravelDirections.get(nextCoord).add(Maze.directionOfCell(nextCoord, currentCoord));
                }
            }
            // End Cell
            else if (nextCoord == null) {
                // If not backtracking
                if (Coordinate2D.isCoordsNeighbours(currentCoord, prevCoord)) {
                    cellTravelDirections.get(currentCoord).add(Maze.directionOfCell(currentCoord, prevCoord));
                }
                // Backtracking!
                else {
                    Coordinate2D cameFromCoord = null;
                    for (int j = path.indexOf(currentCoord) - 1; j >= 0; j--) {
                        if (Coordinate2D.isCoordsNeighbours(path.get(j), currentCoord)) {
                            cameFromCoord = path.get(j);
                            break;
                        }
                    }
                    cellTravelDirections.get(currentCoord).add(Maze.directionOfCell(currentCoord, cameFromCoord));
                    cellTravelDirections.get(cameFromCoord).add(Maze.directionOfCell(cameFromCoord, currentCoord));
                }
            }
        }
        return cellTravelDirections;
    }


    /**
     * Update the walls of the maze
     * @param path path traveled
     * @param m maze
     */
    private void updateMazeCells(ArrayList<Coordinate2D> path, Maze m) {
        HashMap<Coordinate2D, ArrayList<Maze.Direction>> cellTravelDirection = this.getCellTravelDirections(path, m);

        for (Map.Entry<Coordinate2D, ArrayList<Maze.Direction>> entry : cellTravelDirection.entrySet()) {
            Coordinate2D coord = entry.getKey();
            // Contains both right and down
            if(entry.getValue().contains(Maze.Direction.RIGHT) && entry.getValue().contains(Maze.Direction.DOWN)) {
                m.setCellValue(coord.getX(), coord.getY(), 3);
            }
            // Only down open
            else if(entry.getValue().contains(Maze.Direction.RIGHT)) {
                m.setCellValue(coord.getX(), coord.getY(), 1);
            }
            // Only right open
            else if(entry.getValue().contains(Maze.Direction.DOWN)) {
                m.setCellValue(coord.getX(), coord.getY(), 2);
            }
            // neither
            else {
                m.setCellValue(coord.getX(), coord.getY(), 0);
            }
        }
    }


    /**
     * Print the path taken
     * @param path path taken
     * @param m maze
     */
    private void printPath(ArrayList<Coordinate2D> path, Maze m) {
        // Print the path
        for (int y = 0; y <= m.numOfRows(); y++) {
            for (int x = 0; x <= m.numOfColumns(); x++) {
                if (path.contains(new Coordinate2D(x,y))) {
                    System.out.print(centerString(5,String.valueOf(path.indexOf(new Coordinate2D(x,y)))));
                }
            }
            System.out.println("\n");
        }
    }


    /**
     * Randomly generate a path in the maze
     * @param m maze
     * @return path
     */
    private ArrayList<Coordinate2D> generatePath(Maze m) {
        ArrayList<Coordinate2D> visitedCoords = new ArrayList<>();
        Stack<Coordinate2D> coordStack = new Stack<>();

        int randomX = ThreadLocalRandom.current().nextInt(0, m.numOfColumns());
        int randomY = ThreadLocalRandom.current().nextInt(0, m.numOfRows());

        // Set start cell
        Coordinate2D startCell = new Coordinate2D(randomX,randomY);
        m.setStartCellCoord(startCell);
        coordStack.push(startCell);

        // Run till stack empty
        while(!coordStack.isEmpty()) {
            // Get cell at top of stack
            Coordinate2D currentCellCoord = coordStack.pop();

            if (!visitedCoords.contains(currentCellCoord)) {
                visitedCoords.add(currentCellCoord);
            }

            // Get available neighbours of cell
            ArrayList<Coordinate2D> availableNeighboursIndex = m.getNeighbours(currentCellCoord);

            // Remove if have been visited
            availableNeighboursIndex.removeAll(visitedCoords);

            // If there are available neighbours!
            if (!availableNeighboursIndex.isEmpty()) {
                // Shuffle neighbours
                Collections.shuffle(availableNeighboursIndex);
                // Add all neighbours to the stack
                coordStack.addAll(availableNeighboursIndex);
            }
        }
        // Set finish cell
        m.setFinishCellCoord(visitedCoords.get(visitedCoords.size()-1));

        return visitedCoords;
    }


    /**
     * Center a string
     * @param width width to center it in
     * @param s string
     * @return centered string :)
     */
    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }


    /**
     * Saves the maze instance to a file
     * @param m maze
     * @param filePath path of file
     * @throws IOException if cannot create file or write too it.
     */
    private void mazeToFile(Maze m, Path filePath) throws IOException {
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        Path path = Files.createFile(filePath);
        Files.writeString(path, m.toString());
    }

}
