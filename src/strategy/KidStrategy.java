package strategy;

import outputfiles.ChildOutput;

import java.util.List;

public final class KidStrategy implements AverageScoreStrategy {
    private ChildOutput child;

    public KidStrategy(final ChildOutput child) {
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

        // added the niceScoreBonus to formula
        avg = avg +  avg * child.getNiceScoreBonus() / 100;
        if (avg >= 10.0) {
            avg = 10.0;
        }
        child.setAverageScore(avg);
    }
}
