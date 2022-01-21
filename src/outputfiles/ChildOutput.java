package outputfiles;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import common.Constants;
import enums.ElvesType;
import inputfiles.ChildInput;
import inputfiles.Gift;
import enums.Category;
import enums.Cities;
import observer.SantaClaus;

import java.util.ArrayList;
import java.util.List;

public final class ChildOutput {
    private Integer id;
    private String lastName;
    private String firstName;
    private Cities city;
    private int age;
    private List<Category> giftsPreferences;
    private Double averageScore;
    private List<Double> niceScoreHistory;
    private Double assignedBudget;
    private List<Gift> receivedGifts;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Double niceScoreBonus;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ElvesType elf;


    public ChildOutput(final ChildOutput newChild) {
        this.id = newChild.getId();
        this.lastName = newChild.getLastName();
        this.firstName = newChild.getFirstName();
        this.age = newChild.getAge();
        this.city = newChild.getCity();

        // deep-copy for the list of gift preference of the child
        List<Category> newGiftPreference = newChild.getGiftsPreferences();
        this.giftsPreferences = new ArrayList<>();
        this.giftsPreferences.addAll(newGiftPreference);

        this.averageScore = newChild.getAverageScore();

        // deep-copy for the list of nice score history of the child
        List<Double> newNiceScoreHistory = newChild.getNiceScoreHistory();
        this.niceScoreHistory = new ArrayList<>();
        this.niceScoreHistory.addAll(newNiceScoreHistory);

        this.assignedBudget = newChild.getAssignedBudget();

        // deep-copy for the list of received gifts of the child
        List<Gift> newReceivedGifts = newChild.getReceivedGifts();
        this.receivedGifts = new ArrayList<>();
        this.receivedGifts.addAll(newReceivedGifts);

        this.niceScoreBonus = newChild.getNiceScoreBonus();
        this.elf = newChild.getElf();
    }

    public ChildOutput(final ChildInput newChild) {
        this.id = newChild.getId();
        this.lastName = newChild.getLastName();
        this.firstName = newChild.getFirstName();
        this.age = newChild.getAge();
        this.city = newChild.getCity();
        this.giftsPreferences = newChild.getGiftsPreferences();
        this.averageScore = newChild.getNiceScore();
        this.niceScoreHistory = new ArrayList<>();
        this.assignedBudget = 0.0;
        this.receivedGifts = new ArrayList<>();
        this.niceScoreBonus = newChild.getNiceScoreBonus();
        this.elf = newChild.getElf();
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(final Cities city) {
        this.city = city;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public void setGiftsPreferences(final List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(final Double averageScore) {
        this.averageScore = averageScore;
    }

    public List<Double> getNiceScoreHistory() {
        return niceScoreHistory;
    }

    public void setNiceScoreHistory(final List<Double> niceScoreHistory) {
        this.niceScoreHistory = niceScoreHistory;
    }

    public Double getAssignedBudget() {
        return assignedBudget;
    }

    public void setAssignedBudget(final Double assignedBudget) {
        this.assignedBudget = assignedBudget;
    }

    public List<Gift> getReceivedGifts() {
        return receivedGifts;
    }

    public void setReceivedGifts(final List<Gift> receivedGifts) {
        this.receivedGifts = receivedGifts;
    }

    /**
     * Method used for incrementing the age of the child every year
     */
    public void incrementAge() {
        this.age = this.age + 1;
    }


    /**
     * Method used for adding a new nice score at the history of nice scores
     * @param newNiceScore the brand new nice score needed to be added
     */
    public void addNiceScoreHistory(final Double newNiceScore) {
        this.niceScoreHistory.add(newNiceScore);
    }

    /**
     * Method used for removing a preference from the list of preferences of the child
     * @param preference the preference needed to be removed
     */
    public void removePreference(final Category preference) {
        this.giftsPreferences.remove(preference);
    }

    /** Method used for adding a new preference in the list of preferences of the child
     * @param preference the preference neede to be added
     */
    public void addPreference(final Category preference) {
        this.giftsPreferences.add(0, preference);
    }


    /**
     * Method user for adding a new gift in child's receivedGifts list
     * @param gift the gift needed to be added
     */
    public void addReceivedGift(final Gift gift) {
        this.receivedGifts.add(gift);
    }


    @Override
    public String toString() {
        return "ChildOutputFormat{"
                + "id=" + id
                + ", lastName='" + lastName + '\''
                + ", firstName='" + firstName + '\''
                + ", city=" + city
                + ", age=" + age
                + ", giftsPreferences=" + giftsPreferences
                + ", averageScore=" + averageScore
                + ", niceScoreHistory=" + niceScoreHistory
                + ", assignedBudget=" + assignedBudget
                + ", receivedGifts=" + receivedGifts
                + '}';
    }

    @JsonIgnore
    public Double getNiceScoreBonus() {
        return niceScoreBonus;
    }

    public void setNiceScoreBonus(final Double niceScoreBonus) {
        this.niceScoreBonus = niceScoreBonus;
    }

    @JsonIgnore
    public ElvesType getElf() {
        return elf;
    }

    public void setElf(final ElvesType elf) {
        this.elf = elf;
    }

    /**
     *  Compute the budget for every child
     *  Taking into consideration the BLACK AND PINK elf changes
     */
    public void computeBudget() {
        // compute the budget that santa allocated for the current child
        Double budgetUnit = SantaClaus.getInstance().getBudgetUnit();
        double childBudget = this.getAverageScore() * budgetUnit;
        // depending on each child's elf, the budget could be modified
        if (this.getElf().equals(ElvesType.BLACK)) {
            childBudget = childBudget - childBudget * Constants.PERCENTAGE / Constants.PERCENT;
        }
        if (this.getElf().equals(ElvesType.PINK)) {
            childBudget = childBudget + childBudget * Constants.PERCENTAGE / Constants.PERCENT;
        }
        // assigned the computed budget to the child
        this.setAssignedBudget(childBudget);
    }
}
