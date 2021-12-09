package dataprocessing;

import enums.Category;

import java.util.List;

public final class ChildUpdate {
    // every child has an id / use this to detect the changes for a child
    private Integer id;
    // brand new nice score
    private Double niceScore;
    // new wish list
    private List<Category> giftsPreferences;

    public ChildUpdate() {
        this.id = 0;
        this.niceScore = 0.0;
        this.giftsPreferences = null;
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
}
