package net.erchen.adventofcode2021.day10;

import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;

public class SyntaxChecker {

    private final static Map<Character, Character> VALID_CHARS = Map.of('(', ')', '[', ']', '{', '}', '<', '>');
    private final static Map<Character, Long> SYNTAX_ERROR_POINTS = Map.of(')', 3L, ']', 57L, '}', 1197L, '>', 25137L);
    private final static Map<Character, Long> AUTO_COMPLETE_POINTS = Map.of('(', 1L, '[', 2L, '{', 3L, '<', 4L);

    public static long calcSyntaxErrorScore(List<String> inputs) {
        return inputs.stream().mapToLong(input -> analyzeChunks(input, SYNTAX_ERROR_POINTS::get, c -> 0L)).sum();
    }

    public static long calcAutocompleteScore(List<String> inputs) {
        var scores = inputs.stream()
            .mapToLong(input -> analyzeChunks(input, c -> 0L, SyntaxChecker::calculateAutoCompleteScore))
            .filter(i -> i > 0)
            .sorted()
            .toArray();
        return scores[scores.length / 2];
    }

    private static long analyzeChunks(String input, Function<Character, Long> onCorrupt, Function<Stack<Character>, Long> onIncomplete) {
        var stack = new Stack<Character>();
        for (char c : input.toCharArray()) {
            if (VALID_CHARS.containsKey(c)) {
                stack.push(c);
            } else {
                if (VALID_CHARS.get(stack.pop()) != c) {
                    return onCorrupt.apply(c);
                }
            }
        }
        return onIncomplete.apply(stack);
    }

    private static long calculateAutoCompleteScore(Stack<Character> stack) {
        long points = 0;
        while (!stack.isEmpty()) {
            points = points * 5 + AUTO_COMPLETE_POINTS.get(stack.pop());
        }
        return points;
    }
}
