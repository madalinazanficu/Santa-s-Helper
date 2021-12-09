package strategy;

import dataprocessing.Child;

import java.util.List;

public final class KidStrategy implements AverageScoreStrategy {
    private Child child;

    public KidStrategy(final Child child) {
        this.child = child;
    }

    @Override
    public void computeAverageScore() {
        Double sum = 0.0;
        Double avg = 0.0;
        List<Double> niceScoreHistory = this.child.getNiceScoreHistory();
        for (Double niceScore : niceScoreHistory) {
            sum += niceScore;
        }
        if (niceScoreHistory.size() != 0) {
            avg = sum / niceScoreHistory.size();
        }
        child.setAverageScore(avg);
    }
}
