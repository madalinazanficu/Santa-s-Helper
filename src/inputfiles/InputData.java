package inputfiles;

import java.util.List;

public final class InputData {
    private int numberOfYears;
    private Double santaBudget;
    private InitialData initialData;
    private List<InputAnnualChange> annualChanges;

    public InputData() {
        this.numberOfYears = 0;
        this.santaBudget = 0.0;
        this.initialData = null;
        this.annualChanges = null;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(final int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public Double getSantaBudget() {
        return santaBudget;
    }

    public void setSantaBudget(final Double santaBudget) {
        this.santaBudget = santaBudget;
    }

    public InitialData getInitialData() {
        return initialData;
    }

    public void setInitialData(final InitialData initialData) {
        this.initialData = initialData;
    }

    public List<InputAnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(final List<InputAnnualChange> annualChanges) {
        this.annualChanges = annualChanges;
    }
}
