package outputfiles;

import inputfiles.ChildInput;
import inputfiles.ChildUpdate;
import inputfiles.Gift;
import inputfiles.InputAnnualChange;

import java.util.ArrayList;
import java.util.List;

public final class AnnualChange {
    private Double newSantaBudget;
    private List<Gift> newGifts;
    private List<ChildOutput> newChildren;
    private List<ChildUpdate> childrenUpdates;

    public AnnualChange(final InputAnnualChange annualChange) {
        this.newSantaBudget = annualChange.getNewSantaBudget();
        this.newGifts = annualChange.getNewGifts();

        // Transform the child format from InputFormat to OutputFormat
        List<ChildOutput> newChildrenList = new ArrayList<>();
        List<ChildInput> children = annualChange.getNewChildren();

        for (ChildInput child : children) {
            ChildOutput newChild = new ChildOutput(child);
            newChild.addNiceScoreHistory(child.getNiceScore());
            newChildrenList.add(newChild);
        }
        this.newChildren = newChildrenList;
        this.childrenUpdates = annualChange.getChildrenUpdates();
    }

    public Double getNewSantaBudget() {
        return newSantaBudget;
    }

    public void setNewSantaBudget(final Double newSantaBudget) {
        this.newSantaBudget = newSantaBudget;
    }

    public List<Gift> getNewGifts() {
        return newGifts;
    }

    public void setNewGifts(final List<Gift> newGifts) {
        this.newGifts = newGifts;
    }

    public List<ChildOutput> getNewChildren() {
        return newChildren;
    }

    public void setNewChildren(final List<ChildOutput> children) {
        this.newChildren = children;
    }

    public List<ChildUpdate> getChildrenUpdates() {
        return childrenUpdates;
    }

    public void setChildrenUpdates(final List<ChildUpdate> childrenUpdates) {
        this.childrenUpdates = childrenUpdates;
    }
}
