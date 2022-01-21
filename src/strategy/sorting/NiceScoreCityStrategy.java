package strategy.sorting;

import outputfiles.ChildOutput;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * The main idea of the implementation is:
 * 1. Map all the cities with the children that live in
 * 2. Compute the average score of every city
 * 3. Map the cities with their averageScore
 * 4. Sort the hashmap of cities in decreasing order after averageScore criteria
 * 5. Sort the final list of the children based on the sorted hashmap.
 */
public class NiceScoreCityStrategy implements SortingStrategy {

    private final List<ChildOutput> childrenList;
    private final HashMap<String, List<ChildOutput>> townsChildren;
    private final HashMap<String, Double> townsScore;


    public NiceScoreCityStrategy(final List<ChildOutput> childrenList) {
        this.childrenList = childrenList;
        this.townsChildren = new HashMap<>();
        this.townsScore = new HashMap<>();
    }


    /**
     * Map all the children with their birth city
     * @param town the key of the map
     * @param child the value that will be added at key entry
     */
    public void mapChildrenToCity(final String town, final ChildOutput child) {
        // in case the town is already present in map, add a new children at the entry
        if (townsChildren.containsKey(town)) {
            List<ChildOutput> entry = townsChildren.get(town);
            entry.add(child);

        } else {
            // otherwise, create a new entry in the map
            List<ChildOutput> entry = new ArrayList<>();
            entry.add(child);
            townsChildren.put(town, entry);
        }
    }

    /**
     *  Used the townChildren map in order to compute the averageScore of the city
     *  Map every city with the averageScore computed
     */
    public void computeTownScore() {
        //  iterate in townsChildren to make the averageScore of the town
        for (Map.Entry<String, List<ChildOutput>> stringListEntry : townsChildren.entrySet()) {
            Map.Entry mapElement = (Map.Entry) stringListEntry;
            String city = (String) mapElement.getKey();
            List<ChildOutput> specificChildren = (List<ChildOutput>) mapElement.getValue();

            // compute the sum of all children from the specific town
            AtomicReference<Double> sum = new AtomicReference<>(0.0);
            Consumer<ChildOutput> computeSum = t -> sum.updateAndGet(v -> v + t.getAverageScore());
            specificChildren.stream().forEach(computeSum);

            // the average score of children from the specific town
            Double average = sum.get() / specificChildren.size();

            townsScore.put(city, average);
        }
    }

    /**
     * Sorted the map of cities after the averageScore criteria and form the list of children
     * In case 2 cities have the same averageScore, the criteria of sorting is the name
     * All the children from a specific city will be added in the sorted list one after other
     * The order of the children from the same city is increasing order of the id field
     */
    @Override
    public void sortChildrenList() {
        // sort the list of children in increasing order by id
        Collections.sort(childrenList, Comparator.comparing(ChildOutput::getId));

        // map every city with all the children that live in the city
        Consumer<ChildOutput> addToMap = t -> mapChildrenToCity(t.getCity().toString(), t);
        childrenList.stream().forEach(addToMap);

        // compute the average score for all the towns
        computeTownScore();

        // transform the (town, averageScore) hashmap into a list
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(
                                                townsScore.entrySet());

        // sort the list of pairs after averageSCore criteria and cityName criteria
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(final Map.Entry<String, Double> o1,
                               final Map.Entry<String, Double> o2) {
                double result = o2.getValue() - o1.getValue();
                if (result > 0) {
                    return 1;
                }
                if (result == 0) {
                    return o1.getKey().compareTo(o2.getKey());
                }
                return -1;
            }
        });

        // form the sorted list of children based on the sorted averageScore map
        childrenList.clear();
        for (Map.Entry<String, Double> element : list) {
            String town = element.getKey();
            List<ChildOutput> children = townsChildren.get(town);
            childrenList.addAll(children);
        }
    }

}
