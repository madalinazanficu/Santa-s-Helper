package strategy.averagescore;

import common.Constants;
import outputfiles.ChildOutput;

public final class BabyStrategy implements AverageScoreStrategy {
    private final ChildOutput child;

    public BabyStrategy(final ChildOutput child) {
        this.child = child;
    }

    @Override
    public void computeAverageScore() {
        this.child.setAverageScore(Constants.BABY_AVERAGE_SCORE);
    }
}
