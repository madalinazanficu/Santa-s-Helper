package observer;

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
import strategy.averagescore.AverageScoreStrategy;
import strategy.averagescore.StrategyFactory;
import strategy.sorting.SortingFactory;
import strategy.sorting.SortingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
     * Set the list of annual changes
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
     * Repeat 1. 2. and 3. for numberOfYears rounds and update Santa information every year.
     * Finally, add the history list in the outputFormat database.
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
     * Solving the current round
     * The main idea is to compute the average score and the assigned budget for every child
     * Applied different strategies based on the child age to compute the average score.
     * Applied different strategies of sorting the children in order to share gifts
     */
    public void solveRound() {
        List<ChildOutput> children = SantaClaus.getInstance().getChildren();
        // compute the averageScore for every child
        for (ChildOutput child : children) {
            // applied an age based strategy in order to compute the average score for every child
            AverageScoreStrategy strategy = StrategyFactory.createStrategy(child);
            if (strategy != null) {
                strategy.computeAverageScore();
            }
        }

        // Compute the budget for every child
        for (ChildOutput child : children) {
            // reset the child gifts list for the current round
            child.setReceivedGifts(new ArrayList<>());
            child.computeBudget();
        }

        // apply the required strategy of sorting children' list in order to distribute gifts
        applyStrategy();
    }

    /**
     * Apply different strategies of sorting children, based on strategy field from SantaClaus
     * For the Round 0, the default strategy is sorting by ID
     * Used distribute gift method to share the desired gifts according to the assigned budget.
     * Used yellowElf method to check the existence of the yellow elf and to assign extra gifts
     */
    public void applyStrategy() {
        List<ChildOutput> children = SantaClaus.getInstance().getChildren();
        if (SantaClaus.getInstance().getStrategy() != null) {
            SortingStrategy giftsStrategy = SortingFactory.createStrategy(
                                            SantaClaus.getInstance().getStrategy(),
                                            SantaClaus.getInstance().getChildren());
            if (giftsStrategy != null) {
                giftsStrategy.sortChildrenList();
            } else {
                System.out.println("Error: Strategy was not generated!");
            }
        }
        // distribute the gifts to every child
        for (ChildOutput child : children) {
            distributeGifts(child);
        }
        // apply yellow elf technique for lucky children
        yellowElf();

        // sort the list of children after receiving the gifts in order to print them
        Collections.sort(children, Comparator.comparing(ChildOutput::getId));
    }

    /**
     * Distribute the gifts for a specific child
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

        Collections.sort(santaGiftList, Comparator.comparing(Gift::getPrice));

        // iterate in child's preference list
        for (Category category : giftsPreferences) {
            Gift cheapestGift = null;

            // search the cheapest gift from the desired category of the child that santa has
            // the gift should be also available
            for (Gift gift : santaGiftList) {
                if (gift.getQuantity() > 0 && gift.getCategory().equals(category)) {
                        cheapestGift = gift;
                        break;
                }
            }
            // decreased the child budget and the quantity of the gift
            if (cheapestGift != null && cheapestGift.getPrice() < childBudget) {
                childBudget -= cheapestGift.getPrice();
                child.addReceivedGift(cheapestGift);
                cheapestGift.setQuantity(cheapestGift.getQuantity() - 1);
            }
        }
    }

    /**
     * Search the yellowElf in children list
     * 1. If a child have a yellow elf
     * 2. And the child didn't receive any gifts
     * The child will receive the cheapest gift from his preferred category
     * Only if the gift is still available
     */
    public void yellowElf() {
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
