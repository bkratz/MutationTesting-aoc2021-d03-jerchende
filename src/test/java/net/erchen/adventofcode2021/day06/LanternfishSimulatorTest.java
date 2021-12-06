package net.erchen.adventofcode2021.day06;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class LanternfishSimulatorTest {

    @SneakyThrows
    static List<Integer> sampleInput() {
        return Arrays.stream(Files.readString(Path.of("src/test/resources/day06/sample.txt")).trim().split(","))
            .map(Integer::parseInt)
            .collect(toList());
    }

    @SneakyThrows
    static List<Integer> solutionInput() {
        return Arrays.stream(Files.readString(Path.of("src/test/resources/day06/input.txt")).trim().split(","))
            .map(Integer::parseInt)
            .collect(toList());
    }

    @Test
    void shouldCalculatePopulationAfter80Days_Sample() {
        assertThat(LanternfishSimulator.simulateLanternfishPopulation(sampleInput(), 80)).isEqualTo(5934);
    }

    @Test
    void shouldCalculatePopulationAfter256Days_Sample() {
        assertThat(LanternfishSimulator.simulateLanternfishPopulation(sampleInput(), 256)).isEqualTo(26984457539L);
    }

    @Test
    void shouldCalculatePopulationAfter80Days_Solution() {
        assertThat(LanternfishSimulator.simulateLanternfishPopulation(solutionInput(), 80)).isEqualTo(352195);
    }

    @Test
    void shouldCalculatePopulationAfter256Days_Solution() {
        assertThat(LanternfishSimulator.simulateLanternfishPopulation(solutionInput(), 256)).isEqualTo(1600306001288L);
    }
}