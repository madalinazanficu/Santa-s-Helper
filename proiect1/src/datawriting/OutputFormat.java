package datawriting;

import java.util.ArrayList;
import java.util.List;


// Informatii ce trebuie scrise la output
// la fiecare runda se va adauga in final Result, o noua lista de copii
public final class OutputFormat {

    private AnnualChildrenFormat annualChildrenFormat;
    private static OutputFormat instance = null;

    public static OutputFormat getInstance() {
        if (instance == null) {
            instance = new OutputFormat();
        }
        return instance;
    }
    public void clear() {
        annualChildrenFormat = null;
    }

    private OutputFormat() {
    }

    public AnnualChildrenFormat getAnnualChildrenFormat() {
        return annualChildrenFormat;
    }

    public void setAnnualChildrenFormat(AnnualChildrenFormat annualChildrenFormat) {
        this.annualChildrenFormat = annualChildrenFormat;
    }

}
