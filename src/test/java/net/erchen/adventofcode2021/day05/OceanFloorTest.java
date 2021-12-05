package net.erchen.adventofcode2021.day05;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class OceanFloorTest {

    @SneakyThrows
    static List<int[]> sampleInput() {
        return parseInputs(Files.readAllLines(Path.of("src/test/resources/day05/sample.txt")));
    }

    @SneakyThrows
    static List<int[]> solutionInput() {
        return parseInputs(Files.readAllLines(Path.of("src/test/resources/day05/input.txt")));
    }

    private static List<int[]> parseInputs(List<String> input) {
        return input.stream()
            .map(s -> Arrays.stream(s.split("(,| -> )")).mapToInt(Integer::parseInt).toArray())
            .collect(toList());
    }

    @Test
    void shouldApplyHorizonalLines() {
        var oceanFloor = new OceanFloor(10);
        sampleInput().forEach(line -> oceanFloor.applyLine(line[0], line[1], line[2], line[3], true));

        assertThat(oceanFloor.toString()).isEqualTo("""
            0 0 0 0 0 0 0 1 0 0
            0 0 1 0 0 0 0 1 0 0
            0 0 1 0 0 0 0 1 0 0
            0 0 0 0 0 0 0 1 0 0
            0 1 1 2 1 1 1 2 1 1
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            0 0 0 0 0 0 0 0 0 0
            2 2 2 1 1 1 0 0 0 0""");
    }

    @Test
    void shouldApplyAllLines() {
        var oceanFloor = new OceanFloor(10);
        sampleInput().forEach(line -> oceanFloor.applyLine(line[0], line[1], line[2], line[3], false));

        assertThat(oceanFloor.toString()).isEqualTo("""
            1 0 1 0 0 0 0 1 1 0
            0 1 1 1 0 0 0 2 0 0
            0 0 2 0 1 0 1 1 1 0
            0 0 0 1 0 2 0 2 0 0
            0 1 1 2 3 1 3 2 1 1
            0 0 0 1 0 2 0 0 0 0
            0 0 1 0 0 0 1 0 0 0
            0 1 0 0 0 0 0 1 0 0
            1 0 0 0 0 0 0 0 1 0
            2 2 2 1 1 1 0 0 0 0""");
    }

    @Test
    void shouldCountDangerousAreas_Sample() {
        var oceanFloor = new OceanFloor(10);
        sampleInput().forEach(line -> oceanFloor.applyLine(line[0], line[1], line[2], line[3], true));

        assertThat(oceanFloor.countDangerousAreas()).isEqualTo(5);
    }

    @Test
    void shouldCountDangerousAreas_Solution() {
        var oceanFloor = new OceanFloor(1000);
        solutionInput().forEach(line -> oceanFloor.applyLine(line[0], line[1], line[2], line[3], true));

        assertThat(oceanFloor.countDangerousAreas()).isEqualTo(8111);
    }

    @Test
    void shouldCountDangerousAreasWithDiagonal_Sample() {
        var oceanFloor = new OceanFloor(10);
        sampleInput().forEach(line -> oceanFloor.applyLine(line[0], line[1], line[2], line[3], false));

        assertThat(oceanFloor.countDangerousAreas()).isEqualTo(12);
    }

    @Test
    void shouldCountDangerousAreasWithDiagonal_Solution() {
        var oceanFloor = new OceanFloor(1000);
        solutionInput().forEach(line -> oceanFloor.applyLine(line[0], line[1], line[2], line[3], false));

        assertThat(oceanFloor.countDangerousAreas()).isEqualTo(22088);
    }
}