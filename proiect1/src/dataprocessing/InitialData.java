package dataprocessing;
import java.util.List;

public final class InitialData {
    private List<Child> children;
    private List<Gift> santaGiftsList;
    // List<Cities> citiesList;

    public InitialData() {
        this.children = null;
        this.santaGiftsList = null;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(final List<Child> children) {
        this.children = children;
    }

    public List<Gift> getSantaGiftsList() {
        return santaGiftsList;
    }

    public void setSantaGiftsList(final List<Gift> santaGiftsList) {
        this.santaGiftsList = santaGiftsList;
    }
}
