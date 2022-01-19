package strategy.assignGifts;

import outputfiles.ChildOutput;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class NiceScoreCityStrategy implements AssignGiftsStrategy {
    private List<ChildOutput> childrenList;

    public NiceScoreCityStrategy(final List<ChildOutput> childrenList) {
        this.childrenList = childrenList;
    }

    /**
     * @return
     */
    @Override
    public List<ChildOutput> sortChildrenList() {
        // sort the list of children in increasing order by id
        Collections.sort(childrenList, Comparator.comparing(ChildOutput::getId));

        // map every city with all the children that live in the city
        HashMap<String, List<ChildOutput>> townsChildren = new HashMap<>();

        for (ChildOutput child : childrenList) {
            String town = child.getCity().toString();

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

        // map every city with the sum score of all their children
        HashMap<String, Double> townsScore = new HashMap<>();

        Iterator it = townsChildren.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry mapElement = (Map.Entry) it.next();
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

        // imi transform hashmap-ul intr-o lista pentru a o putea sorta
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(
                                                townsScore.entrySet());


        // sortez map-ul in functie de average score / iar daca au average scor egal
        // => sortez lexicografic dupa numele oraselor
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

        List<ChildOutput> sortedList = new ArrayList<>();
        // FORMEZ LISTA SORTATA DE COPII PE BAZA MAPULUI SORTAT DUPA AVERAGE SCORE
        for (Map.Entry<String, Double> element : list) {
            String town = element.getKey();
            List<ChildOutput> children = townsChildren.get(town);
            sortedList.addAll(children);
        }

        childrenList.clear();
        childrenList.addAll(sortedList);
        return childrenList;
    }

}
