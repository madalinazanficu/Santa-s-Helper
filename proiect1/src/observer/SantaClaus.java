package observer;

import common.Constants;
import dataprocessing.AnnualChange;
import dataprocessing.Child;
import dataprocessing.ChildUpdate;
import dataprocessing.Gift;
import enums.Category;

import java.util.ArrayList;
import java.util.List;

// Require updates
public final class SantaClaus implements Update {
    private static SantaClaus instance = null;

    // the annual budget
    private Double santaBudget;

    // the budget unit
    private Double budgetUnit;

    private List<Child> children;

    // the list of gifts available
    private List<Gift> santaGiftList;

    public static SantaClaus getInstance() {
        if (instance == null) {
            instance = new SantaClaus();
        }
        return instance;
    }

    // construct the database for modified information
    private SantaClaus() {
        this.santaBudget = null;
        this.budgetUnit = null;
        this.children = new ArrayList<>();
        this.santaGiftList = new ArrayList<>();
    }

    // clear the database
    public void clearSantaClaus() {
        this.santaBudget = 0.0;
        this.budgetUnit = 0.0;
        this.children = null;
        this.santaGiftList = null;
    }

    /**
     * @param annualChange used to make the update for a specific year
     */
    @Override
    public void update(final AnnualChange annualChange) {
        updateChildrenList(annualChange.getNewChildren());
        updateSantaGiftList(annualChange.getNewGifts());
        updateSpecificChildren(annualChange.getChildrenUpdates());
        updateBudget(annualChange.getNewSantaBudget());
        updateBudgetUnit();
    }


    /**
     * Used for updating the list of children
     * Added the children with age <18
     * @param newChildren the new children needed to be added in the list
     */
    public void updateChildrenList(final List<Child> newChildren) {

        // cresc varsta copiilor cu 1 an
        for (Child child : children) {
            child.incrementAge();
        }

        // daca au peste 18 ani ii scot din lista
        children.removeIf(x->x.getAge() > Constants.TEEN_AGE);

        // adaug copii din noua lista <=> daca au sub 18 ani
        for (Child newChild : newChildren) {
            if (newChild.getAge() < 18) {
                Child myChild = new Child(newChild);
                myChild.getNiceScoreHistory().add(myChild.getNiceScore());
                this.children.add(myChild);
            }
        }
    }

    /**
     * Used for updating tge gifts list
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
    public void updateSpecificChildren(final List<ChildUpdate> childrenUpdates) {

        for (ChildUpdate childUpdate : childrenUpdates) {
            Integer id = childUpdate.getId();

            // iterez in lista de copii si caut copilul cu id-ul specific
            for (Child child : children) {

                // pe acest copil o sa fac update
                if (child.getId().equals(id)) {

                    // adaug in lista copilui de nice-score => noul nice score
                    Double niceScore = childUpdate.getNiceScore();
                    if (niceScore != null) {
                        child.addNiceScoreHistory(niceScore);
                    }
                    // se adauga noile preferinte pentru runda curenta
                    List<Category> newGiftsPreferences = childUpdate.getGiftsPreferences();
                    List<Category> oldGiftsPreferences = child.getGiftsPreferences();

                    // se itereaza prin lista noua de preferinte
                    for (Category category : newGiftsPreferences) {

                        // daca o preferinta a mai aparut, se sterge acea prefeinta si se adauga in fata
                        if (oldGiftsPreferences.contains(category)) {
                            child.removePreference(category);
                        }
                        // chiar daca o preferinta a mai aparut sau nu, ace preferinta va fi adaugata in fata
                        child.addPreference(category);
                    }
                }
            }
        }
    }

    /**
     * @param newSantaBudget the value to update the santaBudget
     */
    public void updateBudget(final Double newSantaBudget) {
        this.santaBudget = newSantaBudget;
    }
    public void updateBudgetUnit() {
        computeBudgetUnit();
    }

    public void computeBudgetUnit() {
        Double totalScore = 0.0;
        for (Child child : children) {
            Double averageScore = child.getAverageScore();
            totalScore += averageScore;
        }
        if (totalScore != 0) {
            this.budgetUnit = this.santaBudget / totalScore;
            //System.out.println("DA");
        } else {
            //System.out.println("The total score is 0 in Santa Claus!");
            this.budgetUnit = 0.0;
        }
    }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final Double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(final List<Child> children) {
        for (Child child : children) {
            child.getNiceScoreHistory().add(child.getNiceScore());
        }
        this.children = children;
    }

    public List<Gift> getSantaGiftList() {
        return santaGiftList;
    }

    public void setSantaGiftList(final List<Gift> santaGiftList) {
        this.santaGiftList = santaGiftList;
    }

    public Double getBudgetUnit() {
        computeBudgetUnit();
        return budgetUnit;
    }

    public void setBudgetUnit(final Double budgetUnit) {
        this.budgetUnit = budgetUnit;
    }
}
