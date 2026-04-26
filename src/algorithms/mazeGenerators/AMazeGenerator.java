package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    @Override
    public long measureAlgorithmTimeMillis(int rows, int cols) {
        long startTime = System.currentTimeMillis();
        generate(rows, cols);
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    @Override
    public abstract Maze generate(int rows, int cols);
}
