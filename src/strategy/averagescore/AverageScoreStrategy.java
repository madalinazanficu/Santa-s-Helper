package strategy.averagescore;

public interface AverageScoreStrategy {
    /**
     *  Implemented by KidStrategy/TeenStrategy/BabyStrategy in order to apply Strategy pattern
     */
    void computeAverageScore();
}
