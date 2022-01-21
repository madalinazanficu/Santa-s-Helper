package strategy.sorting;

import outputfiles.ChildOutput;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IdStrategy implements SortingStrategy {
    private final List<ChildOutput> childrenList;

    public IdStrategy(final List<ChildOutput> childrenList) {
        this.childrenList = childrenList;
    }

    /**
     * Sort the list of children in increasing order by their id
     */
    @Override
    public void sortChildrenList() {
        Collections.sort(this.childrenList, Comparator.comparing(ChildOutput::getId));
    }
}
