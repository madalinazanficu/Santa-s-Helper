package observer;

import outputfiles.AnnualChange;

public interface Update {
    /**
     * @param annualChangeData the annual updated data
     */
    void update(AnnualChange annualChangeData);
}
