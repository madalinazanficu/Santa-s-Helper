package main;

import dataprocessing.InputAnnualChange;
import dataprocessing.Gift;
import dataprocessing.InputData;
import datawriting.*;
import enums.Category;
import observer.SantaClaus;
import strategy.AverageScoreStrategy;
import strategy.StrategyFactory;

import java.util.*;

public final class Solver {

    // the number of rounds
    private int numberOfYears;

    // the list of annualChanges
    private List<AnnualChange> annualChanges;

    public Solver() {
        this.numberOfYears = 0;
        this.annualChanges = null;
    }
    public void setNumberOfYears(InputData inputData) {
        this.numberOfYears = inputData.getNumberOfYears();
    }
    public void setAnnualChanges(InputData inputData) {

        // Convert from inputAnnualChange in annualChange
        List<AnnualChange> annualChanges = new ArrayList<>();
        List<InputAnnualChange> inputAnnualChanges = inputData.getAnnualChanges();

        for (InputAnnualChange inputAnnualChange : inputAnnualChanges) {
            AnnualChange newAnnualChange = new AnnualChange(inputAnnualChange);
            annualChanges.add(newAnnualChange);
        }
        this.annualChanges = annualChanges;
    }


    public void solve() {
        // formatul final de output
        OutputFormat outputFormat = OutputFormat.getInstance();
        AnnualChildrenFormat annualChildrenFormat = new AnnualChildrenFormat();
        ChildrenOutputFormat childrenOutputFormat = new ChildrenOutputFormat();

        solveRound();
        List<ChildOutput> children = SantaClaus.getInstance().getChildren();

        // se adauga copii intr-o clasa ce contine lista de copii
        childrenOutputFormat.setChildrenOutputList(children);

        // se adauga aceasta lista in AnnualChildrenFormat
        annualChildrenFormat.addNewRoundArray(childrenOutputFormat);

        // pentru fiecare an sterg copii vechi din lista
        childrenOutputFormat.clear();

        SantaClaus santaClaus = SantaClaus.getInstance();
        // update pe Mos Craciun la fiecare runda
        for (int i = 0; i < this.numberOfYears; i++) {

            AnnualChange annualChange = annualChanges.get(i);
            santaClaus.update(annualChange);
            solveRound();

            List<ChildOutput> children1 = SantaClaus.getInstance().getChildren();

            // se adauga copii intr-o clasa ce contine lista de copii
            childrenOutputFormat.setChildrenOutputList(children);

            // se adauga aceasta lista in AnnualChildrenFormat
            annualChildrenFormat.addNewRoundArray(childrenOutputFormat);

            // pentru fiecare an sterg copii vechi din lista
            childrenOutputFormat.clear();
        }
        outputFormat.setAnnualChildrenFormat(annualChildrenFormat);
    }

    public void solveRound() {
        // se itereaza prin lista de copii si de determina varsta
        List<ChildOutput> children = SantaClaus.getInstance().getChildren();
        for (ChildOutput child : children) {
            // pentru fiecare copil, caut o stratgie pentru a calcula average score
            AverageScoreStrategy strategy = StrategyFactory.createStrategy(child);
            if (strategy != null) {
                strategy.computeAverageScore();
            }
        }

        for (ChildOutput child : children) {
            // setarea unor noi caoduri pentru o noua runda
            child.setReceivedGifts(new ArrayList<>());

            // calculez bugetul pe care mosul il aloca pentru copilul respectiv
            Double budgetUnit = SantaClaus.getInstance().getBudgetUnit();
            Double childBudget = child.getAverageScore() * budgetUnit;

            // setez bugetul copilului
            child.setAssignedBudget(childBudget);

            // se distribuie cadourile pentru fiecare copil
            distributeGifts(child);
        }
    }

    public void distributeGifts(final ChildOutput child) {
        List<Gift> santaGiftList = SantaClaus.getInstance().getSantaGiftList();
        List<Category> giftsPreferences = child.getGiftsPreferences();
        Double childBudget = child.getAssignedBudget();

        // lista sortata a mosului, in functie de pret
        List<Gift> sortedSantaGiftList = new ArrayList<>();
        for (Gift gift : santaGiftList) {
            sortedSantaGiftList.add(new Gift(gift));
        }
        Collections.sort(sortedSantaGiftList, Comparator.comparing(Gift::getPrice));


        // se itereaza prin preferintele copilului
        for (Category category : giftsPreferences) {
            Gift cheapestGift = null;
            for (Gift gift : sortedSantaGiftList) {
                if (gift.getCategory().equals(category)) {
                        cheapestGift = gift;
                        break;
                }
            }
            // daca mai avem bani si de acest cadou => il luam si scadem bugetul alocat
            if (cheapestGift != null && cheapestGift.getPrice() < childBudget) {
                childBudget -= cheapestGift.getPrice();
                child.addReceivedGift(cheapestGift);
            }
        }
    }
}
