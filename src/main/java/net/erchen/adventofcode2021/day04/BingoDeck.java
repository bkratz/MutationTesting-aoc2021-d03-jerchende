package net.erchen.adventofcode2021.day04;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class BingoDeck {

    private int[][] numbers;

    public static BingoDeck fromInput(String input) {

        return BingoDeck.builder()
            .numbers(Arrays.stream(input.split("\n"))
                .map(String::trim)
                .map(line -> Arrays.stream(line.split(" +")).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new))
            .build();
    }
}
