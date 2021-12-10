package datawriting;

import java.util.ArrayList;
import java.util.List;

// baza de date care retine o lista de copii
public class ChildrenOutputFormat {
    private ArrayList<ChildOutput> children = new ArrayList<>();

    public void clear() {
        children.clear();
    }
    public ChildrenOutputFormat() {

    }
    // Copy-Construct
    public ChildrenOutputFormat(ChildrenOutputFormat childrenOutputFormat) {
        for (ChildOutput childOutput : childrenOutputFormat.getChildrenOutputList()) {
            this.children.add(new ChildOutput(childOutput));
        }
    }

    public void addChildren(ChildOutput childOutput) {
        ChildOutput childOutput1 = new ChildOutput(childOutput);
        children.add(childOutput1);
    }
    public void setChildrenOutputList(final List<ChildOutput> setChildren) {

        // iterez prin lista de copii pentru a le face deep copy
        for (ChildOutput childOutput : setChildren) {
            ChildOutput copyChild = new ChildOutput(childOutput);

            // ii adaug in lista de copii din formatul final
            children.add(copyChild);
        }
    }

    public ArrayList<ChildOutput> getChildrenOutputList() {
        return children;
    }

    @Override
    public String toString() {
        return "ChildrenOutputFormat{" +
                "children=" + children +
                '}';
    }
}
