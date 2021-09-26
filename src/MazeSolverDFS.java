import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MazeSolverDFS {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid arguments, Example: java MazeSolverDFS maze.dat");
        }
        // Check if file exists
        Path filePath = Paths.get(args[1]);
        File f = new File(String.valueOf(filePath.toAbsolutePath()));
        if (!(f.exists() && !f.isDirectory())) {
            return;
        }
        // Here if file does exist
        MazeSolverDFS MS = new MazeSolverDFS();
        MS.run(filePath);

    }

    private void run(Path p) {

    }
}
