package datawriting;

import java.util.ArrayList;
import java.util.List;

public class AnnualChildrenFormat {
    private final List<ChildrenOutputFormat> annualChildren = new ArrayList<ChildrenOutputFormat>();


    public AnnualChildrenFormat() {

    }
    public void addNewRoundArray(ChildrenOutputFormat childrenOutputFormat) {
        ChildrenOutputFormat childrenOutputFormat1 = new ChildrenOutputFormat(childrenOutputFormat);
        annualChildren.add(childrenOutputFormat1);
    }

    public List<ChildrenOutputFormat> getAnnualChildren() {
        return annualChildren;
    }
}
