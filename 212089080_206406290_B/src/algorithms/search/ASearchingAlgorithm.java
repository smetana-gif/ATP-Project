package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {
    protected int AStateCounter = 0;

    @Override
    public abstract Solution solve(ISearchable s);

    @Override
    public abstract String getName();

}
