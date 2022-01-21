package inputfiles;

import enums.Category;
import enums.ElvesType;

import java.util.List;

public final class ChildUpdate {
    // every child has an id
    private Integer id;
    // brand new nice score
    private Double niceScore;
    // new wishlist
    private List<Category> giftsPreferences;
    // new elf
    private ElvesType elf;

    public ChildUpdate() {
        this.id = 0;
        this.niceScore = 0.0;
        this.giftsPreferences = null;
        this.elf = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
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

    public ElvesType getElf() {
        return elf;
    }

    public void setElf(final ElvesType elf) {
        this.elf = elf;
    }
}
