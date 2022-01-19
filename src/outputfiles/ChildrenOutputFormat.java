package outputfiles;

import java.util.ArrayList;
import java.util.List;

/**
 * Database that maintain the final list of children that received gifts
 */

public final class ChildrenOutputFormat {
    private final ArrayList<ChildOutput> children = new ArrayList<>();

    public ChildrenOutputFormat() { }
    public ChildrenOutputFormat(final ChildrenOutputFormat childrenOutputFormat) {
        for (ChildOutput childOutput : childrenOutputFormat.getChildrenOutputList()) {
            this.children.add(new ChildOutput(childOutput));
        }
    }

    /**
     * Method used for clearing the list of children for every simulation
     */
    public void clear() {
        children.clear();
    }

    /**
     * Method used for setting the list of children that received gifts
     * @param setChildren the list of children used for setting the final output list
     */
    public void setChildrenOutputList(final List<ChildOutput> setChildren) {

        // iterate in the list of children to make deep-copy for every child
        for (ChildOutput childOutput : setChildren) {
            ChildOutput copyChild = new ChildOutput(childOutput);
            children.add(copyChild);
        }
    }

    public ArrayList<ChildOutput> getChildrenOutputList() {
        return children;
    }

    @Override
    public String toString() {
        return "ChildrenOutputFormat{"
                + "children=" + children
                + '}';
    }
}
