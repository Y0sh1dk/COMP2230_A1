import java.nio.file.Path;
import java.nio.file.Paths;

public class MazeGenerator {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Invalid arguments, Example: java MazeGenerator 5 6 maze.dat");
        }

        // Check arguments
        int mazeNLength, mazeMLength;
        Path filePath;
        try {
            mazeNLength = Integer.parseInt(args[0]);
            mazeMLength = Integer.parseInt(args[1]);
            filePath = Paths.get(args[2]);
        } catch (Exception e) {
            return;
        }

        MazeGenerator MG = new MazeGenerator();
        MG.run(mazeNLength, mazeMLength, filePath);
    }

    private void run(int inMazeNLength, int inMazeMLength, Path fileName) {
        Maze m  = this.generateMaze(inMazeNLength, inMazeMLength);
    }

    private Maze generateMaze(int inMazeNLength, int inMazeMLength) {
        Maze m = new Maze(inMazeNLength, inMazeMLength);
        return m;
    }

    private void mazeToFile() {

    }
}
