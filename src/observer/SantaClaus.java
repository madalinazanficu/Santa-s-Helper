package observer;

import common.Constants;
import enums.CityStrategyEnum;
import inputfiles.ChildInput;
import inputfiles.ChildUpdate;
import inputfiles.Gift;
import inputfiles.InputData;

import outputfiles.AnnualChange;
import outputfiles.ChildOutput;
import enums.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Database used for maintain the information that will be updated every round
 */
public final class SantaClaus implements Update {
    private static SantaClaus instance = null;

    // the annual budget
    private Double santaBudget;

    // the budget unit
    private Double budgetUnit;

    private List<ChildOutput> children;

    // the list of gifts available
    private List<Gift> santaGiftList;

    // new update for the strategy
    private CityStrategyEnum strategy;

    /**
     * @return the instance of the database
     */
    public static SantaClaus getInstance() {
        if (instance == null) {
            instance = new SantaClaus();
        }
        return instance;
    }

    private SantaClaus() {
        this.santaBudget = null;
        this.budgetUnit = null;
        this.children = new ArrayList<>();
        this.santaGiftList = new ArrayList<>();
        this.strategy = null;
    }

    /**
     * clear the database
     */
    public void clearSantaClaus() {
        this.santaBudget = 0.0;
        this.budgetUnit = 0.0;
        this.children = null;
        this.santaGiftList = null;
        this.strategy = null;
    }

    /**
     * Method used for updating santa information for the current year
     * @param annualChange the changes applied for teh current year
     */
    @Override
    public void update(final AnnualChange annualChange) {
        updateChildrenList(annualChange.getNewChildren());
        updateSantaGiftList(annualChange.getNewGifts());
        updateSpecificChildren(annualChange.getChildrenUpdates());
        updateBudget(annualChange.getNewSantaBudget());
        updateBudgetUnit();
        updateStrategy(annualChange.getStrategy());
    }

    /**
     * Update the strategy every round of simulation
     * @param newStrategy the new strategy of sharing gifts
     */
    public void updateStrategy(final CityStrategyEnum newStrategy) {
        this.strategy = newStrategy;
    }

    /**
     * Method used for updating the list of children
     * Added only the children with age <18
     * @param newChildren the new children needed to be added in the list
     */
    public void updateChildrenList(final List<ChildOutput> newChildren) {

        // increase every child age with 1 year
        for (ChildOutput child : children) {
            child.incrementAge();
        }
        // remove the children that are not eligible for presents
        children.removeIf(x -> x.getAge() > Constants.TEEN_AGE);

        // add new children in the list (but only the ones eligible for presents)
        for (ChildOutput newChild : newChildren) {
            if (newChild.getAge() <= Constants.TEEN_AGE) {
                this.children.add(newChild);
            }
        }
    }

    /**
     * Method used for updating santa's gifts list
     * Added only the gifts that are not in the initial list
     * @param newSantaGiftList the new gifts needed to be added in the list
     */
    public void updateSantaGiftList(final List<Gift> newSantaGiftList) {
        for (Gift newGift : newSantaGiftList) {
            if (!this.santaGiftList.contains(newGift)) {
                this.santaGiftList.add(new Gift(newGift));
            }
        }
    }

    /**
     * Method used for updating information for specific children
     * @param childrenUpdates the list of updated available for the childrens
     */
    public void updateSpecificChildren(final List<ChildUpdate> childrenUpdates) {

        // iterate in the list of updates in order to perform them
        for (ChildUpdate childUpdate : childrenUpdates) {
            Integer id = childUpdate.getId();

            // iterate in the list of children and search the child with specific id
            for (ChildOutput child : children) {

                // found the child with the specific id
                if (child.getId().equals(id)) {

                    // update the elf for the specific child
                    child.setElf(childUpdate.getElf());

                    // add the new nice score in the history of the child
                    Double niceScore = childUpdate.getNiceScore();
                    if (niceScore != null) {
                        child.addNiceScoreHistory(niceScore);
                    }

                    // add the new preference for the current round
                    List<Category> newGiftsPreferences = childUpdate.getGiftsPreferences();
                    Collections.reverse(newGiftsPreferences);

                    List<Category> oldGiftsPreferences = child.getGiftsPreferences();

                    // iterate in the new list of preference
                    for (Category category : newGiftsPreferences) {

                        // the preference is already in the old preference list, remove it
                        if (oldGiftsPreferences.contains(category)) {
                            child.removePreference(category);
                        }
                        // new preference was added one the first position in the preference list
                        child.addPreference(category);
                    }
                }
            }
        }
    }

    /**
     * Method used for updating the santa budget for the current year
     * @param newSantaBudget the value to update the santaBudget
     */
    public void updateBudget(final Double newSantaBudget) {
        this.santaBudget = newSantaBudget;
    }

    /**
     * Method used for updating the budget unit for the current year
     */
    public void updateBudgetUnit() {
        computeBudgetUnit();
    }

    /**
     * Method used for computing the budget unit for the current year
     */
    public void computeBudgetUnit() {
        Double totalScore = 0.0;
        for (ChildOutput child : children) {
            Double averageScore = child.getAverageScore();
            totalScore += averageScore;
        }
        if (totalScore != 0) {
            this.budgetUnit = this.santaBudget / totalScore;
        } else {
            this.budgetUnit = 0.0;
        }
    }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final InputData input) {
        this.santaBudget = input.getSantaBudget();
    }

    public List<ChildOutput> getChildren() {
        return children;
    }

    /**
     * Method used for setting database's list of children
     * Transform every child from childInputFormat to childOutputFormat
     * Added in the list only the children eligible to receive presents
     * @param input information to extract the list od childrem
     */
    public void setChildren(final InputData input) {
        List<ChildOutput> childrenOutputFormat = new ArrayList<>();
        List<ChildInput> childInputFormat = input.getInitialData().getChildren();

        for (ChildInput child : childInputFormat) {
            ChildOutput newChild = new ChildOutput(child);
            if (newChild.getAge() <= Constants.TEEN_AGE) {
                childrenOutputFormat.add(newChild);
                newChild.addNiceScoreHistory(child.getNiceScore());
            }
        }
        this.children = childrenOutputFormat;
    }
    public void setSantaGiftList(final InputData input) {
        this.santaGiftList = input.getInitialData().getSantaGiftsList();
    }

    public List<Gift> getSantaGiftList() {
        return santaGiftList;
    }

    /**
     * Method used for getting the santa budgetUnit
     * @return the budgetUnit of the santa
     */
    public Double getBudgetUnit() {
        computeBudgetUnit();
        return budgetUnit;
    }

    public CityStrategyEnum getStrategy() {
        return strategy;
    }

    public void setStrategy(final CityStrategyEnum strategy) {
        this.strategy = strategy;
    }
}
