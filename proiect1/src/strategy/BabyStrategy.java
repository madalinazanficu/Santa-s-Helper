package strategy;

import dataprocessing.Child;

public final class BabyStrategy implements AverageScoreStrategy {
    private Child child;

    public BabyStrategy(final Child child) {
        this.child = child;
    }

    @Override
    public void computeAverageScore() {
        this.child.setAverageScore(10.0);
    }
}
