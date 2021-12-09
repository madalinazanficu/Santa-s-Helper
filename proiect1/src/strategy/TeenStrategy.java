package strategy;

import dataprocessing.Child;

import java.util.List;

public final class TeenStrategy implements AverageScoreStrategy {
    private Child child;

    public TeenStrategy(final Child child) {
        this.child = child;
    }

    @Override
    public void computeAverageScore() {
        Double sum = 0.0;
        Double avg = 0.0;
        List<Double> niceScoreHistory = this.child.getNiceScoreHistory();
        int n = niceScoreHistory.size();
        int imp = (n * (n + 1)) / 2;
        for (int i = 0; i < n; i++) {
            sum += niceScoreHistory.get(i) * (i + 1);
        }
        if (n != 0) {
            avg = sum / imp;
        }
        child.setAverageScore(avg);
        System.out.println(avg);
    }
}