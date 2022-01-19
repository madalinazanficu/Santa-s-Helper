package outputfiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used in order to incorporate numberOfYears lists of children
 * It contains a list of lists of children
 * Every list of children is linked with a year
 */
public final class AnnualChildrenFormat {
    private final List<ChildrenOutputFormat> annualChildren = new ArrayList<ChildrenOutputFormat>();

    public AnnualChildrenFormat() { }

    /**
     * Method used for adding a new list of children
     * Method is called when new updated are available (every year)
     * @param childrenOutputFormat new list of children linked with the current year
     */
    public void addNewRoundArray(final ChildrenOutputFormat childrenOutputFormat) {
        ChildrenOutputFormat childrenOutputFormat1 = new ChildrenOutputFormat(childrenOutputFormat);
        annualChildren.add(childrenOutputFormat1);
    }

    public List<ChildrenOutputFormat> getAnnualChildren() {
        return annualChildren;
    }
}
