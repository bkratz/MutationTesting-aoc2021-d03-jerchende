package net.erchen.adventofcode2021.day07;

import lombok.Builder;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

@Builder
public class CrabNavigator {

    private final List<Integer> startingPositions;

    public int calculateMinimalFuel(boolean empiricalFuelConsume) {
        var statistics = startingPositions().summaryStatistics();

        return range(statistics.getMin(), statistics.getMax() + 1)
            .map(position -> startingPositions()
                .map(i -> Math.abs(position - i))
                .map(i -> empiricalFuelConsume ? empirical(i) : i)
                .sum())
            .min()
            .orElse(0);
    }

    private int empirical(int num) {
        return (num * num + num) / 2;
    }

    private IntStream startingPositions() {
        return startingPositions.stream().mapToInt(Integer::intValue);
    }
}
