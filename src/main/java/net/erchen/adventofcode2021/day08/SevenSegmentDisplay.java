package net.erchen.adventofcode2021.day08;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class SevenSegmentDisplay {

    public static long countUniqueResults(List<String> input) {
        return input.stream()
            .map(s -> s.split("\\|")[1])
            .flatMap(s -> Arrays.stream(s.split(" ")))
            .filter(s -> switch (s.length()) {
                case 2, 3, 4, 7 -> true;
                default -> false;
            }).count();
    }

    public static long sumOutputValues(List<String> input) {
        return input.stream()
            .map(s -> s.split("\\|"))
            .mapToInt(sa -> decode(sa[0].trim(), sa[1].trim()))
            .peek(System.out::println)
            .sum();
    }

    static int decode(String input, String output) {
        var inputs = splitToSet(input).collect(groupingBy(Set::size));

        Map<Integer, HashSet<String>> knownNumbers = new HashMap<>(10);
        knownNumbers.put(1, inputs.get(2).get(0));
        knownNumbers.put(7, inputs.get(3).get(0));
        knownNumbers.put(4, inputs.get(4).get(0));
        knownNumbers.put(8, inputs.get(7).get(0));

        knownNumbers.put(9, findDigit(inputs, knownNumbers, 6, s -> subtract(s, knownNumbers.get(4)).size() == 2));
        knownNumbers.put(0, findDigit(inputs, knownNumbers, 6, s -> subtract(s, knownNumbers.get(1)).size() == 4));
        knownNumbers.put(6, findDigit(inputs, knownNumbers, 6, s -> true));

        knownNumbers.put(3, findDigit(inputs, knownNumbers, 5, s -> s.containsAll(knownNumbers.get(1))));
        knownNumbers.put(2, findDigit(inputs, knownNumbers, 5, s -> subtract(s, knownNumbers.get(6)).size() == 1));
        knownNumbers.put(5, findDigit(inputs, knownNumbers, 5, s -> true));

        Map<String, String> mapping = new LinkedHashMap<>();
        knownNumbers.forEach((knownNumber, x) -> mapping.put(String.join("", x), String.valueOf(knownNumber)));

        return Integer.parseInt(splitToSet(output)
            .map(s -> String.join("", s))
            .map(mapping::get)
            .collect(joining()));
    }

    private static HashSet<String> findDigit(Map<Integer, List<HashSet<String>>> inputs, Map<Integer, HashSet<String>> knownNumbers, int segmentcount, Predicate<HashSet<String>> fullFilling) {
        return inputs.get(segmentcount)
            .stream()
            .filter(s -> notAlreadyKnown(knownNumbers, s))
            .filter(fullFilling)
            .findAny()
            .orElseThrow();
    }

    private static Stream<HashSet<String>> splitToSet(String output) {
        return Arrays.stream(output.split(" "))
            .map(s -> Arrays.stream(s.split("")).collect(toList()))
            .map(HashSet::new);
    }

    private static boolean notAlreadyKnown(Map<Integer, HashSet<String>> knownNumbers, HashSet<String> segment) {
        return !knownNumbers.containsValue(segment);
    }

    static <T> Set<T> subtract(Set<T> a, Set<T> b) {
        return a.stream().filter(n -> !b.contains(n)).collect(toSet());
    }
}
