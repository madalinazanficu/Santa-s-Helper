package inputfiles;

import enums.Category;
import enums.Cities;
import enums.ElvesType;

import java.util.List;

public final class ChildInput {
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

    // every child has a goodness score
    private Double niceScore;

    // every child has a list of wishes
    private List<Category> giftsPreferences;

    // every child has a bonus nice score => not changeable in rounds
    private Double niceScoreBonus;

    // every child has an elf => changeable in rounds
    private ElvesType elf;

    public ChildInput() {
        this.id = id;
        this.lastName = null;
        this.firstName = null;
        this.age = 0;
        this.city = null;
        this.niceScore = null;
        this.giftsPreferences = null;
        this.niceScoreBonus = 0.0;
        this.elf = null;
    }
    public ChildInput(final ChildInput newChild) {
        this.id = newChild.getId();
        this.lastName = newChild.getLastName();
        this.firstName = newChild.getFirstName();
        this.age = newChild.getAge();
        this.city = newChild.getCity();
        this.niceScore = newChild.getNiceScore();
        this.giftsPreferences = newChild.getGiftsPreferences();
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

    public Double getNiceScoreBonus() {
        return niceScoreBonus;
    }

    public void setNiceScoreBonus(final Double niceScoreBonus) {
        this.niceScoreBonus = niceScoreBonus;
    }

    public ElvesType getElf() {
        return elf;
    }

    public void setElf(final ElvesType elf) {
        this.elf = elf;
    }

    @Override
    public String toString() {
        return "ChildInput{"
                + "id=" + id
                + ", lastName='" + lastName + '\''
                + ", firstName='" + firstName + '\''
                + ", age=" + age
                + ", city=" + city
                + ", niceScore=" + niceScore
                + ", giftsPreferences=" + giftsPreferences
                + ", niceScoreBonus=" + niceScoreBonus
                + ", elf=" + elf
                + '}';
    }
}
