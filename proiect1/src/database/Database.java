package database;

import dataprocessing.AnnualChange;
import enums.Cities;
import java.util.List;

// Not required updates
public final class Database {

    private static Database instance = null;

    // the number of rounds
    private int numberOfYears;

    // the list of cities
    private List<Cities> citiesList;

    // the list of annualChanges
    private List<AnnualChange> annualChanges;


    /**
     * @return the instance of the Singleton class
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    // construct the database for unmodified information
    private Database() {
        this.numberOfYears = 0;
        this.citiesList = null;
        this.annualChanges = null;
    }

    /**
     * Clear the Database information
     */
    // clear the database
    public void clearDatabase() {
        this.numberOfYears = 0;
        this.citiesList = null;
        this.annualChanges = null;
    }

    public int getNumberOfYears() {
        return numberOfYears;
    }

    public void setNumberOfYears(final int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    public List<Cities> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(final List<Cities> citiesList) {
        this.citiesList = citiesList;
    }

    public List<AnnualChange> getAnnualChanges() {
        return annualChanges;
    }

    public void setAnnualChanges(final List<AnnualChange> annualChanges) {
        this.annualChanges = annualChanges;
    }
}
