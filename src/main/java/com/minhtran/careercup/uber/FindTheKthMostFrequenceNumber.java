package com.minhtran.careercup.uber;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

/**
 * Find the Kth most Frequent Number in an Array.
 * <p>
 * Example:
 * <p>
 * <p>
 * arr[] = {1, 2, 3, 2, 1, 2, 2, 2, 3}
 * k = 2
 * Result: 3
 * <p>
 * Because '3' is the second most occurring element.
 * Follow up: What if the array is extremely large?
 */
public class FindTheKthMostFrequenceNumber {
    private static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
        SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<>(
                (e1, e2) -> {
                    int res = -1 * e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    private static int findTheKthMostFrequestNumber1(int[] numbers, int theKthPosition) {
        final Map<Integer, Long> occurenceMap = new HashMap<>();
        for (int number : numbers) {
            Long element = occurenceMap.get(number);
            if (element != null) {
                occurenceMap.put(number, ++element);
            } else {
                occurenceMap.put(number, 1L);
            }
        }

        final ArrayList<Map.Entry<Integer, Long>> sortedNumbers = new ArrayList<>(entriesSortedByValues(occurenceMap));
        Map.Entry<Integer, Long> result = sortedNumbers.get(theKthPosition - 1);
        if (result != null) {
            return result.getKey();
        } else {
            return -1;
        }
    }

    private static int findTheKthMostFrequestNumber2(int[] numbers, int theKthPosition) {
        Map.Entry<String, Long> result = new ArrayList<>(
                IntStream.of(numbers).mapToObj(Integer::toString)
                        .collect(groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(
                                Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (oldValue, newValue) -> oldValue,
                                        LinkedHashMap::new
                                )
                        )
                        .entrySet()
                )
                .get(theKthPosition - 1);
        if (result != null) {
            return Integer.valueOf(result.getKey());
        } else {
            return -1;
        }
    }

    public static void main(String args[]) {
        int arr[] = {1, 2, 3, 2, 1, 2, 2, 2, 3};
        int k = 2;
        System.out.println(findTheKthMostFrequestNumber1(arr, k));
        System.out.println(findTheKthMostFrequestNumber2(arr, k));
    }
}
