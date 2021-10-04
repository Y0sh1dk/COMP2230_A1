import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

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
        Random random = new Random();
        Maze m = new Maze(inMazeNLength, inMazeMLength);
        Boolean[][] visited = new Boolean[inMazeNLength][inMazeMLength];

        // Randomly select starting cell
        try {
            m.setStartCell(random.nextInt(inMazeNLength), random.nextInt(inMazeMLength));
        } catch (InvalidCellException e) {
            System.out.println(e);
        }
        
        // Walk entire maze
        int cellsVisited = 1;
        while(cellsVisited < inMazeNLength*inMazeMLength) {



            cellsVisited++;
        }


        return m;
    }

    private void mazeToFile() {

    }
}
