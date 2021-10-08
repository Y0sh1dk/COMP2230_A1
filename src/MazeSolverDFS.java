import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class MazeSolverDFS {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid arguments, Example: java MazeSolverDFS maze.dat");
        }
        // Check if file exists
        Path filePath = Paths.get(args[0]);
        File f = new File(String.valueOf(filePath.toAbsolutePath()));
        if (!(f.exists() && !f.isDirectory())) {
            return;
        }
        // Here if file does exist
        MazeSolverDFS MS = new MazeSolverDFS();
        MS.run(filePath);

    }

    private void run(Path p) {
        Maze m;
        try {
            m = fileToMaze(p);
        } catch (Exception e) {
            return;
        }
        System.out.println("Maze String: ");
        System.out.println(m);

        System.out.println("\n-------------CELL VALUES-------------");
        this.printMazeOpenness(m);


        ArrayList<Coordinate2D> solvePath = this.solveMazePath(m);
        for (Coordinate2D coord : solvePath) {
            System.out.print(m.coordToIndex(coord.getX(), coord.getY()) +",");
        }
    }

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

            Coordinate2D nextCoord = null;
            int nextCoordRank = 0;

            for (Coordinate2D coord : availableNeighboursIndex) {
                if(Maze.directionOfCell(currentCellCoord, coord) == Maze.Direction.UP) {
                    if(Arrays.asList(directionRanking).indexOf(Maze.Direction.UP) >= nextCoordRank) {
                        nextCoordRank = Arrays.asList(directionRanking).indexOf(Maze.Direction.UP);
                        nextCoord = coord;
                    }
                }
                if(Maze.directionOfCell(currentCellCoord, coord) == Maze.Direction.LEFT) {
                    if(Arrays.asList(directionRanking).indexOf(Maze.Direction.LEFT) >= nextCoordRank) {
                        nextCoordRank = Arrays.asList(directionRanking).indexOf(Maze.Direction.LEFT);
                        nextCoord = coord;
                    }
                }
                if(Maze.directionOfCell(currentCellCoord, coord) == Maze.Direction.RIGHT) {
                    if(Arrays.asList(directionRanking).indexOf(Maze.Direction.RIGHT) >= nextCoordRank) {
                        nextCoordRank = Arrays.asList(directionRanking).indexOf(Maze.Direction.RIGHT);
                        nextCoord = coord;
                    }
                }
                if(Maze.directionOfCell(currentCellCoord, coord) == Maze.Direction.DOWN) {
                    if(Arrays.asList(directionRanking).indexOf(Maze.Direction.DOWN) >= nextCoordRank) {
                        nextCoordRank = Arrays.asList(directionRanking).indexOf(Maze.Direction.DOWN);
                        nextCoord = coord;
                    }
                }
            }

            // We aint backtracking yet :)
            if (nextCoord != null) {
                // Push current coord back onto the stack :)
                coordStack.push(currentCellCoord);
                coordStack.push(nextCoord);
            }
        }
        return visitedCoords;
    }

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

    private void printMazeOpenness(Maze m) {
        for (int y = 0; y < m.numOfRows(); y++){
            for (int x = 0; x < m.numOfColumns(); x++) {
                System.out.print(centerString(5, String.valueOf(m.getCellValue(x, y))));
            }
            System.out.println("\n");
        }
    }

    // TODO(Yoshi): Factor out
    public static String centerString (int width, String s) {
        return String.format("%-" + width  + "s", String.format("%" + (s.length() + (width - s.length()) / 2) + "s", s));
    }


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
