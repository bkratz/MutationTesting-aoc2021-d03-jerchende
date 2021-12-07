package net.erchen.adventofcode2021.day07;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class CrabNavigatorTest {

    @SneakyThrows
    static List<Integer> sampleInput() {
        return Arrays.stream(Files.readString(Path.of("src/test/resources/day07/sample.txt")).trim().split(","))
            .map(Integer::parseInt)
            .collect(toList());
    }

    @SneakyThrows
    static List<Integer> solutionInput() {
        return Arrays.stream(Files.readString(Path.of("src/test/resources/day07/input.txt")).trim().split(","))
            .map(Integer::parseInt)
            .collect(toList());
    }

    @Test
    void shouldCalculateMinimalFuel_Sample() {
        var crabNavigator = CrabNavigator.builder().startingPositions(sampleInput()).build();

        assertThat(crabNavigator.calculateMinimalFuel(false)).isEqualTo(37);
    }

    @Test
    void shouldCalculateMinimalFuel_Solution() {
        var crabNavigator = CrabNavigator.builder().startingPositions(solutionInput()).build();

        assertThat(crabNavigator.calculateMinimalFuel(false)).isEqualTo(356179);
    }

    @Test
    void shouldCalculateMinimalFuelP2_Sample() {
        var crabNavigator = CrabNavigator.builder().startingPositions(sampleInput()).build();

        assertThat(crabNavigator.calculateMinimalFuel(true)).isEqualTo(168);
    }

    @Test
    void shouldCalculateMinimalFuelP2_Solution() {
        var crabNavigator = CrabNavigator.builder().startingPositions(solutionInput()).build();

        assertThat(crabNavigator.calculateMinimalFuel(true)).isEqualTo(99788435);
    }
}