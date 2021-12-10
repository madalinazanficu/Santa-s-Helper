package main;

import dataprocessing.AnnualChange;
import dataprocessing.Child;
import dataprocessing.Gift;
import datawriting.OutputFormat;
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

    public Solver(final int numberOfYears, final List<AnnualChange> annualChanges) {
        this.numberOfYears = numberOfYears;
        this.annualChanges = annualChanges;
    }

    public void solve() {
        solveRound();
        List<Child> children = SantaClaus.getInstance().getChildren();

        OutputFormat.getInstance().addNewRoundArray(children);
        SantaClaus santaClaus = SantaClaus.getInstance();

        // update pe Mos Craciun la fiecare runda
        for (AnnualChange annualChange : annualChanges) {
            santaClaus.update(annualChange);
            solveRound();

            List<Child> children1 = SantaClaus.getInstance().getChildren();
            OutputFormat.getInstance().addNewRoundArray(children1);
        }
    }

    public void solveRound() {
        // se itereaza prin lista de copii si de determina varsta
        List<Child> children = SantaClaus.getInstance().getChildren();

        for (Child child : children) {
            // setarea unor noi caoduri pentru o noua runda
            child.setReceivedGifts(new ArrayList<>());

            // pentru fiecare copil, caut o stratgie pentru a calcula average score
            AverageScoreStrategy strategy = StrategyFactory.createStrategy(child);
            if (strategy != null) {

                strategy.computeAverageScore();

                // calculez bugetul pe care mosul il aloca pentru copilul respectiv
                Double budgetUnit = SantaClaus.getInstance().getBudgetUnit();
                Double childBudget = child.getAverageScore() * budgetUnit;

                // setez bugetul copilului
                child.setAssignedBudget(childBudget);

                // se distribuie cadourile pentru fiecare copil
                distributeGifts(child);

            }
        }
    }

    public void distributeGifts(Child child) {
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
