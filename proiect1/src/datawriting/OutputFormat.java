package datawriting;

import dataprocessing.Child;

import java.util.ArrayList;
import java.util.List;


// Informatii ce trebuie scrise la ooutput
public final class OutputFormat {

    private List<ArrayList<Child>> finalResult = new ArrayList<ArrayList<Child>>();
    private static OutputFormat instance = null;

//    public OutputFormat(final List<Child> children) {
//        this.children = children;
//    }
//
//    public List<Child> getChildren() {
//        return children;
//    }
//
//    public void setChildren(final List<Child> children) {
//        this.children = children;
//    }
    public static OutputFormat getInstance() {
        if (instance == null) {
            instance = new OutputFormat();
        }
        return instance;
    }
    public void clearOutputFormat() {
        finalResult.clear();
    }


    private OutputFormat() {
    }

    public void addNewRoundArray(final List<Child> children) {
        ArrayList<Child> c = new ArrayList<>();
        for (Child  child : children) {
            c.add(new Child(child));
        }
        finalResult.add(c);
    }

    public List<ArrayList<Child>> getFinalResult() {
        return finalResult;
    }

    @Override
    public String toString() {
        return "OutputFormat{" +
                "finalResult=" + finalResult +
                '}';
    }
}
