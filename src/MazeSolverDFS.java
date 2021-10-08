import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

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
        System.out.println(m);



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
                //System.out.println("Brrr");
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
