package strategy;

import datawriting.ChildOutput;

public final class BabyStrategy implements AverageScoreStrategy {
    private ChildOutput child;

    public BabyStrategy(final ChildOutput child) {
        this.child = child;
    }

    @Override
    public void computeAverageScore() {
        this.child.setAverageScore(10.0);
    }
}
