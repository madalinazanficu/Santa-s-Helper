package strategy.assignGifts;

import enums.CityStrategyEnum;
import outputfiles.ChildOutput;

import java.util.List;

public final class AssignGiftsFactory {
    private AssignGiftsFactory() { }

    /**
     * Generating different strategies of sorting the list of children
     * depending on multiple criteria
     * @param strategyEnum the strategy type required from input
     * @param children the list of children that will be sorted
     * @return the sorting strategy applied
     */
    public static AssignGiftsStrategy createStrategy(final CityStrategyEnum strategyEnum,
                                                     final List<ChildOutput> children) {
        AssignGiftsStrategy strategy = null;
        if (strategyEnum.equals(CityStrategyEnum.ID)) {
            strategy = new IdStrategy(children);
        }
        if (strategyEnum.equals(CityStrategyEnum.NICE_SCORE)) {
            strategy = new NiceScoreStrategy(children);
        }
        if (strategyEnum.equals(CityStrategyEnum.NICE_SCORE_CITY)) {
            strategy = new NiceScoreCityStrategy(children);
        }
        return strategy;
    }
}
