package net.erchen.adventofcode2021.day14;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

@Builder(access = AccessLevel.PRIVATE)
public class Polymerization {

    private int[] template;

    @Getter
    private final Map<Integer, Character> instructions;

    public static Polymerization fromInput(String input) {
        var split = input.split("\n\n");
        return Polymerization.builder()
            .template(split[0].chars().toArray())
            .instructions(Arrays.stream(split[1].split("\n"))
                .map(line -> line.split(" -> "))
                .collect(toMap(line -> {
                    var chars = line[0].toCharArray();
                    return instructionKey(chars[0], chars[1]);
                }, line -> line[1].charAt(0))))
            .build();
    }

    public void doInsertion(int times) {
        IntStream.range(0, times).forEach(x -> doInsertion());
    }

    public void doInsertion() {
        template =
            IntStream.concat(
                    IntStream.range(0, template.length - 1)
                        .flatMap(i -> IntStream.of(template[i], instructions.get(instructionKey(template[i], template[i + 1])))),
                    IntStream.of(template[template.length - 1]))
                .toArray();

    }

    private static int instructionKey(int a, int b) {
        return a << 16 | b;
    }

    public Map<Integer, Long> countOccurrences() {
        return Arrays.stream(template).boxed().collect(groupingBy(identity(), counting()));
    }

    public long getMagicNumber() {
        var statistics = countOccurrences().values().stream().mapToLong(Long::longValue).summaryStatistics();
        return statistics.getMax() - statistics.getMin();
    }

    public String getTemplate() {
        return Arrays.stream(template).mapToObj(i -> Character.toString((char) i)).collect(joining());
    }


}
