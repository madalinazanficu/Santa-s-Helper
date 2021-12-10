package strategy;

import common.Constants;
import datawriting.ChildOutput;

public class StrategyFactory {
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
