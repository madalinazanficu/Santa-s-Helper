package strategy.sorting;

public interface SortingStrategy {
    /**
     * Implemented by IdStrategy/NiceScoreStrategy/NiceScoreCityStrategy
     * in order to sort the list of children after specific criteria
     */
    void sortChildrenList();
}
