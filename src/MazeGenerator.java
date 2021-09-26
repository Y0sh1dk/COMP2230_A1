import java.nio.file.Path;
import java.nio.file.Paths;

public class MazeGenerator {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Invalid arguments, Example: java MazeGenerator 5 6 maze.dat");
        }

        // Check arguments
        int mazeNLength, mazeMLength;
        Path filePath;
        try {
            mazeNLength = Integer.parseInt(args[1]);
            mazeMLength = Integer.parseInt(args[2]);
            filePath = Paths.get(args[3]);
        } catch (Exception e) {
            return;
        }

        MazeGenerator MG = new MazeGenerator();
        MG.run(mazeNLength, mazeMLength, filePath);
    }

    private void run(int inMazeNLength, int inMazeMLength, Path fileName) {

    }
}
