package strategy;

import common.Constants;
import outputfiles.ChildOutput;

public final class BabyStrategy implements AverageScoreStrategy {
    private ChildOutput child;

    public BabyStrategy(final ChildOutput child) {
        this.child = child;
    }

    @Override
    public void computeAverageScore() {
        this.child.setAverageScore(Constants.BABY_AVERAGE_SCORE);
    }
}
