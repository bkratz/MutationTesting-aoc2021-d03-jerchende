package net.erchen.adventofcode2021.day01;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class SonarUtilsTest {


    @SneakyThrows
    static List<Integer> sampleInput() {
        return Files.readAllLines(Path.of("src/test/resources/day01/sample.txt")).stream().map(Integer::valueOf).collect(toList());
    }

    @SneakyThrows
    static List<Integer> solutionInput() {
        return Files.readAllLines(Path.of("src/test/resources/day01/input.txt")).stream().map(Integer::valueOf).collect(toList());
    }

    @Test
    void shouldCountIncreases_Sample() {
        assertThat(SonarUtils.countIncreases(sampleInput())).isEqualTo(7);
    }

    @Test
    void shouldCountIncreases() {
        assertThat(SonarUtils.countIncreases(solutionInput())).isEqualTo(1390);
    }


    @Test
    void shouldCountIncreasesInBlocks_Sample() {
        assertThat(SonarUtils.countIncreasesInBlocks(sampleInput())).isEqualTo(5);
    }

    @Test
    void shouldCountIncreasesInBlocks() {
        assertThat(SonarUtils.countIncreasesInBlocks(solutionInput())).isEqualTo(1457);
    }
}