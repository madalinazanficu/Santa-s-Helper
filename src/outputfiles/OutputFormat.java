package outputfiles;

/**
 * Database that maintain the information requested at output
 * The AnnualChildrenFormat keeps numberOfYears lists of children
 */
public final class OutputFormat {

    private AnnualChildrenFormat annualChildrenFormat;
    private static OutputFormat instance = null;

    private OutputFormat() { }

    /**
     * @return the instance of the database
     */
    public static OutputFormat getInstance() {
        if (instance == null) {
            instance = new OutputFormat();
        }
        return instance;
    }

    /**
     * Method used for clearing the database for every test case
     */
    public void clear() {
        annualChildrenFormat = null;
    }

    public AnnualChildrenFormat getAnnualChildrenFormat() {
        return annualChildrenFormat;
    }

    public void setAnnualChildrenFormat(final AnnualChildrenFormat annualChildrenFormat) {
        this.annualChildrenFormat = annualChildrenFormat;
    }

}
