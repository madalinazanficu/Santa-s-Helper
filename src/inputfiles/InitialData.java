package inputfiles;
import enums.Cities;

import java.util.List;

public final class InitialData {
    private List<ChildInput> children;
    private List<Gift> santaGiftsList;
    private List<Cities> citiesList;

    public InitialData() {
        this.children = null;
        this.santaGiftsList = null;
    }

    public List<ChildInput> getChildren() {
        return children;
    }

    public void setChildren(final List<ChildInput> children) {
        this.children = children;
    }

    public List<Gift> getSantaGiftsList() {
        return santaGiftsList;
    }

    public void setSantaGiftsList(final List<Gift> santaGiftsList) {
        this.santaGiftsList = santaGiftsList;
    }
}
