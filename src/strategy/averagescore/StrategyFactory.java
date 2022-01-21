package strategy.averagescore;

import common.Constants;
import outputfiles.ChildOutput;

public final class StrategyFactory {

    private StrategyFactory() { }

    /**
     * Method used for generating different strategies of computing the average score for a child
     * @param child to compute the average score for a specific child
     * @return the chosen age based strategy
     */
    public static AverageScoreStrategy createStrategy(final ChildOutput child) {
        AverageScoreStrategy strategy = null;
        if (child.getAge() < Constants.BABY_AGE) {
            strategy = new BabyStrategy(child);
        }
        if (child.getAge() >= Constants.BABY_AGE && child.getAge() < Constants.KID_AGE) {
            strategy = new KidStrategy(child);
        }
        if (child.getAge() >= Constants.KID_AGE && child.getAge() <= Constants.TEEN_AGE) {
            strategy = new TeenStrategy(child);
        }
        return strategy;
    }
}
