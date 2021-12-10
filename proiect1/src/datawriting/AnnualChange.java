package datawriting;

import dataprocessing.ChildInput;
import dataprocessing.ChildUpdate;
import dataprocessing.Gift;
import dataprocessing.InputAnnualChange;

import java.util.ArrayList;
import java.util.List;

public class AnnualChange {
    private Double newSantaBudget;
    private List<Gift> newGifts;
    private List<ChildOutput> newChildren;
    private List<ChildUpdate> childrenUpdates;

    public AnnualChange(InputAnnualChange annualChange) {
        this.newSantaBudget = annualChange.getNewSantaBudget();
        this.newGifts = annualChange.getNewGifts();

        // Tranform copilul din inputFormat in OutputFormat
        List<ChildOutput> newChildren = new ArrayList<>();
        List<ChildInput> children = annualChange.getNewChildren();
        for (ChildInput child : children) {
            ChildOutput newChild = new ChildOutput(child);
            newChild.addNiceScoreHistory(child.getNiceScore());
            newChildren.add(newChild);
        }
        this.newChildren = newChildren;
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

    public void setNewChildren(final List<ChildOutput> newChildren) {
        this.newChildren = newChildren;
    }

    public List<ChildUpdate> getChildrenUpdates() {
        return childrenUpdates;
    }

    public void setChildrenUpdates(final List<ChildUpdate> childrenUpdates) {
        this.childrenUpdates = childrenUpdates;
    }
}
