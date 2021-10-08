# COMP2230_A1

## Compiling

      javac MazeGenerator.java MazeSolverDFS.java

## Running

### Maze Generator

      java MazeGenerator <x_size> <y_size> <filepath>

### Maze Solver

      java MazeSolverDFS <filepath>

## Examples

An example `example_maze.dat` file has been included. To run the solver against this file, first compile, then run:

      java MazeSolverDFS example_maze.dat

The output should be like so

      (1,2,7,6,11,16,21,22,17,12,13,14,15,10,9,4,5,4,9,8,3)
      20
      2ms