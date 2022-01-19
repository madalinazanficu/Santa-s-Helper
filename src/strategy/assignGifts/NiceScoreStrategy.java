package strategy.assignGifts;

import outputfiles.ChildOutput;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NiceScoreStrategy implements AssignGiftsStrategy {
    private List<ChildOutput> childrenList;

    public NiceScoreStrategy(final List<ChildOutput> childrenList) {
        this.childrenList = childrenList;
    }

    /**
     * Sort the list of children in decreasing order by their average score
     * If the average score of two children is equal, sort them in increasing order by their id
     * @return the sorted list
     */
    @Override
    public List<ChildOutput> sortChildrenList() {
        Collections.sort(childrenList, new Comparator<ChildOutput>() {
            @Override
            public int compare(final ChildOutput o1, final ChildOutput o2) {
                double diff = o2.getAverageScore() - o1.getAverageScore();

                // in case the averageScore is equal => sort by id
                if (diff == 0) {
                    return o1.getId() - o2.getId();
                }
                if (diff > 0) {
                    return 1;
                }
                return -1;
            }
        });
        return childrenList;
    }
}
