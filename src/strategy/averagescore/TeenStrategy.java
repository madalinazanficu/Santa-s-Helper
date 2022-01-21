package strategy.averagescore;

import common.Constants;
import outputfiles.ChildOutput;

import java.util.List;

public final class TeenStrategy implements AverageScoreStrategy {
    private final ChildOutput child;

    public TeenStrategy(final ChildOutput child) {
        this.child = child;
    }

    @Override
    public void computeAverageScore() {
        double sum = 0.0;
        double avg = 0.0;
        List<Double> niceScoreHistory = this.child.getNiceScoreHistory();
        int n = niceScoreHistory.size();
        int imp = (n * (n + 1)) / 2;
        for (int i = 0; i < n; i++) {
            sum += niceScoreHistory.get(i) * (i + 1);
        }
        if (n != 0) {
            avg = sum / imp;
        }
        // added the niceScoreBonus to formula
        avg = avg +  avg * child.getNiceScoreBonus() / Constants.PERCENT;
        if (avg >= Constants.BABY_AVERAGE_SCORE) {
            avg = Constants.BABY_AVERAGE_SCORE;
        }
        child.setAverageScore(avg);
    }
}
