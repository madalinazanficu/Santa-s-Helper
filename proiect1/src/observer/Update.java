package observer;

import dataprocessing.AnnualChange;

public interface Update {
    public void update(final AnnualChange annualChangeData);
}
