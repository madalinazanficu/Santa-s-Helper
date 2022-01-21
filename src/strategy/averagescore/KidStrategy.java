package strategy.averagescore;

import common.Constants;
import outputfiles.ChildOutput;

import java.util.List;

public final class KidStrategy implements AverageScoreStrategy {
    private final ChildOutput child;

    public KidStrategy(final ChildOutput child) {
        this.child = child;
    }

    @Override
    public void computeAverageScore() {
        Double sum = 0.0;
        double avg = 0.0;
        List<Double> niceScoreHistory = this.child.getNiceScoreHistory();
        for (Double niceScore : niceScoreHistory) {
            sum += niceScore;
        }
        if (niceScoreHistory.size() != 0) {
            avg = sum / niceScoreHistory.size();
        }

        // added the niceScoreBonus to formula
        avg = avg +  avg * child.getNiceScoreBonus() / Constants.PERCENT;
        if (avg >= Constants.BABY_AVERAGE_SCORE) {
            avg = Constants.BABY_AVERAGE_SCORE;
        }
        child.setAverageScore(avg);
    }
}
