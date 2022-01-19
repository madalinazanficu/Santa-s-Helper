package observer;

import common.Constants;
import enums.ElvesType;
import inputfiles.InputAnnualChange;
import inputfiles.Gift;
import inputfiles.InputData;

import outputfiles.ChildOutput;
import outputfiles.AnnualChange;
import outputfiles.OutputFormat;
import outputfiles.AnnualChildrenFormat;
import outputfiles.ChildrenOutputFormat;

import enums.Category;
import strategy.AverageScoreStrategy;
import strategy.StrategyFactory;
import strategy.assignGifts.AssignGiftsFactory;
import strategy.assignGifts.AssignGiftsStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

public final class Solver {

    // the number of rounds
    private int numberOfYears;

    // the list of annualChanges
    private List<AnnualChange> annualChanges;


    public Solver() {
        this.numberOfYears = 0;
        this.annualChanges = null;
    }
    public void setNumberOfYears(final InputData inputData) {
        this.numberOfYears = inputData.getNumberOfYears();
    }

    /**
     * Method used for setting the list of annual changes
     * Convert the inputAnnualChange to AnnualChange format
     * @param inputData to extract information
     */
    public void setAnnualChanges(final InputData inputData) {

        // Convert from inputAnnualChange in annualChange
        List<AnnualChange> annualChangesList = new ArrayList<>();
        List<InputAnnualChange> inputAnnualChanges = inputData.getAnnualChanges();

        for (InputAnnualChange inputAnnualChange : inputAnnualChanges) {
            AnnualChange newAnnualChange = new AnnualChange(inputAnnualChange);
            annualChangesList.add(newAnnualChange);
        }
        this.annualChanges = annualChangesList;
    }


    /**
     * Writing the result data for every round of sharing gifts.
     * 1. Call the solveRound method to solve the first round.
     * 2. Set the list of children that received gifts for the first round.
     * 3. Add the list of the first round in history list that maintain data for all the rounds.
     * Repet 1. 2. and 3. for numberOfYears rounds and update Santa information every year.
     * Finally add the history list in the outputFormat database.
     */
    public void solve() {
        // the output format required
        OutputFormat outputFormat = OutputFormat.getInstance();
        AnnualChildrenFormat annualChildrenFormat = new AnnualChildrenFormat();
        ChildrenOutputFormat childrenOutputFormat = new ChildrenOutputFormat();

        // solve the first round
        solveRound();

        // set the list of children that received gifts in this round
        List<ChildOutput> children = SantaClaus.getInstance().getChildren();
        childrenOutputFormat.setChildrenOutputList(children);

        // keep the current list of children in another list
        // that maintain information for numberOfYears rounds
        annualChildrenFormat.addNewRoundArray(childrenOutputFormat);

        // clear the current list of children to execute the next rounds
        childrenOutputFormat.clear();

        SantaClaus santaClaus = SantaClaus.getInstance();
        for (int i = 0; i < this.numberOfYears; i++) {
            AnnualChange annualChange = annualChanges.get(i);

            // update Santa Claus database information to execute the current round
            santaClaus.update(annualChange);

            // solve the current round
            solveRound();

            // write the result in the output format required
            List<ChildOutput> children1 = SantaClaus.getInstance().getChildren();
            childrenOutputFormat.setChildrenOutputList(children);
            annualChildrenFormat.addNewRoundArray(childrenOutputFormat);
            childrenOutputFormat.clear();
        }
        outputFormat.setAnnualChildrenFormat(annualChildrenFormat);
    }

    /**
     * Method used for solving the current round
     * The main idea is to compute the average score and the assigned budget for every child
     * Applied different strategies based on the child age to compute the average score.
     * Used distribute gift method to share the desired gifts according to the assigned budget.
     */
    public void solveRound() {

        List<ChildOutput> children = SantaClaus.getInstance().getChildren();
        // iterate in the list of children and check the age
        for (ChildOutput child : children) {
            // applied an age based strategy in order to compute the average score for every child
            AverageScoreStrategy strategy = StrategyFactory.createStrategy(child);
            if (strategy != null) {
                strategy.computeAverageScore();
            }
        }

        // Compute the budget for every child
        // Taking into consideration the BLACK AND PINK elf changes

        for (ChildOutput child : children) {
            // reset the child gifts list for the current round
            child.setReceivedGifts(new ArrayList<>());

            // compute the budget that santa allocated for the current child
            Double budgetUnit = SantaClaus.getInstance().getBudgetUnit();

            Double childBudget = child.getAverageScore() * budgetUnit;

            // depending on each child's elf, the budget could be modified
            if (child.getElf().equals(ElvesType.BLACK)) {
                childBudget = childBudget - childBudget * Constants.PERCENTAGE / Constants.PERCENT;
            }
            if (child.getElf().equals(ElvesType.PINK)) {
                childBudget = childBudget + childBudget * Constants.PERCENTAGE / Constants.PERCENT;
            }

            // assigned the computed budget to the child
            child.setAssignedBudget(childBudget);
        }

        // apply the required strategy of sharing gifts
        applyStrategy();
    }
    public void applyStrategy() {
        // For the Round 0 => apply no strategy
        // Otherwise apply the strategy in order to sort the list of children
        List<ChildOutput> sortedChildren = SantaClaus.getInstance().getChildren();
        if (SantaClaus.getInstance().getStrategy() != null) {

            AssignGiftsStrategy giftsStrategy = AssignGiftsFactory.createStrategy
                                                (SantaClaus.getInstance().getStrategy(),
                                                 SantaClaus.getInstance().getChildren());

            if (giftsStrategy != null) {
                sortedChildren = giftsStrategy.sortChildrenList();
            } else {
                System.out.println("Error: Strategy was not generated!");
            }

        }
        // distribute the gifts to every child
        for (ChildOutput child : sortedChildren) {
            distributeGifts(child);
        }
        // apply yellow elf technique for lucky children
        YellowElf();

        // sort the list of children after receiving the gifts in order to print them
        Collections.sort(sortedChildren, Comparator.comparing(ChildOutput::getId));
    }

    /**
     * Method used for distribute the gifts for a specific child
     * The main idea is to find the cheapest gifts available from a desired category
     * Sort the available gifts of the santa depending on the price
     * Iterate in child's preference list and search the cheapest gift available
     * If the budget permits add that gift in the child list of received gifts
     * @param child the current child which will receive gifts
     */
    public void distributeGifts(final ChildOutput child) {
        List<Gift> santaGiftList = SantaClaus.getInstance().getSantaGiftList();
        List<Category> giftsPreferences = child.getGiftsPreferences();
        Double childBudget = child.getAssignedBudget();

//        List<Gift> sortedSantaGiftList = new ArrayList<>();
//        for (Gift gift : santaGiftList) {
//            sortedSantaGiftList.add(new Gift(gift));
//        }

//        // sort the list of gifts available depending on the price
//        sortedSantaGiftList.sort(Comparator.comparing(Gift::getPrice));
        Collections.sort(santaGiftList, Comparator.comparing(Gift::getPrice));

        // iterate in child's preference list
        for (Category category : giftsPreferences) {
            Gift cheapestGift = null;

            // search the cheapest gift from the desired category of the child that santa has
            for (Gift gift : santaGiftList) {
                if (gift.getQuantity() > 0) {
                    if (gift.getCategory().equals(category)) {
                        cheapestGift = gift;
                        break;
                    }
                }
            }
            // decreased the child budget
            if (cheapestGift != null && cheapestGift.getPrice() < childBudget) {
                childBudget -= cheapestGift.getPrice();
                child.addReceivedGift(cheapestGift);
                cheapestGift.setQuantity(cheapestGift.getQuantity() - 1);
            }
        }
    }
    public void YellowElf() {
        List<ChildOutput> children = SantaClaus.getInstance().getChildren();
        List<Gift> gifts = SantaClaus.getInstance().getSantaGiftList();

        // sort Santa's gift list in increasing order after price criteria
        Collections.sort(gifts, Comparator.comparing(Gift::getPrice));

        for (ChildOutput child : children) {
            // the child has a yellow elf and didn't receive any present
            if (child.getElf().equals(ElvesType.YELLOW) && child.getReceivedGifts().size() == 0) {
                Category category = child.getGiftsPreferences().get(0);

                // search the cheapest available present from the preferred category
                Gift cheapestGift = null;
                for (Gift gift : gifts) {
                    if (gift.getCategory().equals(category)) {
                        cheapestGift = gift;
                        break;
                    }
                }
                if (cheapestGift != null && cheapestGift.getQuantity() > 0) {
                    child.addReceivedGift(cheapestGift);
                    cheapestGift.setQuantity(cheapestGift.getQuantity() - 1);
                }
            }
        }
    }
}
