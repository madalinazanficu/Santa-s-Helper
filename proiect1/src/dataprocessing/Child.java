package dataprocessing;

import enums.Category;
import enums.Cities;

import java.util.ArrayList;
import java.util.List;

public final class Child {
    // every child has an id
    private Integer id;

    // every child has lastName
    private String lastName;

    // every child has firstName
    private String firstName;

    // every child has age
    private int age;

    // every child is from a city
    private Cities city;

    // every child is from a goodness score
    private Double niceScore;

    // every child has a list of wishes
    private List<Category> giftsPreferences;

    // every child has an averageScore
    private Double averageScore;

    // every child has a niceScoreHistory - nice score
    private List<Double> niceScoreHistory;

    // every child has an assigned budget for presents - at round i
    private Double assignedBudget;

    // every child has a list of received gifts at round i
    private List<Gift> receivedGifts;


    public Child() {
        this.id = id;
        this.lastName = null;
        this.firstName = null;
        this.age = 0;
        this.city = null;
        this.niceScore = null;
        this.giftsPreferences = null;
        this.averageScore = 0.0;
        this.niceScoreHistory = new ArrayList<>();
        this.assignedBudget = null;
        this.receivedGifts = new ArrayList<>();
    }

    // COPY-CONSTRCTOR
    public Child(final Child newChild) {
        this.id = newChild.getId();
        this.lastName = newChild.getLastName();
        this.firstName = newChild.getFirstName();
        this.age = newChild.getAge();
        this.city = newChild.getCity();
        this.niceScore = newChild.getNiceScore();
        this.giftsPreferences = newChild.getGiftsPreferences();
        this.averageScore = newChild.getAverageScore();
        this.niceScoreHistory = newChild.getNiceScoreHistory();
        this.assignedBudget = newChild.getAssignedBudget();
        this.receivedGifts = newChild.getReceivedGifts();
        this.niceScoreHistory.add(niceScore);
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

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public Cities getCity() {
        return city;
    }

    public void setCity(final Cities city) {
        this.city = city;
    }

    public Double getNiceScore() {
        return niceScore;
    }

    public void setNiceScore(final Double niceScore) {
        this.niceScore = niceScore;
    }

    public List<Category> getGiftsPreferences() {
        return giftsPreferences;
    }

    public void setGiftsPreferences(final List<Category> giftsPreferences) {
        this.giftsPreferences = giftsPreferences;
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

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(final Double averageScore) {
        this.averageScore = averageScore;
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
        return "Child{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", age=" + age +
                ", city=" + city +
                ", niceScore=" + niceScore +
                ", giftsPreferences=" + giftsPreferences +
                ", averageScore=" + averageScore +
                ", niceScoreHistory=" + niceScoreHistory +
                ", assignedBudget=" + assignedBudget +
                ", receivedGifts=" + receivedGifts +
                '}';
    }
}
