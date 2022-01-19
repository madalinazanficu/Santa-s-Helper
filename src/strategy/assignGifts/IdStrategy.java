package strategy.assignGifts;

import outputfiles.ChildOutput;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IdStrategy implements AssignGiftsStrategy {
    private List<ChildOutput> childrenList;

    public IdStrategy(final List<ChildOutput> childrenList) {
        this.childrenList = childrenList;
    }

    /**
     * Sort the list of children in increasing order by their id
     * @return the sorted list of children by id criteria
     */
    @Override
    public List<ChildOutput> sortChildrenList() {
        Collections.sort(this.childrenList, Comparator.comparing(ChildOutput::getId));
        return this.childrenList;
    }
}
